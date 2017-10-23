<template>
  <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px" class="demo-ruleForm login-container">
    <h3 class="title">系统登录</h3>
    <el-form-item prop="userId">
      <el-input type="text" v-model="ruleForm2.userId" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="ruleForm2.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <!--<el-checkbox v-model="checked" checked class="remember">记住密码</el-checkbox>-->
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit2" :loading="logining">登录</el-button>
       <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
    </el-form-item>
  </el-form>
</template>


<script>
import { requestLogin } from '../api/api';
import md5 from 'js-md5';
import Login from '@/components/Login'
import NotFound from '@/components/NotFound.vue'
import Home from '@/components/Home.vue'
//import NProgress from 'nprogress'
export default {
  data() {
    return {
      logining: false,
      ruleForm2: {
        userId: '',
        password: ''
      },
      rules2: {
        userId: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          //{ validator: validaePass }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          //{ validator: validaePass2 }
        ]
      },
      checked: true
    };
  },
  methods: {
    handleReset2() {
      this.$refs.ruleForm2.resetFields();
    },
  getUrlKey:function(name){
    return decodeURIComponent((new RegExp('[?|&]'+name+'='+'([^&;]+?)(&|#|;|$)').exec(location.href)||[,""])[1].replace(/\+/g,'%20'))||null;
  },
    handleSubmit2(ev) {
      var _this = this;
      this.$refs.ruleForm2.validate((valid) => {
        if (valid) {
          this.logining = true;
          //NProgress.start();
          var loginParams = { userId: this.ruleForm2.userId, password: md5(this.ruleForm2.password) , visit:this.$route.query.uri };
          requestLogin(loginParams).then(data => {
            this.logining = false;
            //NProgress.done();
            let { success, code , msg , data: userData} = data;
            if (!success) {
              this.$message({
                message: msg,
                type: 'error'
              });
            } else {
              let url = _this.getUrlKey("uri");
              if (url){
                if (url.indexOf("?") == -1) {
                  url += "?ticket=" + userData.ticket;
                } else {
                  url += "&ticket=" + userData.ticket;
                }
                window.location.href = url;
                return ;
              }

              sessionStorage.setItem('user', JSON.stringify(userData.user));

              //清空数组
              this.$router.options.routes.splice(0 , this.$router.options.routes.length);

              this.$router.options.routes.push({
                path: '/',
                name: 'login',
                component: Login,
                hidden: true
              });

              this.$router.options.routes.push({
                path: '/404',
                component: NotFound,
                name: '',
                hidden: true
              });
              let objects = userData.authority.menus;
              sessionStorage.routes=JSON.stringify(objects);

              for (let a = 0 ;a< objects.length ;a++) {
                 let object = objects[a];
                  object.component = Home;
                  for (let b = 0 ;b< object.children.length ;b++) {
                    let child = object.children[b];
                    console.log(child);
                    child.component  = resolve => require([`./` + child.filePath], resolve);
                  }
                this.$router.options.routes.push(object);
              }

              this.$router.options.routes.push({
                path: '*',
                hidden: true,
                redirect: { path: '/404' }
              });

              this.$router.addRoutes(this.$router.options.routes);
              this.$router.push({ path: '/main' });
            }
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}

</script>

<style lang="scss" scoped>
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
</style>
