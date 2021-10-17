package com.example.demo.framework;

/**
 * 创建代理类的接口
 */
public interface ProxyCreator {

    /**
     * 创建代理类
     * @param type
     * @return
     */
    Object createProxy(Class<?> type);

}
