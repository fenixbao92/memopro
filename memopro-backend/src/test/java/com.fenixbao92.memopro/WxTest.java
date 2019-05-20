package com.fenixbao92.memopro;

import com.fenixbao92.memopro.common.model.wx.WxCodeAuthResult;
import com.fenixbao92.memopro.service.wx.WxProxyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxTest {

    @Resource
    WxProxyService wxProxyService;

    @Test
    public void test1(){
        WxCodeAuthResult xxxx = wxProxyService.codeAuth("xxxx");
        System.out.println(xxxx);
    }

}