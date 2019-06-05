package com.test;

public class BTreeTest1 {

    private int h;// 高度
    private int M;// 阶数
    private Node root;// 根节点

    public BTreeTest1(int M){
        this.M = M;
        root = new Node(0);
    }

    class Node{
        private Data[] data = new Data[M];
        private Node parent;
        private int m;// 数据长度

        public void setData(Data data) {
            if(data==null){
                this.data = new Data[M];
            }
            // 先放入在++
            this.data[m] = data;
            m++;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Data[] getData() {
            return data;
        }

        public Node getParent() {
            return parent;
        }

        public Node(int m){
            this.m = m;
        }
    }

    class Data{
        private int m;// 数据长度
        private String key;
        private String value;
        private Node[] next = new Node[2];// 小于key的在第0个,大于在第1个

        public int getm() {
            return m;
        }

        public void setm(int m) {
            this.m = m;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void setNext(Node[] next) {
            this.next = next;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public Node[] getNext() {
            return next;
        }
    }

    public String get(String key){
        checkKey(key);
        String value = search(h, key, root);
        return value;
    }

    private String search(int h,String key,Node node){
        Data[] data = node.getData();
        if(h==0){// 叶子结点
            for (int i = 0; i < node.m; i++) {
                if(eq(key,data[i])){
                    return data[i].getValue();
                }
            }
        }else{// 非叶子节点
            if(greater(key,data[node.m-1])){// 比最大的大则找最右边的
                return search(h--,key,data[node.m-1].getNext()[1]);
            }
            for (int i = 0; i < node.m; i++) {
                if(eq(key,data[i])){
                    return data[i].getValue();
                }
                if(less(key,data[i])){
                    return search(h--,key,data[i].getNext()[0]);
                }
            }
        }
        return null;
    }

    public String put(String key,String value){
        checkKey(key);
        return null;
    }

    public String insert(String key,String value,int h,Node node){
        Data[] data = node.getData();
        String oldValue = null;
        // todo 找插入位置并插入
        if(h==0){// 叶子结点
            if(greater(key,data[node.m-1])){// 比最大的大则插入最右边
                node.m++;
                data[node.m].setKey(key);
                data[node.m].setValue(value);
            }else {
                for (int i = 0; i < node.m; i++) {
                    if (eq(key, data[i])) {
                        oldValue = data[i].getValue();
                        data[i].setKey(key);
                        data[i].setValue(value);
                    } else {
                        if (less(key, data[i])) {// 比第i个数据小则插入左边
                            for (int j = data.length - 2; j >= i; j--) {
                                data[j + 1] = data[j];// 如果是M阶,则第M个数据是空,方便位移
                            }
                            data[i].setKey(key);
                            data[i].setValue(value);
                            node.m++;
                        }
                    }
                }
            }
        }else{// 非叶子节点
            if(greater(key,data[node.m-1])){// 比最大的大则找右边子叶
                return insert(key,value,h--,data[node.m-1].getNext()[1]);
            }
            for (int i = 0; i < node.m; i++) {
                if(eq(key,data[i])){
                    oldValue = data[i].getValue();
                    data[i].setKey(key);
                    data[i].setValue(value);
                    break;
                }else{
                    if (less(key, data[i])) {// 比第i个数据小则在左边的子叶里找
                        return insert(key,value,h--,data[i].getNext()[0]);
                    }
                }
            }
        }
        // 判断是否需要分裂
        if(node.m>=M){// 需要分裂
            split(node);
        }
        return oldValue;
    }

    private void split(Node node){
        Data[] data = node.getData();
        Node left = new Node(0);
        Node right = new Node(0);
        int mid = (M-1)/2;
        for (int i = 0; i < mid; i++) {
            if(i<mid){
                left.setData(data[i]);
            }else if(i>mid){
                right.setData(data[i]);
            }
        }
        Node parent = node.getParent();
        if(parent==null){
            parent = new Node(0);
            parent.setData(data[mid]);
            right.setParent(parent);
            left.setParent(parent);
        }else{
            left.setParent(parent);
            right.setParent(parent);
            if(greater(data[mid].key,data[node.m-1])){// 比最大的大则放入最右边
                parent.setData(data[mid]);
                Data[] pData = parent.getData();
                // 改变指针指向
                pData[node.m-2].next[1] = left;
            }
            for (int i = 0; i < node.m; i++) {
                if(less(data[mid].key,parent.data[i])){// 如果小于则放入当前元素的左边
                    for (int j = i; j < parent.getData().length-2; j++) {
                        parent.data[j+1] = parent.data[j];
                    }
                    parent.data[i] = data[mid];
                    parent.m++;
                    if(i>0){
                        parent.data[i-1].next[1] = left;
                        parent.data[i+1].next[0] = right;
                    }else{
                        parent.data[i+1].next[0] = right;
                    }
                }
            }
        }
        if(parent.m>=M){// 父节点也需要分裂
            split(parent);
        }
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

    private boolean eq(String key, Data key1){
        checkKey(key);
        checkData(key1);
        return key.equals(key1.getKey());
    }

    private boolean less(String key, Data data){
        checkData(data);
        checkKey(key);
        return key.compareTo(data.getKey())<0;
    }

    private boolean greater(String key, Data data){
        checkData(data);
        checkKey(key);
        return key.compareTo(data.getKey())>0;
    }

    // 获取最小的数据数
    public int ceil(int m){
        return (int)Math.ceil(m/(double)2);
    }

    // 获取key在b树中的位置
    public Node getNode(String key,Node node){
        Data[] data = node.getData();
        while (hasNext(node)) {
            for (int i = 0; i < node.m; i++) {
                if (eq(key, data[i])) {// 相等
                    return node;
                } else if (i == node.m - 1) {// 比最后一个大最后一个则取右孩子
                    node = data[i].next[i];
                } else if (less(key, data[i])&&less(key,data[i+1])) {// 小于
                    node = data[i].next[0];// 取左边的node
                }
            }
        }
        return null;
    }

    public boolean hasNext(Node node){
        return checkNode(node)?node.data[0].next!=null&&node.data[0].next.length!=0:false;
    }

    private boolean checkNode(Node node){
        if(node==null){
            return false;
        }
        if(node.data==null||node.data.length==0){
            return false;
        }
        return true;
    }

    // 寻找在b数中比key大的最小值
    public Node getMinNode(String key,Node node){
        // 首先找到key的位置
        node= getNode(key, node);
        Data[] data = node.getData();
        Node node1 = null;
        // 找到比key大的孩子节点
        for (int i = 0; i < node.m; i++) {
            if(eq(key,data[i])){// 找到比key大的孩子节点
                node1 = data[i].getNext()[1];
            }
        }
        if(node1==null){
            return null;
        }
        // 根据孩子节点找到叶节点
        while (hasNext(node1)) {// 叶子节中最小的是整个孩子节点中比key大的最小的key
            node1 = node1.getData()[0].getNext()[0];
        }
        return node1;
    }

    // 聚合,先删除在调用此方法,删除时m--
    public void AddNode(Node node){
        // 找到node的兄弟节点:
        // 1. 找到node.parent中小于node.data[0]的最大的或者大于node.data[data.length-1]的最小的
        // 2. 获取所有子节点
        // 3. 数据量大于ceil((M-1)/2)的子节点是兄弟节点
        // 4. 如果有两个则选择左边的
        Data[] sonData = node.getData();
        Data max = sonData[node.m-1];
        Data min = sonData[0];
        Node parent = node.getParent();
        Data[] data = parent.getData();
        int big = -1,less = -1;
        for (int i = 0; i < parent.m; i++) {
            // 小于key的最大的
            if(i == parent.m-1){// 最后一个数
                if(greater(max.getKey(),data[i])){// 比最大的大
                    big = i;
                }
            } else if(less(min.getKey(),data[i])){// 小于key里最小的
                less = i;
                break;
            } else if(greater(max.getKey(),data[i])&&less(max.getKey(),data[i+1])){// 大于key里最大的
                big = i;
                break;
            }
        }
        // 小于node.data[0]的最大
        // 如果兄弟节点数据够则从兄弟节点获取一个数据然后整合
        // 整合兄弟节点:
        // 1. 替换兄弟节点和父节点的key,value
        // 2. 把兄弟节点放到节点中
        if(less!=-1&&data[less].getNext()!=null&&data[less].getNext().length!=0){
            Node bro = data[less].getNext()[1];// 兄弟节点
            Data broData = bro.getData()[bro.m - 1];//  最大的
            if(checkCeil(bro)){// key赋值给父节点
                String tmpKey = broData.getKey();
                String tmpValue = broData.getValue();
                broData.setValue(data[less].getValue());
                broData.setKey(data[less].getKey());
                data[less].setKey(tmpKey);
                data[less].setValue(tmpValue);
                // 把bro赋值给sonData
                for (int i = 0; i < node.m; i++) {
                    sonData[i+1]=sonData[i];
                }
                sonData[0] = broData;
                node.m++;
                // 删除broData
                bro.getData()[bro.m - 1] = null;
                bro.m--;
            }
        }else if(big!=-1&&data[big].getNext()!=null&&data[big].getNext().length!=0){// 大于node.data[data.length-1]的最小
            Node bro = data[big].getNext()[0];// 兄弟节点
            Data broData = bro.getData()[0];// 最小的
            if(checkCeil(bro)){// 最小的
                // 交换位置
                String tmpKey = broData.getKey();
                String tmpValue = broData.getValue();
                broData.setValue(data[big].getValue());
                broData.setKey(data[big].getKey());
                data[big].setKey(tmpKey);
                data[big].setValue(tmpValue);
                // 把bro赋值给sonData
                node.m++;
                sonData[node.m] = broData;
                // 删除broData
                for (int i = 0; i < bro.m; i++) {
                    bro.getData()[i] = bro.getData()[i+1];
                }
                bro.getData()[bro.m - 1] = null;
                bro.m--;
            }
        }else{// 都不满足则从父节点拿并整合到一起(父节点下移)
            if(less!=-1){// 如果左边有优先使用左边
                // 如果使用左节点则插入左边兄弟节点的右边
                Node bro = data[less].getNext()[1];// 左兄弟节点
                bro.setData(data[less]);
                // 删除之前改变指针(大于key的指针指向bro)
                if(less>parent.m){// 不是最右边的
                    data[less+1].getNext()[0] = bro;
                }
                // 删除data[less]
                for (int i = less; i < parent.m; i++) {
                    data[i] = data[i+1];
                }
                data[parent.m] = null;
                parent.m--;
                // 把原来数据里剩余的data放入左兄弟节点
                for (int i = 0; i < node.m; i++) {
                    bro.setData(sonData[i]);
                }
            }else if(big!=-1){// 否则使用右节点
                Node bro = data[big].getNext()[0];// 有兄弟节点
                Data[] broData = bro.getData();
                // 如果使用右节点则插入右兄弟节点的左边
                for (int i = bro.m-1; i > 0; i--) {
                    broData[i+1] = broData[i];
                }
                broData[0] = data[big];
                bro.m++;
                // 删除之前改变parent的指针
                if(big>0){// 不是最左边的
                    data[big-1].getNext()[1] = bro;
                }
                // 删除data[big]
                for (int i = big; i < parent.m; i++) {
                    data[i] = data[i+1];
                }
                data[parent.m] = null;
                parent.m--;
                // 把原来数据里剩余的data放入右兄弟节点
                for (int i = 0; i < node.m; i++) {
                    for (int j = bro.m; j > 0; j--) {
                        broData[i+1] = broData[i];
                    }
                    broData[0] = data[big];
                    bro.m++;
                }
            }
        }
    }


    public void delete(Node node,String key){
        // 首先找到位置,如果是叶子结点则直接删除,不是就换与比该节点大的最小的节点换key,value
        // 然后直接删除
        Node node1 = getNode(key, node);
        if(node1.getData()[0].getNext()==null||node1.getData()[0].getNext().length==0){// 叶子结点
           deleteYz(node1,key);
        }else{
            Node minNode = getMinNode(key, node);
//            minNode =
        }
    }


    private void deleteYz(Node node1,String key){
        Data[] data = node1.getData();
        for (int i = 0; i < data.length-1; i++) {
            if(eq(key,data[i])){
                for (int j = i; j < data.length-1; j++) {
                    data[j] = data[j+1];
                }
            }
        }
        data[data.length-1]=null;
    }

    private boolean checkCeil(Node node){
        boolean result = checkNode(node);
        if(result){
            // 最小的数据量
            int min = ceil(M - 1);
            result = node.getData().length>min;
        }
        return result;
    }

    public static void main(String[] args) {
//        System.out.println(ceil(5));
    }

}
