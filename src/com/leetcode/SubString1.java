package com.leetcode;


/**
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 */
public class SubString1 {

    // 把第i个点当做中心
    public static String longestPalindrome(String s) {
        String son = "";
        for (int i = 0; i < s.length()-1; i++) {
            String s2 =get(s, i, i);
            String s3 =get(s, i, i+1);
            String s1= s2.length() > s3.length()?s2:s3;
            if(son.length()<s1.length()){ // 长度小于max
                son = s1;
            }
        }
        return son;
    }

    public static String longestPalindrome1(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;// 奇数向下取整
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }
    private static int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;// 左闭右开
    }

    // 返回一个子串的长度
    private static String get(String s,int index,int mid){
        int begin = index;
        int end = mid;
        String result = "";
        while (begin>0&&end<s.length()&&s.charAt(index)==s.charAt(mid)){
            begin--;
            end++;
        }
        return result;
    }

    public static void main(String[] args) {
//        abaaba
        String s = longestPalindrome1("accacacab");
        System.out.println(s);
    }

}
