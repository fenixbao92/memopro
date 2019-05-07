package com.fenixbao92.memopro.dao;

import com.fenixbao92.memopro.common.model.Site;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SiteMapper {

    @Select({"<script>",
            "SELECT * FROM Site",
            "WHERE userId = #{userId} ",
            "<when test='name!=null and name!=\"\"'>",
            "AND name like concat('%',#{name},'%')",
            "</when>",
            "<when test='tag!=null and tag!=\"\"'>",
            "AND tag = #{tag}",
            "</when>",
            "limit #{offset},#{size}",
            "</script>"})
    List<Site> getList(@Param("userId") Long userId,
                       @Param("name") String name,
                       @Param("tag") String tag,
                       @Param("offset") Long offset,
                       @Param("size") Integer size);

    @Select({"<script>",
            "SELECT count(1)FROM Site",
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
            "delete FROM Site",
            "WHERE siteId in",
            "<foreach collection ='ids' separator= ',' open='(' close=')' item='id'>",
            "#{id}",
            "</foreach>",
            "</script>"})
    int deleteByIds(@Param("ids") String[] ids);

    @Update({"<script>",
            "update Site",
            "<set>",

            "<when test='site.name!=null'>",
            "name = #{site.name},",
            "</when>",

            "<when test='site.url!=null'>",
            "url = #{site.url},",
            "</when>",

            "<when test='site.account!=null'>",
            "account = #{site.account},",
            "</when>",

            "<when test='site.password!=null'>",
            "password = #{site.password},",
            "</when>",

            "<when test='site.tag!=null'>",
            "tag = #{site.tag},",
            "</when>",

            "<when test='site.remark!=null'>",
            "remark = #{site.remark},",
            "</when>",

            "<when test='site.updateTime!=null'>",
            "updateTime = #{site.updateTime},",
            "</when>",

            "</set>",
            "WHERE siteId = #{site.siteId}",
            "</script>"})
    int update(@Param("site") Site site);

    @Insert({"<script>",
            "insert into `Site` (userId,name,url,account,password,tag,remark,updateTime)",
            "values (#{userId},#{name},#{url},#{account},#{password},#{tag},#{remark},#{updateTime})",
            "</script>"})
    int add(Site site);
}
