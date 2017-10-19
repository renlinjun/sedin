import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import NotFound from '@/components/404.vue'
import Home from '@/components/Home.vue'
import Main from '@/components/Main.vue'
import User from '@/components/nav1/User.vue'
import Form from '@/components/nav1/Form.vue'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login,
      hidden: true
    },
    {
      path: '/404',
      component: NotFound,
      name: '',
      hidden: true
    },
    {
      path: '/',
      component: Home,
      name: '系统管理',
      iconCls: 'el-icon-setting',//图标样式class
      children: [
        { path: '/main', component: Main, name: '主页', hidden: true },
        { path: '/user', component: User, name: '用户管理' },
        { path: '/form', component: Form, name: '表单' }
      ]
    },
    {
      path: '*',
      hidden: true,
      redirect: { path: '/404' }
    }
  ]
})

router.beforeEach((to, from, next) => {
  //NProgress.start();
  if (to.path == '/') {
    sessionStorage.removeItem('user');
  }
  let user = JSON.parse(sessionStorage.getItem('user'));
  if (!user  && to.path != '/') {
    next({ path: '/' })
  } else {
    next()
  }

})
export default router

