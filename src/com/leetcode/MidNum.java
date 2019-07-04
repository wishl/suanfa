package com.leetcode;


// 寻找两个有序数组的中位数
public class MidNum {

    // 先牌号顺序在找中位数
    public static double findMedianSortedArrays(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) {
            int[] tmp = A;
            A = B;
            B = tmp;
            m = A.length;
            n = B.length;
        }
        int imin = 0, imax = m, helf = (m + n + 1) / 2;// 如果是奇数个数字则左边的多1
        int i, j;
        while (imin < imax) {
            i = (imin + imax) / 2;
            j = helf - i;// j = m+n+1/2-i
            if (i<imax&&B[j-1]>A[i]) {// 小(i增大j减小，B[j-1]减小，所以i太小)
                imin = i+1;
            } else if (i > imin && A[i-1]>B[j]) {// 大
                imax = i -1;
            } else {
                int maxLeft,minRight;
                if (i == 0) {// 都在右边的部分里
                    return B[j-1];
                }else if(j==0){
                    return A[i-1];// 都在左边的部分里
                }else{// 两边都有时找到左边做大的和右边最小的
                    maxLeft = A[i-1]>B[j-i]?A[i-1]:B[j-1];
                    minRight = A[i]>B[j]?A[i]:B[j];
                }
                if (j == 0) {
                    if ((m + n) % 2 == 1) {// 如果是奇数则返回左边的
                        return maxLeft;
                    }
                    return (maxLeft+minRight) / 2.0;
                }
            }
        }
        return 0.0;
    }

    public static double findMedianSortedArrays1(int[] A,int[] B){
        int m = A.length;
        int n = B.length;
        if (m > n) { // to ensure m<=n
            int[] temp = A; A = B; B = temp;
            int tmp = m; m = n; n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && B[j-1] > A[i]){
                iMin = i + 1; // i is too small
            }
            else if (i > iMin && A[i-1] > B[j]) {
                iMax = i - 1; // i is too big
            }
            else { // i is perfect
                int maxLeft = 0;
                if (i == 0) { maxLeft = B[j-1]; }
                else if (j == 0) { maxLeft = A[i-1]; }
                else { maxLeft = Math.max(A[i-1], B[j-1]); }
                if ( (m + n) % 2 == 1 ) { return maxLeft; }

                int minRight = 0;
                if (i == m) { minRight = B[j]; }
                else if (j == n) { minRight = A[i]; }
                else { minRight = Math.min(B[j], A[i]); }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }

    public static void main(String[] args) {
        int[] A = {1,3};
        int[] B = {2};
        double result = findMedianSortedArrays(A, B);
        System.out.println(result);
    }
}
