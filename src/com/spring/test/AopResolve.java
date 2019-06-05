package com.spring.test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class AopResolve {

    public Method getAopMethod(String methodName){
        StringBuffer error = new StringBuffer("");
        try {
            checkString(methodName);
        } catch (Exception e) {
            throw new NullPointerException("Proxy method cannot be null");
        }
        String[] split = methodName.split("\\(");
        String authority = getAuthority(split[0]);
        split[0] = split[0].substring(split[0].indexOf(" ")+1);
        String className = getClassName(split[0]);
        String pack = getPackage(split[0]);
        String param = split[1].substring(0, split[1].lastIndexOf(")"));

        return null;
    }

    private String getAuthority(String methodName){
        checkString(methodName);
        int index = methodName.indexOf(" ");// 空格
        if(index==-1){
            return null;
        }
        String authority = methodName.substring(0,index);
        return authority;
    }

    private String getPackage(String methodName){
        checkString(methodName);
        int index = methodName.indexOf("..");
        String pack = null;
        if(index==-1){
            for (int i = 0; i < 2; i++) {
                pack = methodName.substring(0,methodName.lastIndexOf("."));
                methodName = pack;
            }
           return pack;
        }else{
            pack = methodName.substring(0,methodName.indexOf(".."));
        }
        return pack;
    }

    private String getClassName(String methodName){
        checkString(methodName);
        int index = methodName.lastIndexOf(".");
        String className = null;
        if(index==-1){
            return null;
        }else{
            int begin = -1;
            for (int i = 0; i < 2; i++) {
                if(begin != -1)
                    methodName = methodName.substring(0,methodName.lastIndexOf("."));
                begin = methodName.lastIndexOf(".");
            }
            return methodName.substring(begin+1);
        }
    }

    private void checkString(String text){
        if(text==null||text==""){
            throw new NullPointerException();
        }
    }

    private Method getMethod(String authority,String pack,String className,String param) throws ClassNotFoundException {
        className = pack.concat(".").concat(className);
        Class<?> aClass = Class.forName(className);
        if(param.equals("..")){
           param = null;
        }else{

        }
        Method[] declaredMethods = aClass.getDeclaredMethods();
        if(authority.equals("private")){

        }else if(authority.equals("protected")){

        }else if(authority.equals("public")){

        }else{

        }
        return null;
    }

    public static void main(String[] args) {
        String methodName = "* com.test.spring.aop.pointcutexp..JoinPointObjP2.*(..)";
        AopResolve aopResolve = new AopResolve();
        aopResolve.getAopMethod(methodName);
        Method[] declaredMethods = AopResolve.class.getDeclaredMethods();
    }

}
