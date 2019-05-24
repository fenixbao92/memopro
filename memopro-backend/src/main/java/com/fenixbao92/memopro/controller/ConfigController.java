package com.fenixbao92.memopro.controller;

import com.fenixbao92.memopro.common.model.User;
import com.fenixbao92.memopro.common.vo.MenuVo;
import com.fenixbao92.memopro.service.MenuService;
import com.fenixbao92.memopro.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Resource
    MenuService menuService;

    @Resource
    UserService userService;

    @RequestMapping("/sysmenu")
    public MenuVo sysmenu() {
        return menuService.getMenusByUserId(userService.getCurrentUser().getUserId());
    }

    @RequestMapping("/currentUser")
    public User currentUser() {
        return userService.getCurrentUser();
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public void add(User user) {
        userService.register(user.getAccount(),user.getPassword());
    }
}
