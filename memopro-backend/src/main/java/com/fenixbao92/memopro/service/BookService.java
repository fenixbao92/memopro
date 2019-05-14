package com.fenixbao92.memopro.service;

import com.fenixbao92.memopro.common.model.Book;
import com.fenixbao92.memopro.common.utils.VoConverter;
import com.fenixbao92.memopro.common.vo.BookVo;
import com.fenixbao92.memopro.dao.BookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Resource
    BookMapper bookMapper;

    @Resource
    UserService userService;

    public List<BookVo> getList(String name, String tag, Long offset, Integer size) {
        Long userId = userService.getCurrentUserIdByAccount();
        List<Book> list = bookMapper.getList(userId, name, tag, offset, size);
        return list.stream().map(VoConverter::forBook).collect(Collectors.toList());
    }

    public Long getCount(String name, String tag) {
        Long userId = userService.getCurrentUserIdByAccount();
        return bookMapper.getCount(userId, name, tag);
    }

    public Boolean deleteByIds(String bookIds) {
        String[] ids = bookIds.split(",");
        return bookMapper.deleteByIds(ids) == ids.length;
    }

    public int updateBook(Book book) {
        book.setUpdateTime(new Date());
        return bookMapper.update(book);
    }


    public int add(Book book) {
        Long userId = userService.getCurrentUserIdByAccount();
        book.setUserId(userId);
        book.setUpdateTime(new Date());
        return bookMapper.add(book);
    }
}
