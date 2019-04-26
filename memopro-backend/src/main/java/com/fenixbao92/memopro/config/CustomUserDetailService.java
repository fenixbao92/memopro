package com.fenixbao92.memopro.config;

import com.fenixbao92.memopro.common.model.User;
import com.fenixbao92.memopro.common.model.User2Role;
import com.fenixbao92.memopro.dao.User2RoleMapper;
import com.fenixbao92.memopro.dao.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Resource
    UserMapper userMapper;

    @Resource
    User2RoleMapper user2RoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 从数据库中取出用户信息
        User user = userMapper.loadUserByAccount(username);

        // 判断用户是否存在
        if(user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }


        // 添加权限
        List<User2Role> rolesByUserId = user2RoleMapper.getRolesByUserId(user.getUserId());
        for (User2Role user2Role : rolesByUserId) {
            authorities.add(new SimpleGrantedAuthority(user2Role.getRole()));
        }

        // 返回UserDetails实现类
        return new org.springframework.security.core.userdetails.User(user.getAccount(), user.getPassword(), authorities);
    }
}
