package com.java.ccs.spring5.example.demo.bean;

import lombok.Data;

/**
 * @author caocs
 * @date 2021/10/2
 */
@Data
public class Order {

    private String name;
    private String address;

    public Order(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
