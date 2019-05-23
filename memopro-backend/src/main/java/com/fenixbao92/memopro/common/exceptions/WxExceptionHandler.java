package com.fenixbao92.memopro.common.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by fenixbao92 on 2019/5/23.
 */
@ControllerAdvice
@Slf4j
public class WxExceptionHandler {

    @ExceptionHandler(WxException.class)
    public void handleException(HttpServletRequest request, HttpServletResponse response, WxException e) {
        log.error("微信登录异常：---Host {} invokes url {} ERROR: {}", request.getRemoteHost(), request.getRequestURL(), e.getMessage());
        response.setStatus(e.getCode());
    }

}
