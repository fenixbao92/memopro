package com.fenixbao92.memopro.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class User {
    private Long userId;
    private String account;
    private String name;
    private String avatar;
    private String phone;
    private String password;
    private String remark;
    private Integer status;
}
