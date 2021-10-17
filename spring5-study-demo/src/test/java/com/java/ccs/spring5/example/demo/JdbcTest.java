package com.java.ccs.spring5.example.demo;

import com.java.ccs.spring5.example.demo.config.TxConfig;
import com.java.ccs.spring5.example.demo.jdbc.User;
import com.java.ccs.spring5.example.demo.jdbc.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author caocs
 * @date 2021/10/5
 */
public class JdbcTest {

    @Test
    public void testJdbc(){
        ApplicationContext context =new ClassPathXmlApplicationContext("bean-dao.xml");
        UserService userService = context.getBean("userService", UserService.class);
        User user = new User();
        user.setName("lucy");
        user.setPassword("pwd");
        userService.add(user);
    }

    @Test
    public void testJdbcSelectCount(){
        ApplicationContext context =new ClassPathXmlApplicationContext("bean-dao.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.selectCount();
    }

    @Test
    public void testJdbcResetMoney(){
        ApplicationContext context =new ClassPathXmlApplicationContext("bean-dao.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.accountMoney();
    }

    @Test
    public void testJdbcResetMoney2(){
        ApplicationContext context =new ClassPathXmlApplicationContext("bean-dao2.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.accountMoney();
    }

    @Test
    public void testJdbcResetMoney3(){
        ApplicationContext context =new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = context.getBean("userService", UserService.class);
        userService.accountMoney();
    }

}
