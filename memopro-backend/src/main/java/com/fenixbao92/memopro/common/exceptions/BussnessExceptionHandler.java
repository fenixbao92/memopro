package com.fenixbao92.memopro.common.exceptions;

import com.fenixbao92.memopro.common.vo.Result;
import com.fenixbao92.memopro.controller.ContactController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Create by fenixbao92 on 2019/5/13.
 */
@ControllerAdvice
public class BussnessExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(BussnessExceptionHandler.class);

    @ExceptionHandler(BussnessException.class)
    @ResponseBody
    public Result handleException(HttpServletRequest request, Exception e)
    {
        logger.error("Request FAILD, URL = {} ", request.getRequestURI());
        BussnessException bussnessException = (BussnessException)e;
        return Result.error(bussnessException.getStatus(),bussnessException.getMsg());
    }

}
