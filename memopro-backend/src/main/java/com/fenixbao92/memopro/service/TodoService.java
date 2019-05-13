package com.fenixbao92.memopro.service;

import com.fenixbao92.memopro.common.exceptions.BussnessException;
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
        List<Todo> list = todoMapper.getList(null,userId, time, category, status, tag, offset, size);
        return list.stream().map(VoConverter::forTodo).collect(Collectors.toList());
    }

    public Long getCount(String time, String category, String status, String tag) {
        Long userId = userService.getCurrentUserIdByAccount();
        return todoMapper.getCount(null,userId, time, category, status, tag);
    }

    public Boolean deleteByIds(String todoIds) {
        String[] ids = todoIds.split(",");
        return todoMapper.deleteByIds(ids) == ids.length;
    }

    public Todo getById(Long todoId){
        List<Todo> old = todoMapper.getList(todoId,null,null,
                null,null,null,0l,1);
        if(old!=null&&old.size()>0){
            return old.get(0);
        }
        return null;
    }

    public int updateTodo(Todo todo) {
        if (todo.getStatus().equals("未开始")) {
            todo.setStartTime(null);
            todo.setEndTime(null);
        } else if (todo.getStatus().equals("进行中")) {
            todo.setStartTime(new Date());
            todo.setEndTime(null);
        } else if (todo.getStatus().equals("已完成")) {
            todo.setEndTime(new Date());
        }
        todo.setUpdateTime(new Date());
        return todoMapper.update(todo);
    }


    public int add(Todo todo) {
        Long userId = userService.getCurrentUserIdByAccount();
        todo.setUserId(userId);
        todo.setUpdateTime(new Date());
        Long count = getCount(todo.getTime(), null, null, null);
        todo.setIndex(todo.getTime() + "#" + (count + 1));
        return todoMapper.add(todo);
    }

    public int changeStatus(Long todoId, String action) {
        Todo old = getById(todoId);
        if(old==null) throw new BussnessException("待办不存在");
        String status = old.getStatus();
        Todo toUpdate = new Todo();
        toUpdate.setTodoId(todoId);
        if (status.equals("未开始")) {
            if (action.equals("start")) {
                toUpdate.setStatus("进行中");
                toUpdate.setStartTime(new Date());
                toUpdate.setLatestStartTime(new Date());
                toUpdate.setCostMillis(0l);
            }
        } else if (status.equals("进行中")) {
            Date now = new Date();
            Date latestStartTime  = old.getLatestStartTime();
            if (action.equals("pause")) {
                toUpdate.setStatus("暂停中");
                toUpdate.setCostMillis(old.getCostMillis()+(now.getTime()-latestStartTime.getTime()));
            } else if (action.equals("finish")) {
                toUpdate.setStatus("已完成");
                toUpdate.setEndTime(new Date());
                toUpdate.setCostMillis(old.getCostMillis()+(now.getTime()-latestStartTime.getTime()));
            }
        } else if (status.equals("暂停中")) {
            if (action.equals("resume")) {
                toUpdate.setStatus("进行中");
                toUpdate.setLatestStartTime(new Date());
            }
        } else if (status.equals("已完成")) {
        }
        return todoMapper.update(toUpdate);
    }
}
