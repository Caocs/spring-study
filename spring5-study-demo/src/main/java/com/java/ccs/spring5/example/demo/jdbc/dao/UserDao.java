package com.java.ccs.spring5.example.demo.jdbc.dao;

import com.java.ccs.spring5.example.demo.jdbc.User;

public interface UserDao {

    void add(User user);

    int selectCount();

     void resetMoney(String name, int money);
}
