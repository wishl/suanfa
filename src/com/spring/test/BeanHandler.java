package com.spring.test;

import java.lang.reflect.InvocationHandler;

public class BeanHandler {

    private InvocationHandler invocationHandler;

    private Object bean;

    private String beanName;

    public InvocationHandler getInvocationHandler() {
        return invocationHandler;
    }

    public Object getBean() {
        return bean;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setInvocationHandler(InvocationHandler invocationHandler) {
        this.invocationHandler = invocationHandler;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
