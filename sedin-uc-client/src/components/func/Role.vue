<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="角色名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getRoles">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>

      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="roles" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
      <el-table-column type="selection" width="55" >
      </el-table-column>
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="name" label="名称"  width="100"  sortable>
      </el-table-column>
      <el-table-column prop="description" label="备注"   width="200"    sortable>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="75" :formatter="formatStatus" sortable>
    </el-table-column>
      <el-table-column prop="sort" label="排序号" width="75"   sortable>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="danger"   size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
          <el-button type="danger"   size="small" @click="handleAuthTree(scope.$index, scope.row)">设置权限</el-button>



        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="primary" :disabled="this.sels.length===0" @click="handleSetType('1')">禁用</el-button>
      <el-button type="primary" :disabled="this.sels.length===0" @click="handleSetType('0')">启用</el-button>
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="5" :total="total" style="float:right;">
      </el-pagination>
    </el-col>
    <!--编辑界面-->
    <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="角色名" prop="name">
          <el-input v-model="editForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            type="textarea"
            :rows="4"
            placeholder="请输入内容"
            v-model="editForm.description">
          </el-input>
        </el-form-item>
        <el-form-item label="排序号" prop="sort">
          <el-input v-model="editForm.sort" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--新增界面-->
    <el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">

        <el-form-item label="角色名" prop="name">
          <el-input v-model="addForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            type="textarea"
            :rows="4"
            placeholder="请输入内容"
            v-model="addForm.description">
          </el-input>
        </el-form-item>
        <el-form-item label="排序号" prop="sort">
          <el-input v-model="addForm.sort" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--设置权限界面-->
    <el-dialog title="权限" v-model="authTreeVisible" :close-on-click-modal="false">
      <!-- <el-input
         placeholder="输入关键字进行过滤"
         v-model="filterText">
       </el-input>-->
       <el-tree
         :props="props1"
         show-checkbox
         highlight-current
         node-key="id"
         ref="menuTree"
         :default-expanded-keys="[0]"
         :data="treeData">
       </el-tree>
       <div slot="footer" class="dialog-footer">
         <el-button @click.native="authTreeVisible = false">取消</el-button>
         <el-button type="primary" @click.native="authSaveSubmit" :loading="authSaveLoading">保存</el-button>
       </div>
     </el-dialog>
   </section>
 </template>

 <script>
   import util from '../../common/js/util'
   //import NProgress from 'nprogress'
   import { getRoleListPage, removeRole, batchRemoveRole, editRole, addRole , setRoleType , getAllMenu , getMenuByRoleId , saveRoleMenu} from '../../api/api';

   export default {
     data() {
       let loginUserJson = JSON.parse(sessionStorage.getItem('user'));

       return {
         filters: {
           name: ''
         },
         headers:{Authorization:`Bearer ${sessionStorage.JWT}`},
         roles: [],
         loginUser:loginUserJson,
         total: 0,
         page: 1,
         listLoading: false,
         sels: [],//列表选中列

         editFormVisible: false,//编辑界面是否显示
         editLoading: false,
         editFormRules: {
           name: [
             { required: true, message: '请输入角色名', trigger: 'blur' }
           ]
         },
         //编辑界面数据
         editForm: {
           name: '',
           description: '',
           sort:''
         },
         addFormVisible: false,//新增界面是否显示
         addLoading: false,
         addFormRules: {
           name: [
             { required: true, message: '请输入角色名', trigger: 'blur' }
           ]
         },
         //新增界面数据
         addForm: {
           name: '',
           description: '',
           sort:''
         },
         props1: {
           label: 'name',
           children: 'zones',
           isLeaf: 'leaf'
         },

         authTreeVisible:false,
         authSaveLoading:false,
         treeData:[
           { name: '根目录' , id:0 , zones:[] }
         ],
         roleId:0
       }
     },
     methods: {
       formatStatus: function (row, column) {
         return row.status == '0' ? '启用' :  '禁用';
       },
       handleCurrentChange(val) {
         this.page = val;
         this.getRoles();
       },
       getMenus:function () {
         let para = {id:0};
         getAllMenu(para).then((res) => {
           //加载整个菜单数据
           let list = res.data.data;
           this.treeData[0].zones= list;
         });
       },
       //获取用户列表
       getRoles() {
         let para = {
           page: this.page,
           name: this.filters.name,
           pageSize:5
         };
         this.listLoading = true;
         //NProgress.start();
         getRoleListPage(para).then((res) => {
           this.total = res.data.total;
           this.roles = res.data.list;
           this.listLoading = false;
           //NProgress.done();
         });
       },
       //删除
       handleDel: function (index, row) {
         this.$confirm('确认删除该记录吗?', '提示', {
           type: 'warning'
         }).then(() => {
           this.listLoading = true;
           //NProgress.start();
           let para = { id: row.id };
           removeRole(para).then((res) => {
             this.listLoading = false;
             //NProgress.done();
             this.$message({
               message: '删除成功',
               type: 'success'
             });
             this.getRoles();
           });
         }).catch(() => {

         });
       },
       handleSetType:function (type) {
         var ids = this.sels.map(item => item.id).toString();
         let para = {
           ids: ids,
           type:type
         };

         setRoleType(para).then((res) => {
           this.$message({
             message: '设置成功',
             type: 'success'
           });
           this.getRoles();
           //NProgress.done();
         });
       },
       //显示编辑界面
       handleEdit: function (index, row) {
         this.editFormVisible = true;
         this.editForm = Object.assign({}, row);
         console.log(this.editForm);
       },
       //显示新增界面
       handleAdd: function () {
         this.addFormVisible = true;
         this.addForm = {
           name: '',
           description: '',
           sort:''
         };
       },
       handleAuthTree: function (index, row) {
         let _this = this;
          this.roleId = row.id;
         //加载节点数据
         let para = { roleId: row.id };
         this.authTreeVisible = true;
         getMenuByRoleId(para).then(data => {
           _this.$refs.menuTree.setCheckedKeys([]);
           console.log(data.data);
           let list = data.data.data;
           let selectedKey = new Array( list.length );
           for (let i = 0 ;i < list.length ;i++) {
              let obj = list[i];
             selectedKey[i] = obj.id;
           }
           _this.$refs.menuTree.setCheckedKeys(selectedKey);
         });
       },
       //编辑
       editSubmit: function () {
         this.$refs.editForm.validate((valid) => {
           if (valid) {
             this.$confirm('确认提交吗？', '提示', {}).then(() => {
               this.editLoading = true;
               //NProgress.start();
               let para = Object.assign({}, this.editForm);
               console.log(para);
               editRole(para).then(data => {
                 this.editLoading = false;
                 //NProgress.done();
                 this.$message({
                   message: '提交成功',
                   type: 'success'
                 });
                 this.$refs['editForm'].resetFields();
                 this.editFormVisible = false;
                 this.getRoles();
               });
             });
           }
         });
       },
       //新增
       addSubmit: function () {
         this.$refs.addForm.validate((valid) => {
           if (valid) {
             this.$confirm('确认提交吗？', '提示', {}).then(() => {
               this.addLoading = true;
               //NProgress.start();
               let para = Object.assign({}, this.addForm);
               addRole(para).then(data => {
                 this.addLoading = false;
                 //NProgress.done();
                 this.$message({
                   message: '提交成功',
                   type: 'success'
                 });
                 this.$refs['addForm'].resetFields();
                 this.addFormVisible = false;
                 this.getRoles();
               });
             });
           }
         });
       },
       authSaveSubmit:function () {
           //保存节点数据
             this.$confirm('确认提交吗？', '提示', {}).then(() => {
               this.authSaveLoading = true;
               //NProgress.start();
               this.authSaveLoading = false;
               let keys = this.$refs.menuTree.getCheckedKeys();

               let idsStr = "";
               for(let j = 0; j < keys.length; j++) {
                 idsStr += keys[j] +",";
               }
               let para ={ids:idsStr , roleId:this.roleId};
               saveRoleMenu(para).then(data => {
                 this.authSaveLoading = false;
                 //NProgress.done();
                 this.$message({
                   message: '提交成功',
                   type: 'success'
                 });
                 this.authTreeVisible = false;
               });
             });
       },
       selsChange: function (sels) {
         this.sels = sels;
       },
       //批量删除
       batchRemove: function () {
         var ids = this.sels.map(item => item.id).toString();
         this.$confirm('确认删除选中记录吗？', '提示', {
           type: 'warning'
         }).then(() => {
           this.listLoading = true;
           //NProgress.start();
           let para = { ids: ids };
           batchRemoveRole(para).then((res) => {
             this.listLoading = false;
             //NProgress.done();
             this.$message({
               message: '删除成功',
               type: 'success'
             });
             this.getRoles();
           });
         }).catch(() => {

         });
       }
     },
     mounted() {
       this.getRoles();
       this.getMenus();
     }
   }

 </script>

 <style scoped>
   .avatar-uploader .el-upload {
     border: 1px dashed #d9d9d9;
     border-radius: 6px;
     cursor: pointer;
     position: relative;
     overflow: hidden;
   }
   .avatar-uploader .el-upload:hover {
     border-color: #409EFF;
   }
   .avatar-uploader-icon {
     font-size: 28px;
     color: #8c939d;
     width: 178px;
     height: 178px;
     line-height: 178px;
     text-align: center;
   }
   .avatar {
     width: 178px;
     height: 178px;
     display: block;
   }
 </style>
