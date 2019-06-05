package com.test;

import java.util.Arrays;

/**
 * 希尔排序(插入排序的一种)
 * 把数组分成多个increment,在对每个increment进行排序
 * 之后减少increment的数量,直到increment=1
 * 第一次的increment数量=length/3+1
 * 之后每次的increment数量=之前increment数量/3+1
 */
public class Shells {

    // 分increment再排序
    private static void shellSort(int[] ints){
        int incrementNum = ints.length/3+1;
        while (incrementNum>1){
            increment(incrementNum,ints);
            incrementNum = incrementNum/3+1;
        }
        sort(ints);
        System.out.println(Arrays.toString(ints));
    }

    // 分increment
    private static void increment(int num,int[] ints){
        int[][] incremnt = new int[num][];
        int factor = ints.length%num;
        for (int i = 0; i < num; i++) {
            System.out.println("increment="+incremnt);
            int size = (i + 1)<=factor?ints.length/num+1:ints.length/num;
            int[] is = new int[size];
            int i1 = 0;
            for (int j = i; j < ints.length; j+=num) {
                is[i1++] = ints[j];
            }
            sort(is);
            incremnt[i] = is;
        }
        assemable(ints,incremnt);
    }

    // 对每个increment进行排序
    private static void sort(int[] ints){
        for (int i = 0; i < ints.length; i++) {
            for (int j = 0; j < ints.length - 1 - i; j++) {
                if(ints[j] < ints[j+1]) {
                    int temp = ints[j];
                    ints[j] = ints[j + 1];
                    ints[j + 1] = temp;
                }
            }
        }
    }

    // 聚合increment
    private static void assemable(int[] result,int[][] ints){
        int beginIndex = 0;
        for (int[] anInt : ints) {
            System.arraycopy(anInt,0,result,beginIndex,anInt.length);
            beginIndex += anInt.length;
        }
    }


//    public static void main(String[] args) {
//        int[] ints = {7,6,5,4,3,2,1};
//        shellSort(ints);
//    }


    public static void main(String [] args)
    {
        int[]a={49,38,65,97,76,13,27,49,78,34,12,64,1};
        System.out.println("排序之前：");
        for(int i=0;i<a.length;i++)
        {
            System.out.print(a[i]+" ");
        }
        //希尔排序
        int d=a.length;
        while(true)
        {
            d=d/2;
            for(int x=0;x<d;x++)
            {
                for(int i=x+d;i<a.length;i=i+d)
                {
                    int temp=a[i];
                    int j;
                    for(j=i-d;j>=0&&a[j]>temp;j=j-d)
                    {
                        a[j+d]=a[j];
                    }
                    a[j+d]=temp;
                }
            }
            if(d==1)
            {
                break;
            }
        }
        System.out.println();
        System.out.println("排序之后：");
        for(int i=0;i<a.length;i++)
        {
            System.out.print(a[i]+" ");
        }
    }


}
