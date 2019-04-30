import {getRequest} from './api'
import {Message} from 'element-ui'

export const isNotNullORBlank = (...args)=> {
  for (var i = 0; i < args.length; i++) {
    var argument = args[i];
    if (argument == null || argument == '' || argument == undefined) {
      Message.warning({message: '数据不能为空!'})
      return false;
    }
  }
  return true;
}
export const initMenu = (router, store)=> {
  // console.log(store.state.routes);
  if (store.state.routes.length > 0) {
    return;
  }
  getRequest("/config/sysmenu").then(resp=> {
    if (resp && resp.status == 200) {
      var fmtRoutes = formatRoutes(resp.data.children);
      router.addRoutes(fmtRoutes);
      store.commit('initMenu', fmtRoutes);
      // store.dispatch('connect');
    }
  })
}
export const formatRoutes = (routes)=> {
  let fmRoutes = [];
  routes.forEach(router=> {
    let {
      path,
      component,
      name,
      icon,
      children
    } = router;
    if (children && children instanceof Array) {
      children = formatRoutes(children);
    }
    let fmRouter = {
      path: path,
      component(resolve){
        console.log(component);
        console.log(typeof component);
        if (component.startsWith("Home")) {
          require(['../components/' + component + '.vue'], resolve)
        }
        else if (component.startsWith("Social")) {
          require(['../components/social/' + component + '.vue'], resolve)
        }
        // else if (component.startsWith("Per")) {
        //   require(['../components/personnel/' + component + '.vue'], resolve)
        // } else if (component.startsWith("Sal")) {
        //   require(['../components/salary/' + component + '.vue'], resolve)
        // } else if (component.startsWith("Sta")) {
        //   require(['../components/statistics/' + component + '.vue'], resolve)
        // } else if (component.startsWith("Sys")) {
        //   require(['../components/system/' + component + '.vue'], resolve)
        // }
      },
      name: name,
      icon: icon,
      children: children
    };
    // console.log(fmRouter);
    fmRoutes.push(fmRouter);
  })
  return fmRoutes;
}
