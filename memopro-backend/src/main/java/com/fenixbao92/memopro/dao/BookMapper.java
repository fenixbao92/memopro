package com.fenixbao92.memopro.dao;

import com.fenixbao92.memopro.common.model.Book;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select({"<script>",
            "SELECT * FROM Book",
            "WHERE userId = #{userId} ",
            "<when test='name!=null and name!=\"\"'>",
            "AND name like concat('%',#{name},'%')",
            "</when>",
            "<when test='status!=null and status!=\"\"'>",
            "AND status = #{status}",
            "</when>",
            "<when test='tag!=null and tag!=\"\"'>",
            "AND tag = #{tag}",
            "</when>",
            "limit #{offset},#{size}",
            "</script>"})
    List<Book> getList(@Param("userId") Long userId,
                       @Param("name") String name,
                       @Param("status") String status,
                       @Param("tag") String tag,
                       @Param("offset") Long offset,
                       @Param("size") Integer size);

    @Select({"<script>",
            "SELECT count(1)FROM Book",
            "WHERE userId = #{userId} ",
            "<when test='name!=null and name!=\"\"'>",
            "AND name like concat('%',#{name},'%') ",
            "</when>",
            "<when test='status!=null and status!=\"\"'>",
            "AND status = #{status}",
            "</when>",
            "<when test='tag!=null and tag!=\"\"'>",
            "AND tag = #{tag}",
            "</when>",
            "</script>"})
    Long getCount(@Param("userId") Long userId,
                  @Param("name") String name,
                  @Param("status") String status,
                  @Param("tag") String tag);

    @Delete({"<script>",
            "delete FROM Book",
            "WHERE bookId in",
            "<foreach collection ='ids' separator= ',' open='(' close=')' item='id'>",
            "#{id}",
            "</foreach>",
            "</script>"})
    int deleteByIds(@Param("ids") String[] ids);

    @Update({"<script>",
            "update Book",
            "<set>",

            "<when test='book.index!=null'>",
            "`index` = #{book.index},",
            "</when>",

            "<when test='book.name!=null'>",
            "name = #{book.name},",
            "</when>",

            "<when test='book.url!=null'>",
            "url = #{book.url},",
            "</when>",

            "<when test='book.thoughts!=null'>",
            "thoughts = #{book.thoughts},",
            "</when>",

            "<when test='book.status!=null'>",
            "status = #{book.status},",
            "</when>",

            "<when test='book.tag!=null'>",
            "tag = #{book.tag},",
            "</when>",

            "<when test='book.remark!=null'>",
            "remark = #{book.remark},",
            "</when>",

            "<when test='book.updateTime!=null'>",
            "updateTime = #{book.updateTime},",
            "</when>",

            "</set>",
            "WHERE bookId = #{book.bookId}",
            "</script>"})
    int update(@Param("book") Book book);

    @Insert({"<script>",
            "insert into `Book` (userId,`index`,name,url,thoughts,status,tag,remark,updateTime)",
            "values (#{userId},#{index},#{name},#{url},#{thoughts},#{status},#{tag},#{remark},#{updateTime})",
            "</script>"})
    int add(Book book);
}
