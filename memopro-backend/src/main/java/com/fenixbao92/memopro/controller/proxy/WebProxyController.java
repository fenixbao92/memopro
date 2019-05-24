package com.fenixbao92.memopro.controller.proxy;


import com.fenixbao92.memopro.common.constants.BusinessExceptionEnum;
import com.fenixbao92.memopro.common.exceptions.BusinessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webproxy")
public class WebProxyController {

    @RequestMapping("/needlogin")
    public void needlogin() {
        throw new BusinessException(BusinessExceptionEnum.WEB_NEED_LOGIN);
    }
}
