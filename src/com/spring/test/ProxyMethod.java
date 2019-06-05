package com.spring.test;

import java.lang.reflect.Method;

public final class ProxyMethod {

    private Method method;
    private Object object;
    private Object[] param;

    public Method getMethod() {
        return method;
    }

    public Object getObject() {
        return object;
    }

    public Object getParam() {
        return param;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setParam(Object[] param) {
        this.param = param;
    }
}
