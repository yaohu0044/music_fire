package com.musicfire.common.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 初始化应用
 * @since 2018/6/20
 */
@Component
@Slf4j
@Order(value=1)
public class InitAppInfo implements CommandLineRunner {

    @Value("${init.appInfo}")
    private boolean init;//是否执行初始化操作

    @Override
    public void run(String... args) throws Exception {
        if(init){
            intiApp();
        }
    }

    /**
     * 初始化系统应用
     */
    private void intiApp() {
        log.info("初始化应用及资源权限");


        log.info("初始化应用及资源权限完成");
    }


}
