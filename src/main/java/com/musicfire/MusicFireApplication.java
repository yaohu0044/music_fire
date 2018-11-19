package com.musicfire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MusicFireApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicFireApplication.class,args);
    }

}
