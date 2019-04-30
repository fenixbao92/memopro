package com.fenixbao92.memopro.service;

import com.fenixbao92.memopro.common.constants.PrivilegeConstant;
import com.fenixbao92.memopro.common.model.User;
import com.fenixbao92.memopro.common.model.User2Role;
import com.fenixbao92.memopro.common.utils.UserUtil;
import com.fenixbao92.memopro.common.vo.UserVo;
import com.fenixbao92.memopro.dao.User2RoleMapper;
import com.fenixbao92.memopro.dao.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    User2RoleMapper user2RoleMapper;

    public User getCurrentUserByAccount(){
        return userMapper.loadUserByAccount(UserUtil.getAccountofCurrentUser());
    }

    public UserVo getCurrentUserVoByAccount(){
        User user= getCurrentUserByAccount();
        String [] roles = UserUtil.getRolesofCurrentUser();
        return new UserVo(user.getAccount(),user.getAvatar(),roles,user.getStatus());
    }

    public Long getCurrentUserIdByAccount(){
        User user= getCurrentUserByAccount();
        return user.getUserId();
    }

    public int register(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        if(userMapper.add(user)<0){
            return -1;
        }
        User2Role user2Role = new User2Role();
        user2Role.setUserId(user.getUserId());
        user2Role.setRole(PrivilegeConstant.RoleMeta.USER.getName());
        return user2RoleMapper.add(user2Role);
    }

}
