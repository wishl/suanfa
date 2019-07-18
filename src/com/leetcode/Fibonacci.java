package com.leetcode;

import java.math.BigInteger;

/**
 * 斐波那契
 */
public class Fibonacci {

    public static int fibo(int n){
        if(n==0){
            return 0;
        }
        if(n==1){
            return 1;
        }
        return fibo(n-1)+fibo(n-2);
    }

    // 以空间换时间
    public static String fibo1(int n){
        if(n==0){
            return "0";
        }
        if(n==1){
            return "1";
        }
        BigInteger a = BigInteger.ONE;
        BigInteger b = BigInteger.ONE;
        BigInteger sum = b;
        for (int i = 3; i <= n; i++) {// 从第三位开始算可以少一次循环
            sum=a.add(b);
            a = b;
            b = sum;
        }
        return sum.toString();
    }

    public static void main(String[] args) {
        String fibo = fibo1(100);
        System.out.println(fibo);
    }

}
