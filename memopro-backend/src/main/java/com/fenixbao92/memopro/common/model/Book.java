package com.fenixbao92.memopro.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class Book implements Serializable {

    private Long bookId;
    private Long userId;
    private String index;//编号
    private String name;//书名
    private String url;//封面url
    private String thoughts;//心得
    private String status;
    private String tag;
    private String remark;
    private Date updateTime;

}
