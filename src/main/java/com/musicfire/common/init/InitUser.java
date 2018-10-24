package com.musicfire.common.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 初始化用户
 * @since 2018/6/20
 */
@Component
@Slf4j
@Order(value=2)
public class InitUser implements CommandLineRunner {


    @Override
    public void run(String... strings) throws Exception {
        try {
            System.out.println("初始化调用");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
