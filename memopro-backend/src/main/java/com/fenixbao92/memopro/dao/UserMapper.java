package com.fenixbao92.memopro.dao;

import com.fenixbao92.memopro.common.model.Contact;
import com.fenixbao92.memopro.common.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM `User` WHERE account = #{account}")
    User loadUserByAccount(String account);

    @Insert({"<script>",
            "insert into  `User` (account,name,avatar,phone,password,remark,status)",
            "values (#{account},#{name},#{avatar},#{phone},#{password},#{remark},#{status})",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int add(User user);

}
