package com.fenixbao92.memopro.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Contact {
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
