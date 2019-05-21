package com.fenixbao92.memopro.controller.proxy;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/webproxy")
public class WebProxyController {

    @RequestMapping("/frontpage/redirect")
    public String codeAuth1(HttpServletRequest httpServletRequest) {
        return "redirect:/";
    }
}
