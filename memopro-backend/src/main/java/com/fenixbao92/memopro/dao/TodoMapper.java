package com.fenixbao92.memopro.dao;

import com.fenixbao92.memopro.common.model.Todo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TodoMapper {

    @Select({"<script>",
            "SELECT * FROM Todo",
            "WHERE userId = #{userId} ",
            "<when test='time!=null and time!=\"\"'>",
            "AND time = #{time}",
            "</when>",
            "<when test='category!=null and category!=\"\"'>",
            "AND category = #{category}",
            "</when>",
            "<when test='status!=null and status!=\"\"'>",
            "AND status = #{status}",
            "</when>",
            "<when test='tag!=null and tag!=\"\"'>",
            "AND tag = #{tag}",
            "</when>",
            "limit #{offset},#{size}",
            "</script>"})
    List<Todo> getList(@Param("userId") Long userId,
                       @Param("time") String time,
                       @Param("category") String category,
                       @Param("status") String status,
                       @Param("tag") String tag,
                       @Param("offset") Long offset,
                       @Param("size") Integer size);

    @Select({"<script>",
            "SELECT count(1)FROM Todo",
            "WHERE userId = #{userId} ",
            "<when test='time!=null and time!=\"\"'>",
            "AND time = #{time}",
            "</when>",
            "<when test='category!=null and category!=\"\"'>",
            "AND category = #{category}",
            "</when>",
            "<when test='status!=null and status!=\"\"'>",
            "AND status = #{status}",
            "</when>",
            "<when test='tag!=null and tag!=\"\"'>",
            "AND tag = #{tag}",
            "</when>",
            "</script>"})
    Long getCount(@Param("userId") Long userId,
                  @Param("time") String time,
                  @Param("category") String category,
                  @Param("status") String status,
                  @Param("tag") String tag);


    @Delete({"<script>",
            "delete FROM Todo",
            "WHERE todoId in",
            "<foreach collection ='ids' separator= ',' open='(' close=')' item='id'>",
            "#{id}",
            "</foreach>",
            "</script>"})
    int deleteByIds(@Param("ids") String[] ids);

    @Update({"<script>",
            "update Todo",
            "<set>",

            "<when test='todo.name!=null'>",
            "name = #{todo.name},",
            "</when>",

            "<when test='todo.category!=null'>",
            "category = #{todo.category},",
            "</when>",

            "<when test='todo.time!=null'>",
            "time = #{todo.time},",
            "</when>",

            "<when test='todo.status!=null'>",
            "status = #{todo.status},",
            "</when>",

            "<when test='todo.tag!=null'>",
            "tag = #{todo.tag},",
            "</when>",

            "<when test='todo.parentId!=null'>",
            "parentId = #{todo.parentId},",
            "</when>",

            "<when test='todo.startTime!=null'>",
            "startTime = #{todo.startTime},",
            "</when>",

            "<when test='todo.endTime!=null'>",
            "endTime = #{todo.endTime},",
            "</when>",

            "<when test='todo.updateTime!=null'>",
            "updateTime = #{todo.updateTime},",
            "</when>",

            "</set>",
            "WHERE todoId = #{todo.todoId}",
            "</script>"})
    int update(@Param("todo") Todo todo);

    @Insert({"<script>",
            "insert into `Todo` (userId, `index`,name,category,time,status,tag,parentId,startTime,endTime,updateTime)",
            "values (#{userId},#{index},#{name},#{category},#{time},#{status},#{tag},#{parentId},#{startTime},#{endTime},#{updateTime})",
            "</script>"})
    int add(Todo todo);
}
