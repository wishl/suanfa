package com.spring.test;

public @interface Aop {

    String beforeName() default "";
    String endName() default "";
}
