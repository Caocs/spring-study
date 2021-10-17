package com.java.ccs.spring;

/**
 * @author caocs
 * @date 2021/10/6
 */
public class CcsBeanDefinition {

    private Class clazz;
    private String scope;



    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
