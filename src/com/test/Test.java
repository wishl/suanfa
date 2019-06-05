package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    static class Data{
        String key;
        String value;

        public void setValue(String value) {
            this.value = value;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public static Data get(Map<String,Data> map,String key){
        return map.get(key);
    }

    public static void main(String[] args) {
//        Data data = new Data();
//        data.setKey("111");
//        data.setValue("222");
//        Map<String,Data> map = new HashMap<>();
//        map.put("111",data);
////        Data data1 = Test.get(map, "111");
////        data1.setValue("666");
////        System.out.println(data.value);
//        data.setValue("666");
//        System.out.println(map.get("111").value);

//        String a = "aaa";
//        String b = "aaa";
//        System.out.println(a==b);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> list1 = new ArrayList<>(list);
        for (Integer integer : list) {
            System.out.println(integer);
            list1.remove(integer);
        }
    }

}
