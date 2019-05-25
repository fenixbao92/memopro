package com.fenixbao92.memopro.controller.proxy;

import com.fenixbao92.memopro.service.wx.WxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wxproxy")
public class WxProxyController {

    @Resource
    WxService wxService;

    @RequestMapping("/codeAuth")
    public String codeAuth(@RequestParam String code) {
        return wxService.codeAuth(code);
    }

}
