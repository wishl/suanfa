package com.leetcode;

/**
 * 一个数从上到下，从左到右做z排序
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 */
public class Zsort {

    // numRows为n则(慢)
    // 哈哈哈自己相对的
    public static String convert(String s, int numRows) {
        if(numRows<1) return s;
        // 第一行中间有n-2个数
        // 第二行中间有n-2-1个数
        // 第一行的n个数 = n*(n+(n-2))
        // 第i行中间隔n-2+i个
        // 第i行的数据i
        // 边上的 = 2j(n-1)+j
        // 中间的 = (2n-1)*j
        int gap = 2*numRows-2;// 中间间隔
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < numRows; i++) {//行
            for (int j = 0; j <s.length(); j+=gap) {
                if(j+i<s.length()){//E   D   H   N
                    sb.append(s.charAt(j+i));
                }
                int num = j+gap-i;
                if(i>0&&i<numRows-1&&num<s.length()){// 中间 E T O E S I I G
                    sb.append(s.charAt(num));
                }
            }
        }
        return sb.toString();
    }


    public static String convert1(String s, int numRows){
        if (numRows == 1) return s;

        StringBuilder ret = new StringBuilder();
        int n = s.length();
        int cycleLen = 2 * numRows - 2;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j + i < n; j += cycleLen) {
                ret.append(s.charAt(j + i));
                if (i != 0 && i != numRows - 1 && j + cycleLen - i < n)
                    ret.append(s.charAt(j + cycleLen - i));
            }
        }
        return ret.toString();
    }

    public static void main(String[] args) {
//        int numRows = 4;
//        String s = "0123210123";
//        int line  = s.length()/(2*numRows-2)*2;
//        int i = s.length()%(2*numRows-2)>numRows? s.length()%(2*numRows-2)+1:1;
//        line+=i;
//        System.out.println(line);
        String leetcodeishiring = convert("LEETCODEISHIRING", 3);
        System.out.println(leetcodeishiring);
    }

}
