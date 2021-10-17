package com.java.ccs.spring5.example.demo.bean.factorybean;

import com.java.ccs.spring5.example.demo.bean.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author caocs
 * @date 2021/10/3
 */
public class MyBean implements FactoryBean<User> {

    public void test(){
        System.out.println("hhhhhhhh");
    }
    /**
     * 通过该方法返回最终的类型bean
     */
    @Override
    public User getObject() throws Exception {
        User user = new User();
        user.setName("mybean");
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
