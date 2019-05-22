package com.fenixbao92.memopro.common.vo.wx;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WxRegisterVo {

    private String nickName;
    private int gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String account;
    private String password;

    private String sessionId;
    private String openId; //can not get from client,get from session

}