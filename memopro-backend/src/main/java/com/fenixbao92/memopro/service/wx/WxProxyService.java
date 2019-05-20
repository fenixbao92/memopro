package com.fenixbao92.memopro.service.wx;

import com.alibaba.fastjson.JSON;
import com.fenixbao92.memopro.common.constants.WxConstant;
import com.fenixbao92.memopro.common.model.wx.WxCodeAuthResult;
import com.fenixbao92.memopro.common.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WxProxyService {
    public WxCodeAuthResult codeAuth(String code) {
        String codeAuthUrl = WxConstant.getCodeAuthUrl(code);
        log.info("ProxyService,codeAuthUrl:{}",codeAuthUrl);
        String result = HttpUtil.get(codeAuthUrl);
        return JSON.parseObject(result,WxCodeAuthResult.class);
    }
}
