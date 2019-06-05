package com.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * lRU算法:淘汰最近没有访问过的数据
 * 没有指定次数限制
 * 有次数限制-->LRU-K
 * Multi Queue(MQ)
 */
public class LRU<K,V> {

    private Map<String,Object> deep = new HashMap();// 模拟底层存储
    private int totalSize;
    private AtomicInteger size = new AtomicInteger(0);
    private Link top;

    public Map<String, Object> getDeep() {
        return deep;
    }

    public LRU(int totalSize){
        this.totalSize = totalSize;
    }

    public V get(K k){
        Link contains = contains(k);
        if(contains != null){
            if(contains.getBefore()!=null) {
                contains.before.setNext(contains.getNext());
            }
            if(contains.getNext()!=null) {
                contains.next.setBefore(contains.getBefore());
            }
            top.setBefore(contains);
            top = contains;
            return (V) top.getData().getValue();
        }
        Object v = deep.get(k);
        if(v == null){
            return null;
        }
        Data data = new Data();
        data.setKey(k);
        data.setValue(v);
        Link link = new Link();
        link.setData(data);
        link.setNext(top);
        this.top = link;
        size.addAndGet(1);
        if(size.get()>totalSize){
            dealLink(top);
        }
        return (V) v;
    }


    class Link<K,V>{
        Link before;
        Link next;
        Data<K,V> data;

        public Link getBefore() {
            return before;
        }

        public Link getNext() {
            return next;
        }

        public void setNext(Link next) {
            this.next = next;
        }

        public void setBefore(Link before) {
            this.before = before;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        @Override
        public String toString() {
            String next = this.next == null ? null : this.next.toString();
            String data = this.data == null ? null : this.data.toString();
            return data+"->"+next;
        }
    }

    class Data<K,V>{
        K key;
        V value;

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "{key="+key+",value="+value+"}";
        }
    }

    public Link getTop() {
        return top;
    }

    private Link contains(K k){
        Link top = this.top;
        if(top==null){
            return null;
        }
        if(top.getData().getKey().equals(k)){
            return top;
        }
        while ((top = top.next)!=null){
            if(top.getData().getKey().equals(k)){
                return top;
            }
        }
        return null;
    }

    public void dealLink(Link link){
        for (int i = 0; i < totalSize-1; i++) {
            link = link.getNext();
        }
        link.setNext(null);
        while ((link = link.getBefore()) != null){
        }
    }

    public static void main(String[] args) {
        LRU lru = new LRU(3);
        Map deep = lru.getDeep();
        deep.put("gmy",123);
        deep.put("gmy1",1234);
        deep.put("gmy2",1235);
        deep.put("gmy3",1236);
        deep.put("gmy4",1237);
        lru.get("gmy");
        lru.get("gmy1");
        lru.get("gmy3");
        lru.get("gmy4");
        lru.get("gmy2");
        lru.get("gmy1");
        System.out.println(lru.getTop());
    }

}
