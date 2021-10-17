package com.java.ccs.spring5.example.demo.aopanno;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author caocs
 * @date 2021/10/5
 */
@Component
@Aspect
@Order(-1)
public class BookProxy2 {

    @Before(value = "execution(* com.java.ccs.spring5.example.demo.aopanno.Book.add(..))")
    public void before() {
        System.out.println("before BookProxy2");
    }

}
