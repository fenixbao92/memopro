package com.fenixbao92.memopro.service.wx;

import com.alibaba.fastjson.JSON;
import com.fenixbao92.memopro.common.model.wx.WxCodeAuthResult;
import com.fenixbao92.memopro.config.redis.RedisTool;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WxService {

    @Resource
    WxProxyService wxProxyService;

    @Resource
    RedisTool redisTool;

    @Resource
    WxUserService wxUserService;

    public String codeAuth(String code) {
        WxCodeAuthResult wxCodeAuthResult = wxProxyService.codeAuth(code);
        String sessionId = DigestUtils.md5Hex(JSON.toJSONString(wxCodeAuthResult));

        wxUserService.confirmCodeAuth(wxCodeAuthResult.getOpenid(),wxCodeAuthResult.getSession_key());

        redisTool.setex(sessionId,7200,wxCodeAuthResult.getOpenid());
        return sessionId;
    }
}
