package com.java.ccs.spring5.example.demo;

import com.java.ccs.spring5.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author caocs
 * @date 2021/10/3
 */
public class ServiceTest {
    @Test
    public void testService(){
        ApplicationContext context =new ClassPathXmlApplicationContext("bean1.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.add();
    }
}
