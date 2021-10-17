package com.java.ccs.test.demo;

import com.java.ccs.spring.*;

/**
 * @author caocs
 * @date 2021/10/6
 */
@CcsComponent("userService")
@CcsScope("prototype")
public class UserServiceImpl implements UserService, CcsBeanNameAware, CcsInitializingBean {

    /**
     * 依赖注入测试
     */
    @CcsAutowired
    private OrderService orderService;

    private String beanName;

    private String postProcessor;

    /**
     * BeanNameAware回调测试
     */
    @Override
    public void setBeanName(String name) {
        beanName = name;
    }

    /**
     * CcsInitializingBean测试
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet 在这可以执行很多自己的逻辑");
    }

    /**
     * 测试BeanPostProcessor
     */
    public void setPostProcessor(String postProcessor) {
        this.postProcessor = postProcessor;
    }


    public void test() {
        System.out.println(orderService);
        System.out.println(beanName);
        System.out.println(postProcessor);
    }


}
