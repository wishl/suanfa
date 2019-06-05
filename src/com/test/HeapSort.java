package com.test;

import java.util.Arrays;

// 堆排序
public class HeapSort {


    /**
     * 构造大顶堆
     * @param comparables 数组
     * @param start 开始index
     * @param length 构造长度
     */
    public void bigTop(Comparable[] comparables,int start,int length){
       Comparable comparable = comparables[start];
        for (int i =2*start+1; i < length; i=2*i+1) {
            if(i+1<length&&comparables[i].compareTo(comparables[i+1])<0){// 左节点比右节点小
                i++;
            }
            if(comparable.compareTo(comparables[i])<0){// 小于
                comparables[start] = comparables[i];
                start = i;
            }else{
                break;
            }
            comparables[start] = comparable;
        }
    }

    public void smallTop(Comparable[] comparables,int start,int length){
        Comparable tmp = comparables[start];
        for (int i = 2*start+1; i < length; i=2*i+1) {
            if(i+1<length&&comparables[i].compareTo(comparables[i+1])>0){// 左节点比右节点大(i+1==length说明只有左节点)
                i++;
            }
            if(tmp.compareTo(comparables[i])>0){// 父节点比子节点大
                comparables[start] = comparables[i];
                start = i;
            }else {
                break;
            }
            comparables[start] = tmp;
        }
    }

    // 升序
    private void buildBig(Comparable[] comparables){
        for (int i = comparables.length/2-1; i >= 0; i--) {
            bigTop(comparables,i,comparables.length);
        }
        for (int i = comparables.length-1; i > 0; i--) {
            swap(comparables,0,i);
            bigTop(comparables,0,i);
        }
        System.out.println(Arrays.toString(comparables));
    }

    // 降序
    private void buildSmall(Comparable[] comparables){
        for (int i = comparables.length/2-1; i > 0; i--) {// 从后往前构建
            smallTop(comparables,i,comparables.length);
        }
        for (int i = comparables.length-1; i > 0 ; i--) {
            swap(comparables,0,i);
            smallTop(comparables,0,i);
        }
        System.out.println(Arrays.toString(comparables));
    }

    private void swap(Comparable[] comparables,int start,int end){
        Comparable tmp = comparables[start];
        comparables[start] = comparables[end];
        comparables[end] = tmp;
    }

    public static void main(String[] args) {
        HeapSort heapSort = new HeapSort();
        Integer[] is = {4,3,1,5,6,4,2,1,0};
        heapSort.buildBig(is);
        heapSort.buildSmall(is);
    }


}
