package com.fenixbao92.memopro.controller;

import com.fenixbao92.memopro.common.model.Book;
import com.fenixbao92.memopro.common.vo.BookVo;
import com.fenixbao92.memopro.common.vo.Result;
import com.fenixbao92.memopro.service.BookService;
import com.fenixbao92.memopro.service.oss.OssService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.IOException;
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
                                       @RequestParam(required = false) String status,
                                       @RequestParam(required = false) String tag,
                                       @PathVariable Long offset,
                                       @PathVariable Integer size) {
        List<BookVo> bookVoList = bookService.getList(name, status, tag, offset, size);
        Long count = bookService.getCount(name, status, tag);
        Map<String, Object> map = new HashMap<>();
        map.put("books", bookVoList);
        map.put("count", count);
        return map;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(Book book) {
        if (bookService.add(book) == 1) {
            return Result.ok("添加成功!");
        }
        return Result.error("添加失败!");
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String bookIds) {
        if (bookService.deleteByIds(bookIds)) {
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

}


