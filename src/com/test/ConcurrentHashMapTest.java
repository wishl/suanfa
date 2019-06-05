package com.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在put时当有hash碰撞则加锁,在对应hash中的链表添加数据
 * 如果在添加是遇到forwardNode则证明该map正在扩容,帮助map扩容
 * 扩容时,把底层数组分成一个个hash步长,每个线程对应处理一个步长,
 * 如果(sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT 说明扩容完成
 */
public class ConcurrentHashMapTest {

   static ConcurrentHashMap<String,Object> map = new ConcurrentHashMap(1);

    public static void doTest(){
        for (int i = 0; i < 30; i++) {
          if(i%2==0){
              map.put("123",123);
          }else if(i%3==0){
              map.put("321",321);
          }else if(i%4==0){
              map.put("231",231);
          }
        }
    }

    public static void main(String[] args) {
//        new Thread(()->{
//            while (true) {
//                System.out.println(map.get("2"));
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        doTest();
//        int i = 0xffffffff;
//        System.out.println(i);
//        System.out.println(i>>>16);
        Map<String,Object> m = new HashMap();
        for (int i = 0; i < 20; i++) {
            map.put(i+"",222);
        }
    }

}
