package com.fenixbao92.memopro.controller;

import com.fenixbao92.memopro.common.model.Contact;
import com.fenixbao92.memopro.common.model.User;
import com.fenixbao92.memopro.common.utils.UserUtil;
import com.fenixbao92.memopro.common.vo.MenuVo;
import com.fenixbao92.memopro.common.vo.Result;
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
        return menuService.getMenusByUserId(userService.getCurrentUserByAccount().getUserId());
    }

    @RequestMapping("/currentUser")
    public User currentUser() {
        return userService.getCurrentUserByAccount();
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Result add(User user) {
        if (userService.register(user) == 1) {
            return Result.ok("注册成功!");
        }
        return Result.error("注册失败!");
    }
}
