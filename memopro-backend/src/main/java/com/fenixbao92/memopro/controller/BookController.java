package com.fenixbao92.memopro.controller;

import com.fenixbao92.memopro.common.exceptions.BusinessException;
import com.fenixbao92.memopro.common.model.Book;
import com.fenixbao92.memopro.common.vo.BookVo;
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
    public void add(Book book) {
        bookService.add(book);
    }

    @RequestMapping("/delete")
    public void delete(@RequestParam String bookIds) {
        bookService.deleteByIds(bookIds);
    }

    @RequestMapping("/update")
    public void update(Book book) {
        bookService.updateBook(book);
    }

}


