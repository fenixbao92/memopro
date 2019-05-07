package com.fenixbao92.memopro.controller;

import com.fenixbao92.memopro.common.model.Todo;
import com.fenixbao92.memopro.common.vo.TodoVo;
import com.fenixbao92.memopro.common.vo.Result;
import com.fenixbao92.memopro.service.TodoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Resource
    TodoService todoService;

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
    public Result add(Todo todo) {
        if (todoService.add(todo) == 1) {
            return Result.ok("添加成功!");
        }
        return Result.error("添加失败!");
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String todoIds) {
        if (todoService.deleteByIds(todoIds)) {
            return Result.ok("删除成功!");
        }
        return Result.error("删除失败!");
    }

    @RequestMapping("/update")
    public Result update(Todo todo) {
        System.out.println(1111);
        if (todoService.updateTodo(todo) == 1) {
            return Result.ok("更新成功!");
        }
        return Result.error("更新失败!");
    }

}

