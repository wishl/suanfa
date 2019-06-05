package com.test;

import java.util.Arrays;

/**
 * 希尔排序
 * 把数组分成多个increment,在对每个increment进行排序
 * 之后减少increment的数量,直到increment=1
 * 第一次的increment数量=length/3+1
 * 之后每次的increment数量=之前increment数量/3+1
 * 分组并不新建数组而是使用原数组
 */
public class Shells1 {

    // 升序
    private static void shellSort(int[] ints){
        int distance = ints.length/3+1;
        while (true){
            for (int i = 0; i < ints.length; i++) {
                int tmp = ints[i];
                // 排序时取出第i个元素和i+n*distance比较,把tmp插入正确位置,会出现以下情况:
                // 1.tmp比任何其他的同组元素都大
                // 2.tmp小于其中一个同组元素
                // 3.tmp比任何同组元素都小
                // 在1的情况下如果没有k则会抛出ArrayIndexOutOfBoundsException
                // 在2,3情况下没有k则会造成tmp没有插入正确的位置
                int k = i;
                for (int j = i+distance; j < ints.length&&ints[j]<tmp ; j+=distance) {// 排序
                    ints[i] = ints[j];
                    k = j;
                }
                ints[k] = tmp;
            }
            if(distance==1){// 如果距离是1则break
                break;
            }
            distance = distance/3+1;
        }
    }

    public static void main(String[] args) {
        int[] is = {7,6,5,4,3,2,1};
        shellSort(is);
        System.out.println(Arrays.toString(is));
    }

}
