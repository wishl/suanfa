package com.test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 桶排序
 * 创建n个hash桶,每个hash桶中有一定的数据范围,
 * 在排序时,从待排序的数组中获取数据放入hash桶中,
 * 之后对每个hash桶排序,在把hash桶相连后得到排好序的数组
 * 适合于多线程排序,参考ConcurrentHashMap
 */
public class Buket<T extends Comparable> {

    private static final Unsafe U;
    private static final long INDEX;
    private static final long BUKETINDEX;

    static {

        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            U = (Unsafe) field.get(Unsafe.class);
            Class k = Buket.class;
            INDEX = U.objectFieldOffset(k.getDeclaredField("index"));
            BUKETINDEX = U.objectFieldOffset(k.getDeclaredField("buketIndex"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new Error();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    private Comparable[] result = new Comparable[0];
    private int index = 0;
    private int[] ints = {6,5,2,6,1,34,214,124,23,41,24,124,234,132,423,3,41,32,4,124,234,23,41,32,413,24,5,5,2,45,2,2,1};
    private Buk[] buks;
    int buketIndex = 0;// 排序桶的index
    int buketNum;// 桶的数量
    int bukLength;// 桶的取值范围(0-100)-->100
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public Buket(int buketNum,int bukLength){
        buks = new Buk[buketNum];
        this.bukLength = bukLength;
        this.buketNum = buketNum;
        for (int i = 0; i < buketNum; i++) {
            Buk<Integer> buk = new Buk<>();
            buk.setNum(i);
            buks[i] = buk;
        }
    }

    class Buk<T>{
        List<T> list = new ArrayList<>();
        int num;

        void setNum(int num) {
            this.num = num;
        }

        List<T> getList() {
            return list;
        }

        int getNum() {
            return num;
        }

        void add(T t){
            list.add(t);
        }

        @Override
        public String toString() {
            return "Buk"+num+"{" + list + '}';
        }
    }

    public void putBuket(int[] is){
        for (int i = 0; i < is.length; i++) {
            int ii = is[i] / 100;
            buks[ii].add(is[i]);
        }
    }

    private void sort(int length){
        int nextIndex = index;
        while (index < ints.length) {
            boolean b = U.compareAndSwapInt(this, INDEX, nextIndex, index + length);
            if (b) {
                int nextBound = nextIndex + length;// 计算出的是index
                length = nextBound>ints.length?(ints.length-nextIndex):length;
                int[] bs = new int[length];
                System.arraycopy(ints,nextIndex,bs,0,length);
                putBuket(bs);
            }
            nextIndex = index;
        }
        List<Comparable[]> comparables = sortBuket();
        result = concatAll(result,comparables);
        System.out.println(Arrays.toString(result));
    }

    private List<Comparable[]> sortBuket(){
        int nextIndex = buketIndex;
        List<Comparable[]> result = new ArrayList<>(buketNum);
        while (nextIndex < buks.length){
            boolean b = U.compareAndSwapInt(this, BUKETINDEX, nextIndex, nextIndex + 1);
            if(b) {
                Buk buk = buks[nextIndex];
                List list = buk.getList();
                Comparable[] ts = new Comparable[list.size()];
                list.toArray(ts);
                maopao(ts);
                nextIndex = buketIndex;
                result.add(buk.getNum(),ts);
            }
        }
        return result;
    }


    public Comparable[] concatAll(Comparable[] first, List<Comparable[]> rest) {
        int totalLength = first.length;
        for (Comparable[] array : rest) {
            if (array != null) {
                totalLength += array.length;
            }
        }
        Comparable[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (Comparable[] array : rest) {
            if (array != null) {
                System.arraycopy(array, 0, result, offset, array.length);
                offset += array.length;
            }
        }
        return result;
    }

    // 冒泡
    public void maopao(Comparable[] a){
        for (int i = 0; i < a.length-1; i++) {
            for (int j = 0; j < a.length-1-i; j++) {
                if(a[j].compareTo(a[j+1])>0){
                    Comparable tmp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = tmp;
                }
            }
        }
    }

    public Comparable[] get(){
        return result;
    }

    class SortThread extends Thread{

        int length;

        SortThread(int length){
            this.length = length;
        }

        @Override
        public void run() {
           sort(length);
        }
    }

    public void sortArray(int threadNum){
        for (int i = 0; i < threadNum; i++) {
            executor.execute(new SortThread(5));
        }
        executor.shutdown();
    }



    public static void main(String[] args) {
        Buket buket = new Buket(10,100);
        buket.sortArray(1);
    }
}
