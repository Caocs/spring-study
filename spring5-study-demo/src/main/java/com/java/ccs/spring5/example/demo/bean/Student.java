package com.java.ccs.spring5.example.demo.bean;

import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author caocs
 * @date 2021/10/3
 */
@Data
@ToString
public class Student {

    private String[] names;

    private List<String> list;

    private Map<String,String> map;

    private Set<String> set;

    private List<User> userList;



}
