package com.fenixbao92.memopro;

import com.fenixbao92.memopro.common.model.Book;
import com.fenixbao92.memopro.dao.BookMapper;
import com.fenixbao92.memopro.service.oss.OssService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {

    @Resource
    OssService ossService;

    @Resource
    BookMapper bookMapper;
    @Test
    public void test1() {
        List<Book> list = bookMapper.getList(1l, null, null, null, 0l, 20);
        System.out.println(list);
    }


}
