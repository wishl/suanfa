package com.leetcode;

public class ReturnI {

    public static int reverse(int x) {
        int start = 0;
        StringBuffer ret = new StringBuffer("");
        if(x<0){
            start++;
            ret.append("-");
        }
        String s =x+"";
        for (int i = s.length()-1; i >= start; i--) {
            ret.append(s.charAt(i));
        }
        try {
            return Integer.parseInt(ret.toString());
        } catch (Exception e){
            return 0;
        }
    }

    public static int reverse1(int i){
        int  ret = 0;
        while (i!=0){
            int j = i%10;
            i /= 10;
            if(Integer.MAX_VALUE/10<ret*10) return 0;
            if(Integer.MIN_VALUE/10>ret*10) return 0;
            ret = ret*10+j;
        }
        return ret;
    }

    public static void main(String[] args) {
        int i = -123;
        System.out.println(reverse1(i));
    }

}
