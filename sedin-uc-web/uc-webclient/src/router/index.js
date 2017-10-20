import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import NotFound from '@/components/NotFound.vue'
import Home from '@/components/Home.vue'

Vue.use(Router)

let routesArray = new Array();
routesArray.push({
  path: '/',
  name: 'login',
  component: Login,
  hidden: true
});
routesArray.push({
  path: '/404',
  component: NotFound,
  name: '',
  hidden: true
});

let menus = window.sessionStorage.routes; //登录成功返回的菜单
if (menus) {
  //清空数组
  let objects  = JSON.parse(menus);
  for (let a = 0 ;a< objects.length ;a++) {
    let object = objects[a];
    object.component = Home;
    for (let b = 0 ;b< object.children.length ;b++) {
      let child = object.children[b];
      child.component  = resolve => require([`../components/` +child.filePath], resolve);
    }
    routesArray.push(object);
  }
}
routesArray.push({
  path: '*',
  hidden: true,
  redirect: { path: '/404' }
});

const router = new Router({
  routes:routesArray
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

