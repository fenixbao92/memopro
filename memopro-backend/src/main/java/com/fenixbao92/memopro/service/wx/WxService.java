package com.fenixbao92.memopro.service.wx;

import com.alibaba.fastjson.JSON;
import com.fenixbao92.memopro.common.constants.BusinessExceptionEnum;
import com.fenixbao92.memopro.common.exceptions.BusinessException;
import com.fenixbao92.memopro.common.model.User;
import com.fenixbao92.memopro.common.model.wx.WxCodeAuthResult;
import com.fenixbao92.memopro.common.model.wx.WxUser;
import com.fenixbao92.memopro.common.utils.BeanMapper;
import com.fenixbao92.memopro.common.vo.wx.WxRegisterVo;
import com.fenixbao92.memopro.config.redis.RedisTool;
import com.fenixbao92.memopro.dao.UserMapper;
import com.fenixbao92.memopro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
@Slf4j
public class WxService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private WxProxyService wxProxyService;

    @Resource
    private RedisTool redisTool;

    @Resource
    private WxUserService wxUserService;

    @Resource
    private UserService userService;

    public String codeAuth(String code) {
        WxCodeAuthResult wxCodeAuthResult = wxProxyService.codeAuth(code);
        String sessionId = DigestUtils.md5Hex(JSON.toJSONString(wxCodeAuthResult));

        wxUserService.confirmCodeAuth(wxCodeAuthResult.getOpenid(), wxCodeAuthResult.getSession_key());

        redisTool.setex(sessionId, 7200, wxCodeAuthResult.getOpenid());
        return sessionId;
    }

    public void register(WxRegisterVo wxRegisterVo) {
        log.info("wx register:{}", wxRegisterVo);

        String sessionValue = redisTool.get(wxRegisterVo.getSessionId());
        if(wxRegisterVo.getSessionId()==null || StringUtils.isEmpty(sessionValue)) {
            throw new BusinessException(BusinessExceptionEnum.WX_SESSION_INVALID);
        }
        wxRegisterVo.setOpenId(sessionValue);

        String account = wxRegisterVo.getAccount();
        String password = wxRegisterVo.getPassword();
        User user = userMapper.loadUserByAccount(account);
        if (user != null) {
            log.info("wx register:{},already exit,to bind.", wxRegisterVo.getNickName());
            if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {
                bindUser(wxRegisterVo, user.getUserId());
            } else {
                throw new BusinessException("密码不正确");
            }
        } else {
            log.info("wx register:{},not exit,to create.", wxRegisterVo.getNickName());
            Long generatedUserId = userService.register(account,"1qa2ws3edfenix");
            bindUser(wxRegisterVo,generatedUserId);
        }
    }

    private void bindUser(WxRegisterVo wxRegisterVo, Long userId) {
        WxUser wxUser = wxUserService.getByOpenId(wxRegisterVo.getOpenId());
        BeanMapper.copy(wxRegisterVo,wxUser);
        wxUser.setUserId(userId);
        log.info("wx bindUser new:{}<->{}", wxRegisterVo.getAccount(),wxRegisterVo.getOpenId());
        wxUserService.updateWxUser(wxUser);
    }

    public WxUser getbindinfo(String sessionId) {
        String sessionValue = redisTool.get(sessionId);
        if(sessionValue==null){
            throw new BusinessException("sessionID 无效");
        }
        WxUser byOpenId = wxUserService.getByOpenId(sessionValue);
        if(byOpenId.getUserId()==null){
            throw new BusinessException("尚未绑定");
        }
        byOpenId.setSessionKey(null);
        return byOpenId;
    }
}
