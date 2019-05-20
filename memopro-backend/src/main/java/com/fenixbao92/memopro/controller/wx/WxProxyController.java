package com.fenixbao92.memopro.controller.wx;

import com.fenixbao92.memopro.common.model.wx.WxCodeAuthResult;
import com.fenixbao92.memopro.common.vo.Result;
import com.fenixbao92.memopro.service.wx.WxProxyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wxproxy")
public class WxProxyController {

    @Resource
    WxProxyService wxProxyService;

    @RequestMapping("/codeAuth")
    public Result codeAuth(@RequestParam String code) {
        WxCodeAuthResult wxCodeAuthResult = wxProxyService.codeAuth(code);
        return Result.ok(wxCodeAuthResult);
    }

}
