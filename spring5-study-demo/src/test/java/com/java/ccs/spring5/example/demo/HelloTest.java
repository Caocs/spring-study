package com.java.ccs.spring5.example.demo;

import com.java.ccs.spring5.example.demo.bean.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author caocs
 * @date 2021/10/3
 */
public class HelloTest {

    @Test
    public void testUser() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        User user = context.getBean("user", User.class);
        System.out.println(user);
    }

    @Test
    public void testOrder() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        Order order = context.getBean("order", Order.class);
        System.out.println(order);
    }

    @Test
    public void testBook() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        Book book = context.getBean("book", Book.class);
        System.out.println(book);
    }

    @Test
    public void testStudent() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        Student student = context.getBean("student", Student.class);
        System.out.println(student);
    }

    @Test
    public void testPerson() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
        Person person = context.getBean("person", Person.class);
        System.out.println(person);
    }

    @Test
    public void testFactoryBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean3.xml");
        // 这里根据FactoryBean得到的最终类型，是User类型的对象。
        User user = context.getBean("myBean", User.class);
        System.out.println(user);
    }

}
