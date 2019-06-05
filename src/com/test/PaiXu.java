package com.test;

import java.util.Arrays;

public class PaiXu {

    // 比较排序(把第i个元素放入正确的位置)
    public static void bijiao(int[] array){
        for (int i = 0; i < array.length-1; i++) {
            for (int j = i+1; j < array.length; j++) {
                if(array[i]<array[j]){
                    int tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(array));
    }


    // 冒泡
    public static void maopao(int[] a){
        for (int i = 0; i < a.length-1; i++) {
            for (int j = i+1; j < a.length; j++) {
                if(a[j-1]<a[j]){
                    int tmp = a[j-1];
                    a[j-1] = a[j];
                    a[j] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(a));
    }


    // 堆排序

    /**
     * 先算出大顶堆或小顶堆
     * 然后与末尾元素交换
     */
    static class Heap{

        static void sort(int[] a){
            // 构建大顶堆
            for (int i = a.length/2-1; i >= 0; i--) {
                adjustHeap(a,i,a.length);
            }
            // 调整大顶堆
            for (int i = a.length - 1; i >= 0; i--) {
                swap(a,0,i);
                adjustHeap(a,0,i);
            }
        }

        /**
         * 调整大顶堆
         */
       static void adjustHeap(int[] a,int j,int length){
           int tmp = a[j];
           for (int i = 2*j+1; i < length; i=2*i+1) {
               if(i+1<length&&a[i]<a[i+1]){
                   i++;
               }
               if(tmp<a[i]){
                   a[j] = a[i];
                   j = i;
               }else{
                   break;
               }
           }
           a[j] = tmp;
       }

       static void swap(int[] a,int i,int j){
           int tmp = a[i];
           a[i] = a[j];
           a[j] = tmp;
       }

    }

    /**
     * 降序排列
     */
    static class Heap1{


        static void sort(int[] a){
            // 最小的非叶子节点=(length/2)-1
            for (int i = a.length/2 - 1; i >= 0; i--) {
                adjustHeap(a,i,a.length);
            }
            // 所有节点慢慢变成叶子节点
            for (int i = a.length - 1; i >= 0; i--) {
                swap(a,0,i);
                adjustHeap(a,0,i);
            }
        }

        /**
         *
         * @param a
         * @param j 顶部的id
         * @param length
         */
        static void adjustHeap(int[] a,int j,int length){
            int tmp = a[j];
            // 左子节点的index是2j+1
            // 完成父节点的变换之后变换子节点
            // 第一次生成从最小的子节点开始
            for (int i = 2*j+1; i < length; i = 2*i+1) {
                if(i+1<length&&a[i]>a[i+1]){// 如果左节点大于右节点
                    i++;
                }
                if(a[i]<tmp){// 如果子节点大于父节点则变化
                    a[j] = a[i];
                    j = i;
                }else{// 本次没有变化则下面的都不用变化
                    break;
                }
            }
            a[j] = tmp;
        }

        static void swap(int[] a,int i,int j){
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }

    }


    // 从左往右找到大于x的值放到右边界,从右往左找到小于x的值放到左边界
    // 把x放入正确的位置
    public static void quickSort(int[] a, int l, int r) {
        if(l<r){
            int i = l;
            int j = r;
            int x = a[i];
            while (i<j){// 从右往左查询小于x的值
                while (i<j&&a[j]<x){
                    j--;
                }
                if(i<j){
                    a[i++] = a[j];
                }
                while (i<j&&a[i]>x){// 从左往右查询大于x的值
                    i++;
                }
                if(i<j){
                    a[j--] = a[i];
                }
            }
            a[i] = x;
            quickSort(a,l,i-1);
            quickSort(a,i+1,r);
        }
    }


    public static void maopao1(int[] args){
        for (int i = 1; i < args.length-1; i++) {
            for (int j = i+1; j < args.length; j++) {
                if(j-1<j){
                    int tmp = args[j];
                    args[j] = args[j+1];
                    args[j+1] = tmp;
                }
            }
        }
    }


    public static void main(String[] args) {
        int[] ints = {0,3,1,9,8,6,7,4,2,5};
        bijiao(ints);
//        System.out.println("=======");
//        maopao(ints);
//        System.out.println("=======");
//        new Heap().sort(ints);
//        System.out.println(Arrays.toString(ints));
//          new Heap1().sort(ints);
//        quickSort(ints,0,ints.length-1);
        System.out.println(Arrays.toString(ints));
    }

}
