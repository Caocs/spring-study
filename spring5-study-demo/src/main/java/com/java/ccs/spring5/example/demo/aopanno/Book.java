package com.java.ccs.spring5.example.demo.aopanno;

import org.springframework.stereotype.Component;

/**
 * @author caocs
 * @date 2021/10/5
 */
@Component
public class Book {

    public void add(){
        System.out.println("com.java.ccs.spring5.example.demo.aopanno.Book  :add()");
    }

}
