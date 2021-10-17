package com.java.ccs.test;

import com.java.ccs.spring.CcsApplicationContext;
import com.java.ccs.test.demo.UserService;

/**
 * @author caocs
 * @date 2021/10/6
 */
public class MainTest {

    public static void main(String[] args) {
        CcsApplicationContext context = new CcsApplicationContext(AppConfig.class);
        UserService userService = (UserService) context.getBean("userService");
        userService.test();

    }
}
