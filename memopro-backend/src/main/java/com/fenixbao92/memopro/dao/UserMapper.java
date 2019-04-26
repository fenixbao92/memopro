package com.fenixbao92.memopro.dao;

import com.fenixbao92.memopro.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM `User` WHERE account = #{account}")
    User loadUserByAccount(String account);

}
