package com.musicfire.modular.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail teatQuartzDetail(){
        return JobBuilder.newJob(TestQuartz.class).withIdentity("sendEmail").storeDurably().build();
    }

    @Bean
    public CronTrigger testQuartzTrigger(){
        return TriggerBuilder.//和之前的 SimpleTrigger 类似，现在的 CronTrigger 也是一个接口，通过 Tribuilder 的 build()方法来实例化
        newTrigger().
                withIdentity("cronTrigger", "cronTrigger").
                forJob("sendEmail").
                withSchedule(CronScheduleBuilder.cronSchedule("0 0 9 * * ?")). //在任务调度器中，使用任务调度器的 CronScheduleBuilder 来生成一个具体的 CronTrigger 对象
                build();
    }
}