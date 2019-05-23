package com.fenixbao92.memopro.controller.wx;

import com.fenixbao92.memopro.common.constants.WxConstant;
import com.fenixbao92.memopro.common.exceptions.BussnessException;
import com.fenixbao92.memopro.common.exceptions.WxException;
import com.fenixbao92.memopro.common.model.Book;
import com.fenixbao92.memopro.common.vo.Result;
import com.fenixbao92.memopro.common.vo.wx.WxRegisterVo;
import com.fenixbao92.memopro.service.wx.WxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wx")
public class RegisterController {

    @Resource
    WxService wxService;

    @RequestMapping(value = "/register")
    public Result register(WxRegisterVo wxRegisterVo, HttpServletRequest httpServletRequest) {
        String sessionId = httpServletRequest.getHeader("sessionId");
        wxRegisterVo.setSessionId(sessionId);
        wxService.register(wxRegisterVo);
        return Result.ok();
    }

    @RequestMapping(value = "/test")
    public Result test(Integer c) {
        if(c==1){
            throw new WxException(WxConstant.WxExceptionEnum.SESSION_INVALID);
        }
        return Result.ok();
    }

}
