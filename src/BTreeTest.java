import java.util.Arrays;

public class BTreeTest {

    private int count;// 操作数
    private static int M;// 阶数
    private Node root;// 根节点

    public static int getM() {
        return M;
    }

    public static void setM(int m) {
        M = m;
    }

    class Node{
        int h;// 高度
        int n;// 元素数(>0&&<=M-1)
        Data[] data;// 数据
        Node[] next = new Node[M];// 子数据
        Node parent;// 上级数据

        public int getN() {
            return n;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Data[] getData() {
            return data;
        }

        public Node[] getNext() {
            return next;
        }

        public void setN(int n) {
            this.n = n;
        }

        public void setData(Data[] data) {
            this.data = data;
        }

        public void setNext(Node[] next) {
            this.next = next;
        }

        public Node(int n){
            this(n,null);
        }

        public Node(int n,Data[] data){
            if(n>=M){
                throw new RuntimeException("超范围");
            }
            if(data.length>M){
                throw new RuntimeException("超范围");
            }
            this.n = n;
            this.data = data;
        }

    }

     class Data{
        String key;
        String value;

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(String value) {
            this.value = value;
        }


        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public BTreeTest(int m){
        M = m;
        Node root = new Node(m - 1);
        this.root = root;
    }

    public String put(String key,String value){
        checkKey(key);
        insert(root,root.h,key,value);
        return null;
    }

    private String insert(Node node,int h,String key,String value){
        //步骤：
        //1、插入一个元素时，首先在B树中是否存在，如果不存在，即比较大小寻找插入位置，在叶子结点处结束，然后在叶子结点中插入该新的元素
        //2、如果叶子结点空间足够，这里需要向右移动该叶子结点中大于新插入关键字的元素，如果空间满了以致没有足够的空间去添加新的元素，则将该结点进行“分裂”，将一半数量的关键字元素分裂到新的其相邻右结点中，中间关键字元素上移到父结点中（当然，如果父结点空间满了，也同样需要“分裂”操作）
        //3、当结点中关键元素向右移动了，相关的指针也需要向右移。如果在根结点插入新元素，空间满了，则进行分裂操作，这样原来的根结点中的中间关键字元素向上移动到新的根结点中，因此导致树的高度增加一层
        Data[] datas;
        Data[] data = node.getData();
        if(data==null){
            data = new Data[1];
        }
        String oldValue;
        // 找到插入位置
        if(h==0){
            // m-1个元素
            for (int i = 0; i < data.length-1; i++) {
                if(eq(key,data[i])){
                    oldValue = data[i].getValue();
                    data[i].setKey(key);
                    data[i].setValue(value);
                    return oldValue;
                }
            }
        }else{
            if(less(key,data[0])){// 小于第0个
                Data[] data1 = new Data[data.length+1];

            }
            for (int i = 0; i < data.length - 2; i++) {

                for (int j = data.length-1; j >= i; j--) {
                    data[j] = data[j-1];
                }
            }

//            data[i].key
        }
        return null;
    }

    public String get(String key){
        checkKey(key);
        String result = seach(root, key, root.h);
        return result;
    }

    private String seach(Node node,String key,int h){
        Data[] data = node.getData();
        if (h==0) {// 叶子节点
            for (int i = 0; i < data.length; i++) {
                if(eq(key,data[i])){
                    return data[i].getValue();
                }else{
                    return null;
                }
            }
        }else{// 非叶子节点
            if(eq(key,data[data.length/2])){
                return data[data.length/2].getValue();
            }else if(less(key,data[data.length/2])){// 小于
                if (less(key,data[0])) {// 比最小的小则找最左边的数组
                    if(data[0]!=null){// 如果没有
                        return seach(node.getNext()[0],key,h--);
                    }else {
                        return null;
                    }
                }
                for (int i = 1; i < data.length/2; i++) {
                    if(less(key,data[i])){// 在两个中间
                        return seach(node.getNext()[i],key,h--);
                    }
                }
            }else{// 大于
                if (greater(key,data[0])) {// 比最大的大则找最右边的数组
                    if(data[data.length-1]!=null){// 如果没有
                        return seach(node.getNext()[0],key,h--);
                    }else {
                        return null;
                    }
                }
                for (int i = data.length-2; i > data.length/2; i--) {
                    if(greater(key,data[i])){// 在两个中间
                        return seach(node.getNext()[i],key,h--);
                    }
                }
            }
        }
        return null;
    }

    private void checkKey(String key){
        if(key == null){
            throw new NullPointerException();
        }
    }

    private void checkData(Data data){
        if(data==null){
            throw new NullPointerException();
        }
    }

    private boolean eq(String key,Data key1){
        checkKey(key);
        checkData(key1);
        return key.equals(key1.getKey());
    }

    private boolean less(String key,Data data){
        checkData(data);
        checkKey(key);
        return key.compareTo(data.getKey())<0;
    }

    private boolean greater(String key,Data data){
        checkData(data);
        checkKey(key);
        return key.compareTo(data.getKey())>0;
    }

    private String halfSearch(Data[] data,int start,int end,String key){
        if(start<end){
            throw new RuntimeException("开始位置小于结束位置");
        }
        if(start>data.length||start<0||end>data.length||end<0){
            throw new RuntimeException("参数异常");
        }
        while (start<=end){
            int mid = (start + end)/2;
            if(eq(key,data[mid])){
                return data[mid].getValue();
            }else if(less(key,data[mid])){
                end = mid-1;
            }else{
                start = mid+1;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] i = {1,2,3,4,5};
        int[] a = new int[2];
        int[] b = new int[2];
//        int mid = i.length%2==0?(i.length-1)/2:(i.length-1)/2+1;
        int mid = (i.length-1)/2;
        int k = 0;
        for (int j = 0; j < i.length; j++) {
            if(j<mid){
                a[j] = i[j];
            }else if(j>mid){
                b[k++] = i[j];
            }
        }
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        System.out.println(i[mid]);
    }

}
