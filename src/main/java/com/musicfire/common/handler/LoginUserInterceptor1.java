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
import java.util.Arrays;
import java.util.List;

/**
 * 登陆拦截
 * @since 2018/8/1
 */
@Component
@Slf4j
public class LoginUserInterceptor1 extends HandlerInterceptorAdapter {
//    @Autowired
//    private RedisDao redisDao;
//
//    private final List<String> filters = Arrays.asList("/login",
//            "/goodsList.html","/goodsDetails.html",
//            "/paymentResult.html","/orderInfo.html","/mobile","/wxpay",
//            "authorize","userInfo","notify","mobilePay","mobile_register","wechat"
//            ,"alipay"
//    );
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
////        boolean login = request.getServletPath().endsWith("login");
//        String token = request.getHeader("token");
//
//        if(token!=null ){
//            if(redisDao.exist(token)){
//                JSON parse = (JSON)JSON.parse(redisDao.get(token));
//                Login l = JSONObject.toJavaObject(parse, Login.class);
//                RequestHolder.add(l);
//                RequestHolder.add(request);
//                return true;
//            }else{
//                String servletPath = request.getServletPath();
//                for (String l:filters ) {
//                    boolean b = servletPath.contains(l);
//                    if(b){
//                        return true;
//                    }
//                }
//            }
//        }else{
//            String servletPath = request.getServletPath();
//            for (String l:filters ) {
//                boolean b = servletPath.contains(l);
//                if(b){
//                    return true;
//                }
//            }
//            System.out.println("访问后缀："+servletPath);
//        }
//        return false;
//    }
//
//    @Override
//    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        RequestHolder.remove();
//    }
}
