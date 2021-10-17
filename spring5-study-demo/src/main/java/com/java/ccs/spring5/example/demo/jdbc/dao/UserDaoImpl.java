package com.java.ccs.spring5.example.demo.jdbc.dao;

import com.java.ccs.spring5.example.demo.jdbc.User;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author caocs
 * @date 2021/10/3
 */
public class UserDaoImpl implements UserDao {

    // set属性注入
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(User user) {
        final String sql = "insert into t_user ( name,password ) values(?,?)";
        Object[] params = {user.getName(), user.getPassword()};
        int update = jdbcTemplate.update(sql, params);
        System.out.println("jdbc dao add.." + update);
    }

    public int selectCount() {
        String sql = "select count(*) from t_user";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

    public void resetMoney(String name, int money) {
        String sql = "update t_user set money=money-? where name=?";
        jdbcTemplate.update(sql, money, name);
    }

}
