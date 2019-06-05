//https://blog.csdn.net/baidu_28312631/article/details/47418773
public class MaxTest {

    public static void main(String[] args) {
        int[][] data = new int[6][6];
        data[1][1] = 7;
        data[2][1] = 3;
        data[2][2] = 8;
        data[3][1] = 8;
        data[3][2] = 1;
        data[3][3] = 0;
        data[4][1] = 2;
        data[4][2] = 7;
        data[4][3] = 4;
        data[4][4] = 4;
        data[5][1] = 4;
        data[5][2] = 5;
        data[5][3] = 2;
        data[5][4] = 6;
        data[5][5] = 5;
        for (int i = 1; i <=5 ; i++) {
            for (int j = 1; j <= i ; j++) {
                System.out.print(data[i][j]);
                System.out.print("   ");
            }
            System.out.println();
        }
        int max = getMax(data, 5);
        System.out.println(max);
    }

    public static int getMax(int[][] data,int m){
        int[] result = new int[m+1];
        int[] ms = data[m];// 获取最后一行数据
        for (int i = m-1; i >= 1 ; i--) {
            for (int j = 1; j <= i; j++) {
                int result1 = max(ms[j],ms[j+1])+data[i][j];
                ms[j] = result1;
            }
        }
        return ms[1];
    }

    private static int max(int a,int b){
        return a>b?a:b;
    }

}
