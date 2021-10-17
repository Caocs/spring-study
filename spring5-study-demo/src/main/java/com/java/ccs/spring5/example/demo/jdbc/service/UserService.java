package com.java.ccs.spring5.example.demo.jdbc.service;

import com.java.ccs.spring5.example.demo.jdbc.User;
import com.java.ccs.spring5.example.demo.jdbc.dao.UserDao;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    public void add(User user) {
        System.out.println("service add..");
        userDao.add(user);
    }

    public void selectCount() {
        int count = userDao.selectCount();
        System.out.println("service selectCount..." + count);
    }

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.REPEATABLE_READ,
            timeout = -1,
            readOnly = false)
    public void accountMoney() {
        userDao.resetMoney("ccs", 100);
        int i = 10 / 0;
        userDao.resetMoney("lucy", -100);
    }

}
