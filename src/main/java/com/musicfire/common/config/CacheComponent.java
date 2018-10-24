package com.musicfire.common.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CacheComponent {

    @Autowired
    private CacheManager cacheManager;

    @PostConstruct
    void init(){
    }

    public Cache cacheCode(){
        return getCache("UserSpaceService");
    }

    protected Cache getCache(String name){
      return   this.cacheManager.getCache(name);
    }






}
