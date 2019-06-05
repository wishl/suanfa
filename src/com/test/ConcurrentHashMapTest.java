package com.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
