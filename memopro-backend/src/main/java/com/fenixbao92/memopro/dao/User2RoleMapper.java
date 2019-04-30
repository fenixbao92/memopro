package com.fenixbao92.memopro.dao;

import com.fenixbao92.memopro.common.model.User2Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface User2RoleMapper {

    @Select("SELECT * FROM `User2Role` WHERE userId = #{userId}")
    List<User2Role> getRolesByUserId(Long userId);

    @Insert({"<script>",
            "insert into `User2Role` (userId,role)",
            "values (#{userId},#{role})",
            "</script>"})
    int add(User2Role user2Role);
}
