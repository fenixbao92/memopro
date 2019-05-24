package com.fenixbao92.memopro.service;

import com.fenixbao92.memopro.common.constants.BusinessExceptionEnum;
import com.fenixbao92.memopro.common.exceptions.BusinessException;
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
    private BookMapper bookMapper;

    @Resource
    private UserService userService;

    public List<BookVo> getList(String name, String status, String tag, Long offset, Integer size) {
        Long userId = userService.getCurrentUserId();
        List<Book> list = bookMapper.getList(userId, name, status, tag, offset, size);
        return list.stream().map(VoConverter::forBook).collect(Collectors.toList());
    }

    public Long getCount(String name, String status, String tag) {
        Long userId = userService.getCurrentUserId();
        return bookMapper.getCount(userId, name, status, tag);
    }

    public void deleteByIds(String bookIds) {
        String[] ids = bookIds.split(",");
        if(bookMapper.deleteByIds(ids) != ids.length){
            throw new BusinessException(BusinessExceptionEnum.DATABASE_ERROR);
        }
    }

    public void updateBook(Book book) {
        book.setUpdateTime(new Date());
        if(bookMapper.update(book)!=1){
            throw new BusinessException(BusinessExceptionEnum.DATABASE_ERROR);
        }
    }


    public void add(Book book) {
        Long userId = userService.getCurrentUserId();
        book.setUserId(userId);
        book.setUpdateTime(new Date());
        if(bookMapper.add(book)<1){
            throw new BusinessException(BusinessExceptionEnum.DATABASE_ERROR);
        }
    }

}
