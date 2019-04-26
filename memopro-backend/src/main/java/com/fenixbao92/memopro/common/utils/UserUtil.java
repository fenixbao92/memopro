package com.fenixbao92.memopro.common.utils;

import com.fenixbao92.memopro.common.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    public static String getAccountofCurrentUser() {
        return ((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
