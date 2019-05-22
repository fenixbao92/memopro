package com.fenixbao92.memopro.controller.proxy;

import com.fenixbao92.memopro.common.vo.Result;
import com.fenixbao92.memopro.service.wx.WxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wxproxy")
public class WxProxyController {

    @Resource
    WxService wxService;

    @RequestMapping("/codeAuth")
    public Result codeAuth(@RequestParam String code) {
        String sessionId = wxService.codeAuth(code);
        return Result.ok(sessionId);
    }

}
