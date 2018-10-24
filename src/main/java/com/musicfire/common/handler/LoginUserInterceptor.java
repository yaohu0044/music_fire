package com.musicfire.common.handler;

import com.musicfire.common.config.CacheComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆拦截
 * @since 2018/8/1
 */
@Component
@Slf4j
public class LoginUserInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    CacheComponent cacheComponent;


    //@Value("${Test}")
    boolean Test;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(Test)return true;

        return true;
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    }
}
