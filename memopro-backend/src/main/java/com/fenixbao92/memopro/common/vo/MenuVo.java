package com.fenixbao92.memopro.common.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuVo {

    private String url;
    private String path;
    private String component;
    private String name;
    private String icon;

    private List<MenuVo> children;

    public MenuVo() {
    }

    public MenuVo(String url, String path, String component, String name, String icon) {
        this.url = url;
        this.path = path;
        this.component = component;
        this.name = name;
        this.icon = icon;
    }
}
