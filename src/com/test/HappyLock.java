package com.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

/**
 * 乐观悲观锁
 */
public class HappyLock {

    private static Map<String,Object> map = new HashMap<>();
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static Lock sad = new ReentrantLock();
    private static StampedLock stampedLock = new StampedLock();

    private static Semaphore semaphore = new Semaphore(1);


    public static void happyInsert(String value){
        lock.writeLock().lock();// 写锁会阻塞读锁
        try {
            System.out.println(Thread.currentThread().getName()+"===>"+value);
            Thread.sleep(1000);
            map.put("111",value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void happyRead(){
        lock.readLock().lock();
        try {// readLock 可以同时读
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName()+"===>"+map.get("111"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void sadInsert(String value){
        sad.lock();
        try {
            Thread.sleep(100);
            map.put("111",value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            sad.unlock();
        }
    }

    public static void sadRead(){
        boolean b = sad.tryLock();// trylock 不会等待
        if(b) {
            try {
                System.out.println(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + "===>" + map.get("111"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sad.unlock();
            }
        }
    }

    // jdk1.8中加入了StampedLock 类,解决写锁阻塞读锁的问题,使用的是tryOptimisticRead 获取
    // 一个类似时间戳的东西,然后在取数据是校验时间戳,如果没有改变则获取数据,如果改变则
    // 可以循环调用tryOptimisticRead 知道可以获取数据或者升级锁为悲观锁
    public static void realHappyInsert(String value){
        long stmp = stampedLock.writeLock();// stmp 用于释放锁
        try {
            System.out.println(Thread.currentThread().getName()+"===>"+value);
            Thread.sleep(1000);
            map.put("111", value);
        }catch (Exception e){
        } finally {
            stampedLock.unlock(stmp);
        }
    }

    public static void writeHappyRead(){
        long stmp = stampedLock.tryOptimisticRead();// 获取stmp
        while (!stampedLock.validate(stmp)){// 校验stmp 如果不通过则重新获取锁
            stmp = stampedLock.tryOptimisticRead();
            System.out.println("get stmp again");
        }
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName()+"===>"+map.get("111"));
        // 只获取stmp时不需要释放锁,因为并没有获取锁
    }


    public static void writeHappyRead1(){// 会变慢
        long stmp = stampedLock.tryOptimisticRead();// 获取stmp
        if (!stampedLock.validate(stmp)){// 校验stmp 如果不通过则升级锁
            try {
                Thread.sleep((long)Math.random()*10);
//                semaphore.acquire();// 可以只升级一次锁,但是没有多线程效果semaphore(1)的时候相当于加锁
                stmp = stampedLock.tryOptimisticRead();// 获取stmp

                if(!stampedLock.validate(stmp)) {// 再做一次校验
                    //也可以升级锁的级别,这里我们升级乐观锁的级别,
                    // 将乐观锁变为悲观锁, 如果当前对象正在被修改,则读锁的申请可能导致线程挂起.
                    stmp = stampedLock.readLock();
                    System.out.println("lock up");
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName() + "===>" + map.get("111"));
                    stampedLock.unlock(stmp);// 升级锁之后释放锁
                }else{
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName() + "===>" + map.get("111"));
                }
//                semaphore.release();
            } catch (Exception e){
                e.printStackTrace();
            }
        }else {
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + "===>" + map.get("111"));
        }
        // 只获取stmp时不需要释放锁,因为并没有获取锁
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                try {
                    countDownLatch.await();
                    Thread.sleep(10);
                    realHappyInsert(Thread.currentThread().getName()+"111");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            countDownLatch.countDown();
        }
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    countDownLatch.await();
                    writeHappyRead1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            countDownLatch.countDown();
        }
    }

}
