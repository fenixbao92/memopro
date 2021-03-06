package com.fenixbao92.memopro.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fenixbao92.memopro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    CustomUserDetailService userDetailService;
    @Resource
    CustomMetadataSource metadataSource;
    @Resource
    CustomAccessDecisionManager accessDecisionManager;
    @Resource
    CustomAccessDeniedHandler deniedHandler;
    @Resource
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/","/index.html", "/config/register", "/static/**", "/favicon.ico", "/wx/*/**" ,"/wxproxy/**", "/webproxy/*/**");
                                            // login_p
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(metadataSource);
                        o.setAccessDecisionManager(accessDecisionManager);
                        return o;
                    }
                })
                .and()
//                .formLogin().loginPage("/login_p").loginProcessingUrl("/login")
//                .formLogin()
                .formLogin().loginPage("/webproxy/needlogin").loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req,
                                                        HttpServletResponse resp,
                                                        AuthenticationException e) throws IOException {
                        resp.setContentType("application/json;charset=utf-8");
                        String result = null;
                        if (e instanceof BadCredentialsException ||
                                e instanceof UsernameNotFoundException) {
                            result = "账户名或者密码输入错误!";
                        } else if (e instanceof LockedException) {
                            result = "账户被锁定，请联系管理员!";
                        } else if (e instanceof CredentialsExpiredException) {
                            result = "密码过期，请联系管理员!";
                        } else if (e instanceof AccountExpiredException) {
                            result = "账户过期，请联系管理员!";
                        } else if (e instanceof DisabledException) {
                            result = "账户被禁用，请联系管理员!";
                        } else {
                            result = "登录失败!";
                        }
                        resp.setStatus(401);
                        ObjectMapper om = new ObjectMapper();
                        PrintWriter out = resp.getWriter();
                        out.write(om.writeValueAsString(result));
                        out.flush();
                        out.close();
                    }
                })
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req,
                                                        HttpServletResponse resp,
                                                        Authentication auth) throws IOException {
                        resp.setContentType("application/json;charset=utf-8");
                        log.info("登录成功!{}", userService.getCurrentUserVo());
                        ObjectMapper om = new ObjectMapper();
                        PrintWriter out = resp.getWriter();
                        out.write(om.writeValueAsString(userService.getCurrentUserVo()));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication userService) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        log.info("注销成功!{}",userService.getPrincipal());
                        ObjectMapper om = new ObjectMapper();
                        PrintWriter out = resp.getWriter();
                        out.write(om.writeValueAsString("注销成功!"));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and().csrf().disable()
                .exceptionHandling().accessDeniedHandler(deniedHandler);
    }
}
