package com.java.ccs.spring5.example.demo;

import com.java.ccs.spring5.example.demo.bean.autowire.Computer;
import com.java.ccs.spring5.example.demo.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author caocs
 * @date 2021/10/6
 */
@RunWith(SpringJUnit4ClassRunner.class) // 单元测试框架
@ContextConfiguration(classes = {SpringConfig.class}) // 加载配置文件
public class SpringJunitTest {

    @Autowired
    Computer computer;

    @Test
    public void test(){
        computer.test();
    }


}
