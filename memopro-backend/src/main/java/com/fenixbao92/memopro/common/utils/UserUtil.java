package com.fenixbao92.memopro.common.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class UserUtil {
    public static String getAccountofCurrentUser() {
        return ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public static String[] getRolesofCurrentUser() {
        org.springframework.security.core.userdetails.User frameworkUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<GrantedAuthority> authorities = frameworkUser.getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
    }

}
