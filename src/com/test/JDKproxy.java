package com.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// jdk 动态代理
public class JDKproxy  {


    // 模拟Spring注入
    private Subject subject;

    interface Subject{

        default void method1(){
            System.out.println("interface method without implemented by any object");
        }

        int method2();
    }

    class SubjectImpl implements Subject{

        @Override
        public int method2() {
            System.out.println("interface method2 implemented by SubjectImpl");
            return 1;
        }
    }

    // 动态代理类
    class DynamicProxy implements InvocationHandler {
        private Object subject;// 需代理的对象

        DynamicProxy(Object subject){
            this.subject = subject;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("before invoke method");
            Object invoke = method.invoke(subject, args);
            System.out.println("after invoke method");
            return invoke;
        }
    }


    public int method(){
        subject.method1();
        int i = subject.method2();
        return i;
    }

    public int method1(){
        Subject subject = new SubjectImpl();
        InvocationHandler handler = new DynamicProxy(subject);
        // 生成动态代理类
        Subject sub  = (Subject) Proxy.newProxyInstance(handler.getClass().getClassLoader(), subject.getClass().getInterfaces(), handler);
        sub.method1();
        return sub.method2();
    }

    @interface AutoWrited{

    }

    public static void main(String[] args) {
       JDKproxy jdKproxy = new JDKproxy();
        int i = jdKproxy.method1();
    }


}
