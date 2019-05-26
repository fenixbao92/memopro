package com.fenixbao92.memopro.controller.proxy;

import com.fenixbao92.memopro.common.constants.BusinessExceptionEnum;
import com.fenixbao92.memopro.common.exceptions.BusinessException;
import com.fenixbao92.memopro.common.model.wx.WxUser;
import com.fenixbao92.memopro.common.utils.UserUtil;
import com.fenixbao92.memopro.common.vo.wx.WxRegisterVo;
import com.fenixbao92.memopro.service.wx.WxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//wxproxy is open api with no auth.
@RestController
@RequestMapping("/wxproxy")
public class WxProxyController {

    @Resource
    private WxService wxService;

    @RequestMapping("/codeAuth")
    public String codeAuth(@RequestParam String code) {
        return wxService.codeAuth(code);
    }

    @RequestMapping(value = "/register")
    public void register(WxRegisterVo wxRegisterVo, HttpServletRequest httpServletRequest) {
        String sessionId = httpServletRequest.getHeader("sessionId");
        wxRegisterVo.setSessionId(sessionId);
        wxService.register(wxRegisterVo);
    }

    @RequestMapping("/getbindinfo")
    public WxUser getbindinfo(HttpServletRequest httpServletRequest) {
        String sessionId = httpServletRequest.getHeader("sessionId");
        return wxService.getbindinfo(sessionId);
    }

    @RequestMapping(value = "/test")
    public String test(Integer c) {
        if(c==1){
            throw new BusinessException(BusinessExceptionEnum.WX_SESSION_INVALID);
        }
        String accountofCurrentUser = UserUtil.getAccountofCurrentUser();
        return "test success";
    }

}
