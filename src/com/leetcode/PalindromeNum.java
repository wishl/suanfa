package com.leetcode;

/**
 * 判断一个数字是否是回文
 */
public class PalindromeNum {

    public static boolean isPalindrome(int x) {
        String s = x+"";
        int end = s.length()-1,start = 0;
        while (end>=start){
            if(s.charAt(start)!=s.charAt(end)){
                return false;
            }
            end--;
            start++;
        }
        return true;
    }

    // 把int翻转在比
    public static boolean isPalindrome1(int x){
        if(x<0||(x!=0&&x%10==0)){// 最后一位是0只可能x是0
            return false;
        }
        int z = x;
        int y = 0;
        while (x!=0){
            y = y*10+(x%10);
            x /=10;
        }
        return y==z;
    }

    public static void main(String[] args) {
        boolean palindrome = isPalindrome1(-121);
        System.out.println(palindrome);
    }

}
