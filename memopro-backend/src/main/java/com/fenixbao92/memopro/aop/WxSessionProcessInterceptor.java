package com.fenixbao92.memopro.aop;

import com.fenixbao92.memopro.common.model.User;
import com.fenixbao92.memopro.common.model.wx.WxUser;
import com.fenixbao92.memopro.config.CustomUserDetailService;
import com.fenixbao92.memopro.config.redis.RedisTool;
import com.fenixbao92.memopro.dao.UserMapper;
import com.fenixbao92.memopro.service.UserService;
import com.fenixbao92.memopro.service.wx.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
@Slf4j
public class WxSessionProcessInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTool redisTool;

    @Resource
    private WxUserService wxUserService;

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {       //请求进入这个拦截器
        String sessionId = request.getHeader("sessionId");
        if (sessionId == null) {
            return true;
        }
        String openId = redisTool.get(sessionId);
        if (openId == null) {
            return true;
        }
        log.info("wx request received ==== sessionId:{},openId:{}",sessionId,openId);
        WxUser wxUser = wxUserService.getByOpenId(openId);
        User user = userMapper.loadUserByUserId(wxUser.getUserId());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getAccount(), user.getPassword(), new ArrayList<>());

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String path = request.getRequestURI();
        path = path.replace("^/wx/", "");
        request.getRequestDispatcher(path).forward(request, response);
        return true;        //有的话就继续操作
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}