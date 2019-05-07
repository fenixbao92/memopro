package com.fenixbao92.memopro.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Site {

    private Long siteId;
    private Long userId;
    private String name;
    private String url;
    private String account;
    private String password;
    private String tag;
    private String remark;
    private Date updateTime;
}
