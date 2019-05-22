package com.fenixbao92.memopro.controller.proxy;


import com.fenixbao92.memopro.common.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/webproxy")
public class WebProxyController {

    @RequestMapping("/needlogin")
    public Result needlogin() {
        return Result.needLogin();
    }
}
