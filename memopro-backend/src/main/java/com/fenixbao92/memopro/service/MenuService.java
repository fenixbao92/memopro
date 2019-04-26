package com.fenixbao92.memopro.service;

import com.fenixbao92.memopro.common.constants.PrivilegeConstant;
import com.fenixbao92.memopro.common.model.User2Role;
import com.fenixbao92.memopro.common.vo.MenuVo;
import com.fenixbao92.memopro.dao.User2RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuService {

    @Resource
    User2RoleMapper user2RoleMapper;

    public MenuVo getMenusByUserId(Long userId) {
        List<User2Role> user2Roles = user2RoleMapper.getRolesByUserId(userId);
        Map<Integer,PrivilegeConstant.MenuMeta> menuMap = new HashMap<>();
        for (User2Role user2Role : user2Roles) {
            PrivilegeConstant.RoleMeta roleMeta = PrivilegeConstant.RoleMeta.getByName(user2Role.getRole());
            PrivilegeConstant.MenuMeta[] menuMetas = roleMeta.getMenuMetas();
            for(PrivilegeConstant.MenuMeta menuMeta:menuMetas){
                menuMap.put(menuMeta.getId(),menuMeta);
            }
        }
        return PrivilegeConstant.MenuMeta.getFilteredMenuVo(PrivilegeConstant.MenuMeta.ROOT,menuMap);
    }
}
