package com.musicfire.common.handler;

import com.musicfire.modular.login.Login;

import javax.servlet.http.HttpServletRequest;

/**
 * @author evan_qb
 * @date 2018/8/29 15:09
 */
public class RequestHolder {
    private static final ThreadLocal<Login> userHolder = new ThreadLocal<>();
 
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();
 
    public static void add(Login login){
        userHolder.set(login);
    }
 
    public static void add(HttpServletRequest request){
        requestHolder.set(request);
    }
 
    public static Login getCurrentUser(){
        return userHolder.get();
    }
 
    public static HttpServletRequest getCurrentRequest(){
        return requestHolder.get();
    }
 
    public static void remove(){
        userHolder.remove();
        requestHolder.remove();
    }
 
}