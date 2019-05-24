package com.fenixbao92.memopro.controller;

import com.fenixbao92.memopro.common.model.Todo;
import com.fenixbao92.memopro.common.vo.TodoVo;
import com.fenixbao92.memopro.service.TodoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Resource
    private TodoService todoService;

    @RequestMapping(value = "/page/{offset}/{size}")
    public Map<String, Object> getPage(@RequestParam(required = false) String time,
                                       @RequestParam(required = false) String category,
                                       @RequestParam(required = false) String status,
                                       @RequestParam(required = false) String tag,
                                       @PathVariable Long offset,
                                       @PathVariable Integer size) {
        List<TodoVo> todoVoList = todoService.getList(time, category, status, tag, offset, size);
        Long count = todoService.getCount(time, category, status, tag);
        Map<String, Object> map = new HashMap<>();
        map.put("todos", todoVoList);
        map.put("count", count);
        return map;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(Todo todo) {
        todoService.add(todo);
    }

    @RequestMapping("/delete")
    public void delete(@RequestParam String todoIds) {
        todoService.deleteByIds(todoIds);
    }

    @RequestMapping("/update")
    public void update(Todo todo) {
        todoService.updateTodo(todo);
    }

    @RequestMapping("/changeStatus")
    public void changeStatus(Long todoId, String action) {
        todoService.changeStatus(todoId, action);
    }

}

