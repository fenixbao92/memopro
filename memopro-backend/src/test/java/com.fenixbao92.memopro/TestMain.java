package com.fenixbao92.memopro;

import com.fenixbao92.memopro.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMain {

    @Test
    public void test1(){
        System.out.println(1111);

    }

    @Test
    public void test2(){
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        System.out.println(antPathMatcher.match("/","/"));
    }

}
