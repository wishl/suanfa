// https://blog.csdn.net/huang_ke_hai/article/details/79977905 跑步问题
public class CowTest {

    /**
     * 大佬以及他的N头奶牛打算过一条河，但他们仅仅只有一个木筏。
     * 由于奶牛不会划船，在整个渡河过程中，大佬必须始终在木筏上。
     * 当然，木筏上的奶牛数目每增加1，大佬把木筏划到对岸就得花更多的时间。
     * 当大佬一个人坐在木筏上，他把木筏划到对岸需要M分钟。
     * 当木筏搭载的奶牛数目从i-1增加到i时，大佬得多花M_i分钟才能把木筏划过河
     * （也就是说，船上有1头奶牛时，FJ得花M+M_1分钟渡河；船上有2头奶牛时，时间就变成M+M_1+M_2分钟……以此类推）。那么，大佬最少要花多少时间，才能把所有奶牛带到对岸呢？(当然，这个时间得包括FJ一个人把木筏从对岸划回来接下一批的奶牛的时间。)
     */


    public static void main(String[] args) {

    }

    /**
     * @param m 大佬自己返回的时间
     * @param n 奶牛数
     * @param a 奶牛数对应的时间
     * @return
     */
    // 相当于只有一件物品的完全背包问题
    private static int getMax(int m, int n, int[] a) {
        int[] result = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            // 多运一次和多加一直牛比较
            result[i] = max(result[i - 1] + m + a[i], result[i - 1] + a[i]);
        }
        return 0;
    }

    private static int max(int a,int b){
        return a>b?a:b;
    }
}
