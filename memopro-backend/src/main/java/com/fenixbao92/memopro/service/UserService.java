package com.fenixbao92.memopro.service;

import com.fenixbao92.memopro.common.model.User;
import com.fenixbao92.memopro.common.utils.UserUtil;
import com.fenixbao92.memopro.common.vo.UserVo;
import com.fenixbao92.memopro.dao.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

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

}
