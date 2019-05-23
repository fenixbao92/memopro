package com.fenixbao92.memopro.common.exceptions;

import com.fenixbao92.memopro.common.constants.WxConstant;

/**
 * Create by fenixbao92 on 2019/5/23.
 */
public class WxException extends RuntimeException{
    private int code;   // 异常状态码
    public WxException(WxConstant.WxExceptionEnum wxExceptionEnum) {
        super(wxExceptionEnum.getDescription());
        code = wxExceptionEnum.getCode();
    }
    public int getCode() {
        return code;
    }
}
