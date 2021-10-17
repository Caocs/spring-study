package com.java.ccs.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author caocs
 * @date 2021/10/6
 * 自定义的Component注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CcsComponent {
    /**
     * 指定bean的名称，可以默认
     */
    String value() default "";

}
