package com.java.ccs.spring5.example.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author caocs
 * @date 2021/10/3
 */
@Configuration
@ComponentScan(basePackages = {"com.java.ccs.spring5.example.demo"}) // 包扫描
public class SpringConfig {
}
