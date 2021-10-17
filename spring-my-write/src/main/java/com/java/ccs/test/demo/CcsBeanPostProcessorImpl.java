package com.java.ccs.test.demo;

import com.java.ccs.spring.CcsBeanPostProcessor;
import com.java.ccs.spring.CcsComponent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author caocs
 * @date 2021/10/7
 * BeanPostProcessor的自定义实现类
 * 当需要自定义一些执行逻辑的时候，在这里实现。然后在框架中找到该类并执行。
 */
@CcsComponent // 通过该注解，让spring找到我们自定义的BeanPostProcessor实现类。
public class CcsBeanPostProcessorImpl implements CcsBeanPostProcessor {


    @Override
    public Object postProcessorBeforeInitialization(Object bean, String beanName) {
        System.out.println("postProcessorBeforeInitialization");
        if ("userService".equals(beanName)) {
            ((UserServiceImpl) bean).setPostProcessor("ProcessorBefore");
        }
        return bean;
    }

    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) {
        System.out.println("postProcessorAfterInitialization");
        if ("userService".equals(beanName)) {
            /**
             * 模拟AOP动态代理逻辑
             */
            Object proxyInstance = Proxy.newProxyInstance(
                    CcsBeanPostProcessor.class.getClassLoader(),
                    bean.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("先执行一些代理逻辑");
                            return method.invoke(bean, args);
                        }
                    }
            );
            return proxyInstance;
        }
        return bean;
    }
}
