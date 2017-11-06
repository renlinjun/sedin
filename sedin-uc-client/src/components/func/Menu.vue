<template>
  <section>
  <el-row :gutter="10">
    <el-col :span="6">
      <div class="elButton">
        <el-button type="primary"   @click="handleAdd" :disabled="this.currentLevel===3"  icon="el-icon-plus">增加</el-button>
        <el-button type="primary"  @click="handleEdit" :disabled="this.currentNodeData.id===0" icon="el-icon-edit">修改</el-button>
        <el-button type="primary"  @click="handleDel"  :loading="delLoading" :disabled="this.currentNodeData.id===0 || !this.currentNodeData.leaf" icon="el-icon-delete">删除</el-button>
      </div>
      <div>
      <el-tree
        :props="props1"
        :load="loadNode"
        highlight-current
        @node-click="selectedNode"
        node-key="id"
        :default-expanded-keys="[0]"
        ref="tree"
        lazy>
      </el-tree>  </div>
    </el-col>
    <el-col :span="6">
    </el-col>
    <el-col :span="6">
    </el-col>
    <el-col :span="6">
    </el-col>
  </el-row>


    <!--编辑界面-->
    <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="菜单名" prop="name">
          <el-input v-model="editForm.name" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="地址" prop="url">
          <el-input v-model="editForm.url" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="对应文件" prop="filePath">
          <el-input v-model="editForm.filePath" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="备注">
          <el-input
            type="textarea"
            :rows="4"
            placeholder="请输入内容"
            v-model="editForm.description">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--增加界面-->
    <el-dialog title="增加界面" v-model="addFormVisible" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
        <el-form-item label="菜单名" prop="name">
          <el-input v-model="addForm.name" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="地址" prop="url">
          <el-input v-model="addForm.url" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="对应文件" prop="filePath">
          <el-input v-model="addForm.filePath" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="备注">
          <el-input
            type="textarea"
            :rows="4"
            placeholder="请输入内容"
            v-model="addForm.description">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>

  </section>
</template>

<script>
  import util from '../../common/js/util'
  //import NProgress from 'nprogress'
  import {getMenus , removeMenu, editMenu, addMenu} from '../../api/api';

  export default {
    data() {
      let loginUserJson = JSON.parse(sessionStorage.getItem('user'));
      return  {
        props1: {
          label: 'name',
          children: 'zones',
          isLeaf: 'leaf'
        },
        currentNodeData:{ name: '根目录' , id:0 , zones:[] },
        currentNode:null,
        currentLevel:1,

        editFormVisible:false,
        editForm:{
          name:'',
          filePath:'',
          url:'',
          description:''
        },
        editFormRules:{
          name:[
            { required: true, message: '请输入菜单名', trigger: 'blur' }
          ]
        },
        editLoading:false,

        addFormVisible:false,
        addForm:{
          name:'',
          filePath:'',
          url:'',
          description:''
        },
        addFormRules:{
          name:[
            { required: true, message: '请输入菜单名', trigger: 'blur' }
          ]
        },
        addLoading:false,
        delLoading:false
      };
    },
    methods: {
      loadNode:function(node, resolve) {
        if (node.level === 0) {
          return resolve([this.currentNodeData]);
        }
        let para = { parentId:node.data.id };
        getMenus(para).then((res) => {
            let list = res.data.data;
            let arr = new Array(list.length);
            this.$set(node.data, 'zones', []);
            for (let i = 0 ;i < list.length ;i++ ) {
              let res = list[i];
              let isLeaf = (res.isLeaf == '0') ;

              let nod = {
                    name:res.name,
                    id:res.id,
                    leaf: isLeaf,
                    data:res,
                     zones:[]
                  };
              arr[i] = nod;
              if (!node.data.zones) {
                this.$set(node.data, 'zones', []);
              }
              node.data.zones.push(nod);
            }
            resolve(arr);
        });
      },
      selectedNode:function (data , node , comp) {
        this.currentNodeData = data;
        this.currentLevel = node.level;
        this.currentNode = node;
      },
      editSubmit:function () {
        this.$refs.editForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.editLoading = true;
              //NProgress.start();
              let para = Object.assign({}, this.editForm);

              editMenu(para).then(data => {
                this.editLoading = false;
                //NProgress.done();
                this.$message({
                  message: '提交成功',
                  type: 'success'
                });
                this.$refs['editForm'].resetFields();
                this.editFormVisible = false;

                this.currentNodeData.data = data.data;
                this.currentNodeData.name = data.data.name;
                this.currentNode.data = this.currentNodeData;
              });
            });
          }
        });
      },
      addSubmit:function () {
        this.$refs.addForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.addLoading = true;
              //NProgress.start();
              let para = Object.assign({}, this.addForm);
              addMenu(para).then(data => {
                this.addLoading = false;
                //NProgress.done();
                this.$message({
                  message: '提交成功',
                  type: 'success'
                });
                this.$refs['addForm'].resetFields();
                this.addFormVisible = false;

                const newChild = { id: data.data.id, name: data.data.name, data:data.data , leaf:true , zones:[] };

                console.log(this.currentNodeData);
                if (!this.currentNodeData.zones) {
                  this.$set(this.currentNodeData, 'zones', []);
                }

                if (this.currentNodeData.zones.length == 0) {
                  this.currentNodeData.leaf = false;
                  this.currentNodeData.data.isLeaf = false;

                  const parent = this.currentNode.parent;
                  const children = parent.data.zones || parent.data;
                  const index = children.findIndex(d => d.id === this.currentNodeData.id);
                  children.splice(index, 1);
                  parent.data.zones.push(this.currentNodeData);
                }
                console.log(this.currentNodeData);

                this.currentNodeData.zones.push(newChild);
              });
            });
          }
        });
      },
      handleDel:function () {
        this.$confirm('确认删除该记录吗?', '提示', {
          type: 'warning'
        }).then(() => {
          this.delLoading = true;
          //NProgress.start();
          let para = { id: this.currentNodeData.id };
          removeMenu(para).then((res) => {
            this.delLoading = false;
            //NProgress.done();
            this.$message({
              message: '删除成功',
              type: 'success'
            });

            const parent = this.currentNode.parent;
            const children = parent.data.zones || parent.data;
            const index = children.findIndex(d => d.id === this.currentNodeData.id);
            children.splice(index, 1);

            if (parent.data.zones.length == 0) {
              parent.data.leaf = true;
              parent.data.data.isLeaf = true;
            }

          });
        }).catch(() => {

        });
      },
      //显示编辑界面
      handleEdit: function () {
        this.editFormVisible = true;
        this.editForm = Object.assign({}, this.currentNodeData.data);

      },
      //显示新增界面
      handleAdd: function () {
        this.addFormVisible = true;
        this.addForm={
            name:'',
            filePath:'',
            url:'',
            description:'',
            parentId:this.currentNodeData.id
        };
      },
    },
    mounted() {
    }
  }

</script>

<style scoped>
.elButton {
  margin-bottom: 10px;
  margin-top: 10px;
}
</style>
