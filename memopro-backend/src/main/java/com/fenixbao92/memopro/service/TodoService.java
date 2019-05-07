package com.fenixbao92.memopro.service;

import com.fenixbao92.memopro.common.model.Todo;
import com.fenixbao92.memopro.common.utils.VoConverter;
import com.fenixbao92.memopro.common.vo.TodoVo;
import com.fenixbao92.memopro.dao.TodoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Resource
    TodoMapper todoMapper;

    @Resource
    UserService userService;

    public List<TodoVo> getList(String time, String category, String status, String tag, Long offset, Integer size) {
        Long userId = userService.getCurrentUserIdByAccount();
        List<Todo> list = todoMapper.getList(userId, time, category, status, tag, offset, size);
        return list.stream().map(VoConverter::forTodo).collect(Collectors.toList());
    }

    public Long getCount(String time, String category, String status, String tag) {
        Long userId = userService.getCurrentUserIdByAccount();
        return todoMapper.getCount(userId, time, category, status, tag);
    }

    public Boolean deleteByIds(String todoIds) {
        String[] ids = todoIds.split(",");
        return todoMapper.deleteByIds(ids) == ids.length;
    }

    public int updateTodo(Todo todo) {
        todo.setUpdateTime(new Date());
        return todoMapper.update(todo);
    }


    public int add(Todo todo) {
        Long userId = userService.getCurrentUserIdByAccount();
        todo.setUserId(userId);
        todo.setUpdateTime(new Date());
        Long count = getCount(todo.getTime(),todo.getCategory(),todo.getStatus(),todo.getTag());
        todo.setIndex(todo.getTime()+"#"+(count+1));
        System.out.println(todo);
        return todoMapper.add(todo);
    }
}
