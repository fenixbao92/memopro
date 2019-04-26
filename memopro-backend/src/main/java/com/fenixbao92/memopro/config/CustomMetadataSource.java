package com.fenixbao92.memopro.config;

import com.fenixbao92.memopro.common.constants.PrivilegeConstant;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.*;

@Service
public class CustomMetadataSource implements FilterInvocationSecurityMetadataSource {

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
//        List<MenuVo> allMenuVos = menuService.getAllMenu();
//        for (MenuVo menuVo : allMenuVos) {
//            if (antPathMatcher.match(menuVo.getUrl(), requestUrl)
//                    && menuVo.getRoles().size()>0) {
//                List<Role> roles = menuVo.getRoles();
//                int size = roles.size();
//                String[] values = new String[size];
//                for (int i = 0; i < size; i++) {
//                    values[i] = roles.get(i).getName();
//                }
//                return SecurityConfig.createList(values);
//            }
//        }
        List<String> trustedRoleNames = new ArrayList<>();
        for(PrivilegeConstant.RoleMeta roleMeta : PrivilegeConstant.RoleMeta.values()){
            for(PrivilegeConstant.MenuMeta menuMeta:roleMeta.getMenuMetas()){
                if(antPathMatcher.match(menuMeta.getUrl(), requestUrl)){
                    trustedRoleNames.add(roleMeta.getName());
                    break;
                }
            }
        }
        if(!trustedRoleNames.isEmpty()){
            return SecurityConfig.createList(trustedRoleNames.toArray(new String[0]));
        }
        //没有匹配上的资源，都是登录访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    //unknown
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    //unknown
    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
