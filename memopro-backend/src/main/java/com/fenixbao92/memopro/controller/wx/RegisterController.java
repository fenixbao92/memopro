package com.fenixbao92.memopro.controller.wx;

import com.fenixbao92.memopro.common.constants.BusinessExceptionEnum;
import com.fenixbao92.memopro.common.exceptions.BusinessException;
import com.fenixbao92.memopro.common.vo.wx.WxRegisterVo;
import com.fenixbao92.memopro.service.wx.WxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.fenixbao92.memopro.common.constants.Constant.SUCCESS;

@RestController
@RequestMapping("/wx")
public class RegisterController {

    @Resource
    private WxService wxService;

    @RequestMapping(value = "/register")
    public void register(WxRegisterVo wxRegisterVo, HttpServletRequest httpServletRequest) {
        String sessionId = httpServletRequest.getHeader("sessionId");
        wxRegisterVo.setSessionId(sessionId);
        wxService.register(wxRegisterVo);
    }

    @RequestMapping(value = "/test")
    public String test(Integer c) {
        if(c==1){
            throw new BusinessException(BusinessExceptionEnum.WX_SESSION_INVALID);
        }
        return "test success";
    }

}
