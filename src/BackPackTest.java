public class BackPackTest {

    public static void main(String[] args) {
        int m = 10;// 总重量
        int n = 3;// 物品数量
        int[] w = {3,4,5};
        int[] p = {4,5,6};
        int[][] result = getMax(m, n, w, p);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                System.out.print(result[i][j]+"\t");
                if(j==m){
                    System.out.println();
                }
            }
        }
        int max1 = getMax1(m, n, w, p);
        System.out.println(max1);
        System.out.println("最大价值:"+result[n][m]);
    }

    private static int[][] getMax(int m,int n,int[] w,int[] p){
        int[][] c = new int[n+1][m+1];
        // 赋值0个物体和背包容量为0时的总价值为0
        for(int i=0;i<n+1;i++){
            c[i][0] = 0;
        }
        for (int i = 0; i < m + 1; i++) {
            c[0][i] = 0;
        }
        for (int i = 1; i < n+1; i++) {// 遍历物品
            for (int j = 1; j < m + 1; j++) {// 重量
                if(w[i-1]<=j){// 如果这个物体小于总重量
                    int x = c[i-1][j];// 不加这个物体之前的价值
                    int y = c[i-1][j-w[i-1]];// 没有加他之前的价值
                    int z = p[i-1];// 第i个物品的重量
                    if(x<(y+z)){// 如果加上这个物品的价值大于不加的价值则加上(没有加这个物品之前的价值加上这个物品的价值)
                        c[i][j] = y+z;
                    }else{// 否则不加(保持之前的价值)
                        c[i][j] = c[i-1][j];
                    }
                }else{// 如果物品重量大于总重量则无法加入保持原价值
                    c[i][j] = c[i-1][j];
                }
            }
        }
        return c;
    }

    /**
     * 一维数组实现
     */
    private static int getMax1(int m,int n,int[] w,int[] p){
        int result[] = new int[m+1];
        for (int i = 1; i <= n; i++) {
            for (int j = m; j >= 1; j--) {
                if(w[i-1]<j){
                    result[j] = result[j]<(result[j-1]+p[i-1])?result[j-w[i-1]]+p[i-1]:result[j-1];
                }
            }
        }
        return result[m];
    }

    /**
     * 完全背包问题(一维数组实现):
     * 多次放入同一个,直到同种类的物品放不下,再放下一种类.
     */
    private static int getMax2(int m,int n,int[] w,int[] p){
        int[] result = new int[n+1];
        for (int i = 1; i <= n; i++) {
            for (int j = w[i-1]; j <= m ; j++) {// 从能装下的开始
                if(w[i-1]<j){
                    result[j] = max(result[j],result[j-1]+p[i-1]);
                }
            }
        }
        return result[n];
    }

    private static int max(int a,int b){
        return a<b?b:a;
    }


}
