package com.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 3题 找最大不重复的子串
 */
public class SubString {

    // abcabc==>3
    // abba
    // bbb==>1
    // 暴力解
    public static int lengthOfLongestSubstring(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i+1; j <= s.length(); j++) {
                boolean flag = getMax(s, i, j);
                if(flag){
                    result = result > j-i?result:j-i;
                }
            }
        }
        return result;
    }

    // 优化1
    public static int lengthOfLongestSubstring1(String s){
        Set<Character> set = new HashSet<>();
        int result = 0;
        int param = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            for (int j = i; j < chars.length; j++) {
                if(!set.contains(chars[j])){
                    param++;
                    set.add(chars[j]);
                }else{
                    break;
                }
            }
            result = result>param?result:param;
            param = 0;
            set = new HashSet<>();
        }
        return result;
    }


    // 优化2 寻找set窗口：我们使用 HashSet 将字符存储在当前窗口 [i, j)[i,j)（最初 j = ij=i）中。 然后我们向右侧滑动索引 jj，如果它不在 HashSet 中，我们会继续滑动 jj。直到 s[j] 已经存在于 HashSet 中。此时，我们找到的没有重复字符的最长子字符串将会以索引 ii 开头。如果我们对所有的 ii 这样做，就可以得到答案。
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/two-sum/solution/wu-zhong-fu-zi-fu-de-zui-chang-zi-chuan-by-leetcod/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    public static int lengthOfLongestSubstring2(String s){
        int n = s.length();
        int result = 0,i =0,j=0;
        Set<Character> set = new HashSet<>();
        while (i<n&&j<n){
            if(!set.contains(s.charAt(j))){
                set.add(s.charAt(j++));
                result = result > j-i?result:j-i;
            }else{
                set.remove(s.charAt(i++));// 删除离开窗口的char
            }
        }
        return result;
    }

    // 使用map存储char的位置，如果有重复，则该位置之前的子串都不满足要求
    public static int lengthOfLongestSubstring3(String s){
        int result = 0;
        int length = s.length();
        int i = 0, j = 0;
        Map<Character,Integer> map = new HashMap<>();
        while (j<length){
           if(map.containsKey(s.charAt(j))){// 判断i与map中的value，如果i>value则证明是之前的数据
               i = i>map.get(s.charAt(j))?i:map.get(s.charAt(j))+1;
           }
           map.put(s.charAt(j),j);
           j++;
           result = result>j-i?result:j-i;
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
        System.out.println(lengthOfLongestSubstring3("123213"));
        // kwkwe
    }

}
