package com.java.ccs.spring;

public interface CcsBeanPostProcessor {

    Object postProcessorBeforeInitialization(Object bean, String beanName);

    Object postProcessorAfterInitialization(Object bean, String beanName);

}
