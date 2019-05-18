package com.fenixbao92.memopro.common.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookVo {

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
