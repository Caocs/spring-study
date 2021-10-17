package com.java.ccs.spring5.example.demo;

import com.java.ccs.spring5.example.demo.bean.factorybean.MyBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author caocs
 * @date 2021/10/6
 */
@ExtendWith(SpringExtension.class) // 单元测试框架
@ContextConfiguration("classpath:bean3.xml") // 加载配置文件
public class SpringJunit5Test {

    @Autowired
    MyBean myBean;

    @Test
    public void test(){
        myBean.test();
    }


}
