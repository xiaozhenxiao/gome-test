package com.gome.tbschedule;

import java.util.Properties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.taobao.pamirs.schedule.strategy.TBScheduleManagerFactory;

public class TaskCenter {

    public static void main(String[] args) throws Exception {

        // 初始化Spring
        ApplicationContext ctx = new ClassPathXmlApplicationContext("springContext.xml");
        // 初始化调度工厂
        TBScheduleManagerFactory scheduleManagerFactory = new TBScheduleManagerFactory();

        Properties p = new Properties();
        p.put("zkConnectString", "localhost:2181,localhost:2182,localhost:2183");
        p.put("rootPath", "/tbschedule/test");
        p.put("zkSessionTimeout", "60000");
        p.put("userName", "zookeeper");
        p.put("password", "zookeeper");
        p.put("isCheckParentPath", "true");

        scheduleManagerFactory.setApplicationContext(ctx);
        
        scheduleManagerFactory.init(p);  
    }
}