package com.java.ccs.spring5.example.demo;

import com.java.ccs.spring5.example.demo.aopanno.Book;
import com.java.ccs.spring5.example.demo.dao.UserDao;
import com.java.ccs.spring5.example.demo.dao.UserDaoImpl;
import com.java.ccs.spring5.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author caocs
 * @date 2021/10/5
 */
public class ProxyTest {

    @Test
    public void testJDKProxy(){
        Class[] clazzArray = {UserDao.class};
        final UserDaoImpl userDaoImpl = new UserDaoImpl();
        UserDao userDao = (UserDao)Proxy.newProxyInstance(
                ProxyTest.class.getClassLoader(),
                clazzArray,
                new InvocationHandler(){
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("我在前面执行了");
                        Object result = method.invoke(userDaoImpl,args);
                        System.out.println("我后面执行完了");
                        return result;
                    }
                });
        userDao.add();
    }

    @Test
    public void testAopAnnotation(){
        ApplicationContext context =new ClassPathXmlApplicationContext("bean-aop.xml");
        Book book = context.getBean("book", Book.class);
        book.add();
    }

    @Test
    public void testAopXml(){
        ApplicationContext context =new ClassPathXmlApplicationContext("bean-aopxml.xml");
        com.java.ccs.spring5.example.demo.aopxml.Book book
                = context.getBean("book", com.java.ccs.spring5.example.demo.aopxml.Book.class);
        book.add();
    }

}
