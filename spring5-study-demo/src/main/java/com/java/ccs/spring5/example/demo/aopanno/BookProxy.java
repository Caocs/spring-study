package com.java.ccs.spring5.example.demo.aopanno;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author caocs
 * @date 2021/10/5
 * 增强的代理类
 */
@Component
@Aspect
@Order(0)
public class BookProxy {
    // 抽取相同的切入点
    @Pointcut(value = "execution(* com.java.ccs.spring5.example.demo.aopanno.Book.add(..))")
    public void pointCut() {

    }

    /**
     * 前置
     */
    @Before(value = "pointCut()")
    public void before() {
        System.out.println("before");
    }

    @After(value = "pointCut()")
    public void after() {
        System.out.println("after");
    }

    @AfterReturning(value = "pointCut()")
    public void afterReturning() {
        System.out.println("afterReturning");
    }

    @AfterThrowing(value = "pointCut()")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    @Around(value = "execution(* com.java.ccs.spring5.example.demo.aopanno.Book.add(..))")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before");
        joinPoint.proceed();
        System.out.println("around after");
    }


}
