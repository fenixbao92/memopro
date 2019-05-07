package com.fenixbao92.memopro.common.constants;

import com.fenixbao92.memopro.common.vo.MenuVo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fenixbao92.memopro.common.constants.PrivilegeConstant.MenuMeta.*;


public class PrivilegeConstant {

    @Getter
    public enum MenuMeta {
        //level 0
        ROOT(0, "/", null, null, "所有", null, null),

        //level 1
        BASIC(1, "/", "/home", "Home", "基本资料", "fa fa-user-circle-o", ROOT),
        SOCIAL(2, "/", "/home", "Home", "社交管理", "fa fa-address-card-o", ROOT),
        READING(3, "/", "/home", "Home", "阅读管理", "fa fa-money", ROOT),
        TODO(4, "/", "/home", "Home", "待办管理", "fa fa-bar-chart", ROOT),
        SYSTEM(5, "/", "/home", "Home", "系统管理", "fa fa-bar-chart", ROOT),

        //level 2
        BASIC_INFO(6, "/basic/info/**", "/basic/info", "BasicInfo", "个人信息", null, BASIC),
        BASIC_PASSWORD(7, "/basic/password/**", "/basic/password", "BasicPassword", "账号密码", null, BASIC),

        SOCIAL_CONTACT(8, "/social/contact/**", "/social/contact", "SocialContact", "通讯录", null, SOCIAL),

        READING_LIST(9, "/reading/list/**", "/reading/list", "ReadingList", "阅读书目", null, READING),

        TODO_NOTE(10, "/todo/note/**", "/todo/note", "TodoNote", "小便条", null, TODO),
        TODO_DAY(11, "/todo/day/**", "/todo/day", "TodoDay", "每日待办", null, TODO),
        TODO_WEEK(12, "/todo/week/**", "/todo/week", "TodoWeek", "每周计划", null, TODO),

        SYSTEM_PRIVILEGE(13, "/system/privilege/**", "/system/privilege", "SystemPrivilege", "系统权限", null, SYSTEM),;

        MenuMeta(Integer id, String url, String path, String component, String name, String icon, MenuMeta parent) {
            this.id = id;
            this.url = url;
            this.path = path;
            this.component = component;
            this.name = name;
            this.icon = icon;
            this.parent = parent;
        }

        private Integer id;
        private String url;
        private String path;
        private String component;
        private String name;
        private String icon;
        private MenuMeta parent;

        public static MenuVo getFilteredMenuVo(MenuMeta root, Map<Integer, MenuMeta> filterMap) {
            //dfs
            MenuVo current = new MenuVo(root.getUrl(),root.getPath(),root.getComponent(),root.getName(),root.getIcon());
            List<MenuVo> children = new ArrayList<>();
            for(MenuMeta menuMeta:MenuMeta.values()){
                if(menuMeta.getParent()!=null&&menuMeta.getParent()==root&&filterMap.get(menuMeta.getId())!=null){
                    MenuVo menuVo = getFilteredMenuVo(menuMeta,filterMap);
                    children.add(menuVo);
                }
            }
            if(children.size()>0){
                current.setChildren(children);
            }
            return current;
        }
    }

    @Getter
    public enum RoleMeta {

        //only level 3 need control
        ADMIN("ROLE_ADMIN", "管理员", new MenuMeta[]{ROOT, BASIC, SOCIAL, READING, TODO, SYSTEM, BASIC_INFO, BASIC_PASSWORD, SOCIAL_CONTACT, READING_LIST, TODO_NOTE, TODO_DAY, TODO_WEEK, SYSTEM_PRIVILEGE}),
        VIP("ROLE_VIP", "VIP用户", new MenuMeta[]{ROOT, BASIC, SOCIAL, READING, TODO, BASIC_INFO, BASIC_PASSWORD, SOCIAL_CONTACT, READING_LIST, TODO_NOTE, TODO_DAY, TODO_WEEK}),
        USER("ROLE_USER", "普通用户", new MenuMeta[]{ROOT, BASIC, SOCIAL, TODO, BASIC_INFO, BASIC_PASSWORD, SOCIAL_CONTACT, TODO_NOTE, TODO_DAY});

        RoleMeta(String name, String nameZh, MenuMeta[] menuMetas) {
            this.name = name;
            this.nameZh = nameZh;
            this.menuMetas = menuMetas;
        }

        public static RoleMeta getByName(String name) {
            for (RoleMeta roleMeta : RoleMeta.values()) {
                if (roleMeta.name.equals(name)) {
                    return roleMeta;
                }
            }
            return null;
        }

        private String name;
        private String nameZh;
        private MenuMeta[] menuMetas;
    }

}
