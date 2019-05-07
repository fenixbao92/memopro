package com.fenixbao92.memopro.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class Todo {
    private Long todoId;
    private Long userId;
    private String name;
    private String category;//小便条 每日待办 每周待办
    private String time;
    private String status;
    private String tag;//健身 编程 人文阅读
    private Long parentId;
    private String index;//编号如20190715#1
    private Date updateTime;
}
