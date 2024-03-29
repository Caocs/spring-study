package com.java.ccs.spring5.example.demo.aopxml;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author caocs
 * @date 2021/10/5
 * 增强的代理类
 */
public class BookProxy {

    public void before() {
        System.out.println("before");
    }

    public void after() {
        System.out.println("after");
    }

    public void afterReturning() {
        System.out.println("afterReturning");
    }

    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before");
        joinPoint.proceed();
        System.out.println("around after");
    }

}
