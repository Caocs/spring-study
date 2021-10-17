package com.java.ccs.spring5.example.demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.java.ccs.spring5.example.demo.bean.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author caocs
 * @date 2021/10/2
 */
public class HelloSpring {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        User user = context.getBean("user", User.class);
        System.out.println(user);


    }

}
