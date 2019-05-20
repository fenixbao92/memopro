package com.fenixbao92.memopro.common.model.wx;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WxCodeAuthResult {
    private String openid;
    private String session_key;
    private String unionid;
    private String errcode;
    private String errmsg;
}
