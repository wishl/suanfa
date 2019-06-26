package com.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 3题
 */
public class SubString {

    // abcabc == >3
    // abba
    // bbb ==> 1
    public static int lengthOfLongestSubstring(String s) {
        int result = 0;
        if(s.length()==1){
            return 1;
        }
        for (int i = 0; i < s.length(); i++) {
            for (int j = i+1; j < s.length(); j++) {
                boolean flag = getMax(s, i, j);
                if(flag){
                    result = result > j-i?result:j-i;
                }
            }
        }
        return result;
    }

    private static boolean getMax(String s,int begin,int end){
        char[] chars = s.toCharArray();
        Set<Character> set = new HashSet<>();
        for (int i = begin; i < end; i++) {
            if(set.contains(chars[i])){
                return false;
            }
            set.add(chars[i]);
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("1"));
    }

}
