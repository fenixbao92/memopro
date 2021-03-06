// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './store'
import {getRequest} from './utils/api'
import {postRequest} from './utils/api'
import {initMenu} from './utils/utils'
import './utils/filter_utils'
import 'font-awesome/css/font-awesome.min.css'

Vue.config.productionTip = false
Vue.use(ElementUI)

Vue.prototype.getRequest = getRequest;
Vue.prototype.postRequest = postRequest;

router.beforeEach((to, from, next) => {

    if (to.name == '登录界面') {
      next();
      return;
    }
    // var name = store.state.user.account;
    // if (name == '未登录') {
    //   if (to.meta.requireAuth || to.name == null) {
    //     next({path: '/', query: {redirect: to.path}})
    //   } else {
    //     next();
    //   }
    // } else {
    //   initMenu(router, store);
    //   if(to.path=='/chat')
    //     store.commit("updateMsgList", []);
    //   next();
    // }
    initMenu(router, store);
    next();
  }
)

new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: {App}
})
