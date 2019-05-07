package com.fenixbao92.memopro.common.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SiteVo {

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
