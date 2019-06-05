package com.test;

import java.util.concurrent.Semaphore;

/**
 * Semaphore 是一种基于计数的信号量
 */
public class SemaphoreTest {

    private static Semaphore semaphore = new Semaphore(3);

    public static void test(){
        try {
            semaphore.acquire();// 只能同时有3个线程走
            try {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            new Thread(()->{
               test();
            },"thread"+i).start();
        }
    }

}
