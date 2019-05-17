package com.fenixbao92.memopro;

import com.fenixbao92.memopro.service.MenuService;
import com.fenixbao92.memopro.service.oss.OssService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OssTest {

    @Resource
    OssService ossService;

    @Test
    public void test1(){
        System.out.println(1111);

    }

    @Test
    public void test2(){
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        System.out.println(antPathMatcher.match("/","/"));
    }

    @Test
    public void test3(){
        ossService.download("key1.txt");
    }

    @Test
    public void test4(){
        ossService.getBucketInfo();
    }

    @Test
    public void test5(){
        ossService.storeStr("key1.txt","hahahaha");
    }

    @Test
    public void test6(){
        ossService.storeFile("pom.xml",new File("pom.xml"));
    }

    @Test
    public void test7(){
        ossService.listObject();
    }

    @Test
    public void test8(){
        ossService.deleteObject("pom.xml");
    }

}
