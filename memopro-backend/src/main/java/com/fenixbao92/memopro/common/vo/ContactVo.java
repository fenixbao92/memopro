package com.fenixbao92.memopro.common.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ContactVo {
    private Long contactId;
    private Long userId;
    private String name;
    private String birthday;
    private String phone;
    private String email;
    private String tag;
    private String remark;
    private Date updateTime;
}
