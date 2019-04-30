package com.fenixbao92.memopro.common.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVo {
    private String account;
    private String avatar;
    private String[] roles;
    private Integer status;

    UserVo(){}

    public UserVo(String account, String avatar, String[] roles,Integer status) {
        this.account = account;
        this.avatar = avatar;
        this.roles = roles;
        this.status = status;
    }
}
