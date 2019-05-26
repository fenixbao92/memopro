package com.fenixbao92.memopro.controller.wx;

import com.fenixbao92.memopro.common.model.Book;
import com.fenixbao92.memopro.common.vo.BookVo;
import com.fenixbao92.memopro.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/book")
public class WxBookController {

    @Resource
    BookService bookService;

    @RequestMapping(value = "/page/{offset}/{size}")
    public List<BookVo> getPage(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String status,
                                       @RequestParam(required = false) String tag,
                                       @PathVariable Long offset,
                                       @PathVariable Integer size) {
        return bookService.getList(name, status, tag, offset, size);
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
