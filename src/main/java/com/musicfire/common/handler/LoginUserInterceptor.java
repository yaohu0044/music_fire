package com.musicfire.common.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.modular.login.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        boolean login = request.getServletPath().endsWith("login");
        if(login){
            return true;
        }else{
            String token = request.getHeader("token");
            if(redisDao.exist(token)){
                JSON parse = (JSON)JSON.parse(redisDao.get(token));
                Login l = JSONObject.toJavaObject(parse, Login.class);
                RequestHolder.add(l);
                RequestHolder.add(request);
                return true;
            }else{
                return true;
            }
        }
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RequestHolder.remove();
    }
}
