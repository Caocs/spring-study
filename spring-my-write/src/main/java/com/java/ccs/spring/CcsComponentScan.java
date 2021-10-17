package com.java.ccs.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author caocs
 * @date 2021/10/6
 * 自定义的ComponentScan注解，用来扫包。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CcsComponentScan {
    /**
     * 用于指定需要扫描的包路径
     */
    String value();

}
