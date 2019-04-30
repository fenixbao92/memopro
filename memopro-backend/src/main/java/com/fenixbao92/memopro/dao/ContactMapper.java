package com.fenixbao92.memopro.dao;

import com.fenixbao92.memopro.common.model.Contact;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContactMapper {

    @Select({"<script>",
            "SELECT * FROM Contact",
            "WHERE userId = #{userId} ",
            "<when test='name!=null and name!=\"\"'>",
            "AND name like concat('%',#{name},'%')",
            "</when>",
            "<when test='tag!=null and tag!=\"\"'>",
            "AND tag = #{tag}",
            "</when>",
            "limit #{offset},#{size}",
            "</script>"})
    List<Contact> getList(@Param("userId") Long userId,
                          @Param("name") String name,
                          @Param("tag") String tag,
                          @Param("offset") Long offset,
                          @Param("size") Integer size);

    @Select({"<script>",
            "SELECT count(1)FROM Contact",
            "WHERE userId = #{userId} ",
            "<when test='name!=null and name!=\"\"'>",
            "AND name like concat('%',#{name},'%') ",
            "</when>",
            "<when test='tag!=null and tag!=\"\"'>",
            "AND tag = #{tag}",
            "</when>",
            "</script>"})
    Long getCount(@Param("userId") Long userId,
                  @Param("name") String name,
                  @Param("tag") String tag);


    @Delete({"<script>",
            "delete FROM Contact",
            "WHERE contactId in",
            "<foreach collection ='ids' separator= ',' open='(' close=')' item='id'>",
            "#{id}",
            "</foreach>",
            "</script>"})
    int deleteByIds(@Param("ids") String[] ids);

    @Update({"<script>",
            "update Contact",
            "<set>",

            "<when test='contact.name!=null'>",
            "name = #{contact.name},",
            "</when>",

            "<when test='contact.birthday!=null'>",
            "birthday = #{contact.birthday},",
            "</when>",

            "<when test='contact.phone!=null'>",
            "phone = #{contact.phone},",
            "</when>",

            "<when test='contact.email!=null'>",
            "email = #{contact.email},",
            "</when>",

            "<when test='contact.tag!=null'>",
            "tag = #{contact.tag},",
            "</when>",

            "<when test='contact.remark!=null'>",
            "remark = #{contact.remark},",
            "</when>",

            "<when test='contact.updateTime!=null'>",
            "updateTime = #{contact.updateTime},",
            "</when>",

            "</set>",
            "WHERE contactId = #{contact.contactId}",
            "</script>"})
    int update(@Param("contact") Contact contact);

    @Insert({"<script>",
            "insert into `Contact` (userId,name,birthday,phone,email,tag,remark,updateTime)",
            "values (#{userId},#{name},#{birthday},#{phone},#{email},#{tag},#{remark},#{updateTime})",
            "</script>"})
    int add(Contact contact);
}
