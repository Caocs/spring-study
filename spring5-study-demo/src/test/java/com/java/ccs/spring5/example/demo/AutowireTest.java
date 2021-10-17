package com.java.ccs.spring5.example.demo;

import com.java.ccs.spring5.example.demo.bean.autowire.Computer;
import com.java.ccs.spring5.example.demo.config.SpringConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author caocs
 * @date 2021/10/3
 */
public class AutowireTest {

    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean-autowire.xml");
        Computer computer = context.getBean("computer", Computer.class);
        System.out.println(computer);
        computer.test();
    }


    @Test
    public void testConfig() {
        // 使用完全注解的方法开发。扫描包的配置在SpringConfig中。
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        Computer computer = context.getBean("computer", Computer.class);
        System.out.println(computer);
        computer.test();
    }


}
