package com.java.ccs.spring5.example.demo.bean.autowire;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author caocs
 * @date 2021/10/3
 */
@Data
@Component
public class Computer {
    private String name;

    public void test() {
        System.out.println(name);
    }
}
