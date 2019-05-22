package com.fenixbao92.memopro.dao;

import com.fenixbao92.memopro.common.model.wx.WxUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WxUserMapper {

    @Select({"<script>",
            "SELECT * FROM WxUser",
            "WHERE",
            "<when test='wxUserId!=null'>",
            "AND wxUserId = #{wxUserId}",
            "</when>",
            "<when test='userId!=null'>",
            "AND userId = #{userId}",
            "</when>",
            "<when test='openId!=null and openId!=\"\"'>",
            "AND openId = #{openId}",
            "</when>",
            "limit #{offset},#{size}",
            "</script>"})
    List<WxUser> getList(@Param("wxUserId") Long wxUserId,
                         @Param("userId") Long userId,
                         @Param("openId") String openId,
                         @Param("offset") Long offset,
                         @Param("size") Integer size);

    @Select({"<script>",
            "SELECT count(1)FROM WxUser",
            "WHERE",
            "<when test='wxUserId!=null'>",
            "AND wxUserId = #{wxUserId}",
            "</when>",
            "<when test='userId!=null'>",
            "AND userId = #{userId}",
            "</when>",
            "<when test='openId!=null and openId!=\"\"'>",
            "AND openId = #{openId}",
            "</when>",
            "</script>"})
    Long getCount(@Param("wxUserId") Long wxUserId,
                  @Param("userId") Long userId,
                  @Param("openId") String openId);


    @Delete({"<script>",
            "delete FROM WxUser",
            "WHERE wxUserId in",
            "<foreach collection ='ids' separator= ',' open='(' close=')' item='id'>",
            "#{id}",
            "</foreach>",
            "</script>"})
    int deleteByIds(@Param("ids") String[] ids);

    @Update({"<script>",
            "update WxUser",
            "<set>",

            "<when test='wxUser.userId!=null'>",
            "userId = #{wxUser.userId},",
            "</when>",

            "<when test='wxUser.openId!=null'>",
            "openId = #{wxUser.openId},",
            "</when>",

            "<when test='wxUser.nickName!=null'>",
            "nickName = #{wxUser.nickName},",
            "</when>",

            "<when test='wxUser.gender!=null'>",
            "gender = #{wxUser.gender},",
            "</when>",

            "<when test='wxUser.country!=null'>",
            "country = #{wxUser.country},",
            "</when>",

            "<when test='wxUser.province!=null'>",
            "province = #{wxUser.province},",
            "</when>",

            "<when test='wxUser.city!=null'>",
            "city = #{wxUser.city},",
            "</when>",

            "<when test='wxUser.avatarUrl!=null'>",
            "avatarUrl = #{wxUser.avatarUrl},",
            "</when>",

            "<when test='wxUser.language!=null'>",
            "`language` = #{wxUser.language},",
            "</when>",

            "<when test='wxUser.unionId!=null'>",
            "unionId = #{wxUser.unionId},",
            "</when>",

            "<when test='wxUser.sessionKey!=null'>",
            "sessionKey = #{wxUser.sessionKey},",
            "</when>",

            "<when test='wxUser.lastLoginTime!=null'>",
            "lastLoginTime = #{wxUser.lastLoginTime},",
            "</when>",

            "<when test='wxUser.updateTime!=null'>",
            "updateTime = #{wxUser.updateTime},",
            "</when>",
            "</set>",
            "WHERE wxUserId = #{wxUser.wxUserId}",
            "</script>"})
    int update(@Param("wxUser") WxUser wxUser);

    @Insert({"<script>",
            "insert into `WxUser` (userId,openId,nickName,gender,country,province,city,avatarUrl,`language`,unionId,sessionKey,lastLoginTime,updateTime)",
            "values (#{userId},#{openId},#{nickName},#{gender},#{country},#{province},#{city},#{avatarUrl},#{language},#{unionId},#{sessionKey},#{lastLoginTime},#{updateTime})",
            "</script>"})
    int add(WxUser wxUser);
}
