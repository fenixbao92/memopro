package com.fenixbao92.memopro.controller;
import com.alibaba.fastjson.JSON;
import com.fenixbao92.memopro.common.model.Book;
import com.fenixbao92.memopro.common.model.Todo;
import com.fenixbao92.memopro.common.utils.BeanMapper;
import com.fenixbao92.memopro.common.vo.BookVo;
import com.fenixbao92.memopro.common.vo.Result;
import com.fenixbao92.memopro.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    BookService bookService;

    @RequestMapping(value = "/page/{offset}/{size}")
    public Map<String, Object> getPage(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String tag,
                                       @PathVariable Long offset,
                                       @PathVariable Integer size) {
        List<BookVo> bookVoList = bookService.getList(name, tag, offset, size);
        Long count = bookService.getCount(name, tag);
        Map<String, Object> map = new HashMap<>();
        map.put("books", bookVoList);
        map.put("count", count);
        return map;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(Book book) {
        if (bookService.add(book) == 1) {
            return Result.ok("添加成功!");
        }
        return Result.error("添加失败!");
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String bookIds) {
        if(bookService.deleteByIds(bookIds)){
            return Result.ok("删除成功!");
        }
        return Result.error("删除失败!");
    }

    @RequestMapping("/update")
    public Result update(Book book) {
        if (bookService.updateBook(book) == 1) {
            return Result.ok("更新成功!");
        }
        return Result.error("更新失败!");
    }


    public static void main(String[] args){
        Todo todo = new Todo();
        Todo todo1 = new Todo();
        todo.setName("dddd");
        System.out.println(JSON.toJSON(todo));
        System.out.println(JSON.toJSON(todo1));
        BeanMapper.copy(todo,todo1);
        System.out.println(JSON.toJSON(todo1));
    }
}


