package com.musicfire.common.handler;

import com.musicfire.common.config.redisdao.RedisDao;
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
    private RedisDao redisDao;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean login = request.getServletPath().endsWith("login");
//        if(login){
            return true;
//        }else{
//            String authorization = request.getHeader("authorization");
//            if(redisDao.exist(authorization)){
//                return true;
//            }else{
//                return false;
//            }
//        }
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.err.println(1231231);
    }
}
