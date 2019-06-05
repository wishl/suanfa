// https://blog.csdn.net/huang_ke_hai/article/details/79977905 跑步问题
public class RunTest {

    /**
     * 贝茜每天进行N分钟的晨跑。在每分钟的开始，
     * 贝茜会选择下一分钟是用来跑步还是休息。
     * 贝茜的体力限制了她跑步的距离。更具体地，
     * 如果贝茜选择在第i分钟内跑步，她可以在这一分钟内跑a_i米，
     * 并且她的疲劳度会增加1。不过，无论何时贝茜的疲劳度都不能超过M。
     * 如果贝茜选择休息，那么她的疲劳度就会每分钟减少1，
     * 但她必须休息到疲劳度恢复到0为止。晨跑开始时，贝茜的疲劳度为0。
     * ，在N分钟的锻炼结束时，贝茜的疲劳度也必须恢复到0，否则她将没有足够的精力来对付这一整天中剩下的事情。
     * 请你计算一下她最多能走多少距离？
     */


    public static void main(String[] args) {
        int max = getMax(5, 5, new int[]{0, 5, 4, 3, 2,1});
        System.out.println(max);
    }

    /**
     * @param m 疲劳最大值
     * @param n 时间
     * @param a 第i分钟跑的距离
     */
    private static int getMax(int m,int n,int a[]){
        int[][] result = new int[n+1][m+1];
        for (int i = 1; i <=n; i++) {// 时间
            result[i][0] = result[i-1][0];// 设置i分钟疲劳为0的出事距离
            for (int j = 1; j <= min(n, i); j++) {// 疲劳
                // 第i分钟疲劳为0的结果:
                result[i][0] = max(result[i][0],result[i-j][j]);
                // 第i分钟疲劳不为0的结果
                result[i][j] =result[i-1][j-1]+a[i];
            }
        }
        return result[n][0];
    }

    private static int min(int a,int b){
        return a>b?b:a;
    }

    private static int max(int a,int b){
        return a>b?a:b;
    }
}
