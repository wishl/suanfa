package com.spring.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxy implements InvocationHandler {

    private Object subject;

    private ProxyMethod beforeMethod;

    private ProxyMethod afterMethod;

    public void setSubject(Object subject) {
        this.subject = subject;
    }

    public void setBeforeMethod(ProxyMethod beforeMethod) {
        this.beforeMethod = beforeMethod;
    }

    public void setAfterMethod(ProxyMethod afterMethod) {
        this.afterMethod = afterMethod;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method before = beforeMethod.getMethod();
        before.invoke(beforeMethod.getObject(),beforeMethod.getParam());
        Object result = method.invoke(proxy, args);
        Method after = afterMethod.getMethod();
        after.invoke(afterMethod.getObject(),afterMethod.getParam());
        return result;
    }
}
