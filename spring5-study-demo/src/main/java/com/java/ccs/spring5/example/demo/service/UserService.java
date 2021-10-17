package com.java.ccs.spring5.example.demo.service;

import com.java.ccs.spring5.example.demo.dao.UserDao;

/**
 * @author caocs
 * @date 2021/10/3
 */
public class UserService {

    private UserDao userDao;

    // 使用set方法注入
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add() {
        System.out.println("service add..");
        userDao.add();
    }

}
