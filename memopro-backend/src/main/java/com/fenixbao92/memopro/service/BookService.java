package com.fenixbao92.memopro.service;

import com.fenixbao92.memopro.common.model.Book;
import com.fenixbao92.memopro.common.utils.VoConverter;
import com.fenixbao92.memopro.common.vo.BookVo;
import com.fenixbao92.memopro.dao.BookMapper;
import com.fenixbao92.memopro.service.oss.OssService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Resource
    BookMapper bookMapper;

    @Resource
    UserService userService;

    public List<BookVo> getList(String name, String status, String tag, Long offset, Integer size) {
        Long userId = userService.getCurrentUserId();
        List<Book> list = bookMapper.getList(userId, name, status, tag, offset, size);
        return list.stream().map(VoConverter::forBook).collect(Collectors.toList());
    }

    public Long getCount(String name, String status, String tag) {
        Long userId = userService.getCurrentUserId();
        return bookMapper.getCount(userId, name, status, tag);
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
        Long userId = userService.getCurrentUserId();
        book.setUserId(userId);
        book.setUpdateTime(new Date());
        return bookMapper.add(book);
    }

}
