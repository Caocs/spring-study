package com.java.ccs.spring5.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.java.ccs.spring5.example.demo.jdbc.dao.UserDao;
import com.java.ccs.spring5.example.demo.jdbc.dao.UserDaoImpl;
import com.java.ccs.spring5.example.demo.jdbc.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author caocs
 * @date 2021/10/6
 */
@Configuration
// @ComponentScan(basePackages = "com.java.ccs.spring5.example.demo")
@EnableTransactionManagement // 开启事务
public class TxConfig {

    /**
     * 数据源配置。
     */
    @Bean
    public DruidDataSource druidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test_spring5?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");
        return dataSource;
    }

    /**
     * 根据具体框架，创建事务管理器
     * 如果没有将这个类注入Spring，则会在@EnableTransactionManagement开启事务时报错。
     * NoSuchBeanDefinitionException
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DruidDataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }


    @Bean
    public JdbcTemplate jdbcTemplate(DruidDataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    public UserDao userDao(JdbcTemplate jdbcTemplate) {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(jdbcTemplate);
        return userDao;
    }

    @Bean
    public UserService userService(UserDao userDao) {
        UserService userService = new UserService();
        userService.setUserDao(userDao);
        return userService;
    }


}
