package com.test;

import java.util.Map;
import java.util.TreeMap;

/**
 * 红黑树
 */
public class RBTree<K extends Comparable,V> {

    private static final boolean BLACK = false;
    private static final boolean RED = true;
    private Entry<K,V> root;
    private int modeCount;


    public V get(K key){
        if(key == null){
            throw new NullPointerException("key 不能为空");
        }
        if(root == null){
            throw new NullPointerException("root 不能为空");
        }
        Entry<K,V> p = root;
        if(p.key==key){
            return root.value;
        }else{
            while (p!=null){
                int compare = p.key.compareTo(key);// 大于在右边小于在左边
                if(compare>0){// 右边
                    p = p.right;
                    if(p!=null&&p.key.equals(key)){// 相等
                        return p.value;
                    }
                }else{
                    p = p.left;
                    if(p!=null&&p.key.equals(key)){
                        return p.value;
                    }
                }
            }
        }
        return null;
    }


    public V put(K key,V value){
        if(key==null){
            throw new NullPointerException();
        }
        if(value==null){
            throw new NullPointerException();
        }
        if(root == null){
            root = new Entry<K,V>(key,value,null);
        }else{
            Entry<K,V> p = root;
            Entry<K,V> p1 = null;
            int compare = 0;
            while (p!=null){
                p1 = p;
                compare = p.key.compareTo(key);// 大于在右边小于在左边
                if(compare>0){
                    p = p.right;
                }else if(compare < 0){
                    p = p.left;
                }else{
                    V v = p.value;
                    p.key = key;
                    p.value = value;
                    return v;
                }
            }
            Entry<K,V> e = new Entry<>(key,value,p1);
            e.color=RED;
            if(compare>0){
                p1.right = e;
            }else{
                p1.left = e;
            }
            // todo fixTree
            fixTree(e);
        }
        return null;
    }

    // 如果当前是左节点，则
    // case1:当前节点的父节点是红色，且当前节点的祖父节点的另一个子节点（叔叔节点）也是红色。
    //(01) 将“父节点”设为黑色。
    //(02) 将“叔叔节点”设为黑色。
    //(03) 将“祖父节点”设为“红色”。
    //(04) 将“祖父节点”设为“当前节点”(红色节点)；即，之后继续对“当前节点”进行操作
    // case2:当前节点的父节点是红色，叔叔节点是黑色，且当前节点是其父节点的右孩子
    //(01) 将“父节点”作为“新的当前节点”。
    //(02) 以“新的当前节点”为支点进行左旋。
    // case3:当前节点的父节点是红色，叔叔节点是黑色，且当前节点是其父节点的左孩子
    //(01) 将“父节点”设为“黑色”。
    //(02) 将“祖父节点”设为“红色”。
    //(03) 以“祖父节点”为支点进行右旋。
    //
    private void fixTree(Entry<K,V> p){
        if(colorOf(parentOf(p))==RED) {// 父节点是红色
            while (p!=null&&p != root&&colorOf(root)==BLACK) {
                if (p == leftOf(parentOf(p))) {// 判断当前节点是左节点还是右节点
                    if (colorOf(rightOf(parentOf(parentOf(p)))) == RED) {// 当前节点的叔叔节点是红色
                        // 将父节点设为黑色
                        parentOf(p).color=BLACK;
                        rightOf(parentOf(parentOf(p))).color=BLACK;
                        parentOf(parentOf(p)).color=RED;
                        p=parentOf(parentOf(p));
                    }else{// 叔叔节点是黑色
                        if(p == rightOf(parentOf(p))){
                            // 叔叔节点是黑色并且是父节点的右孩子
                            p = parentOf(p);
                            // 左旋
                            left(p);
                        }
                        // 叔叔节点是黑色且当前节点是父节点的左孩子
                        parentOf(p).color=BLACK;
                        parentOf(parentOf(p)).color=RED;
                        p = parentOf(parentOf(p));
                        // 右旋
                        right(p);
                        break;
                    }
                }
            }
            root.color=BLACK;
        }
    }

    public void remove(K k){
        // 先找到k的Entry
        Entry<K, V> entry = getEntry(k);
        if(entry!=null){

        }
    }

    private Entry<K,V> deleteEntry(Entry<K,V> entry){
        if(entry==null){
            throw new NullPointerException();
        }
        modeCount++;
        if(entry.left!=null&&entry.right!=null){// 有左右两个子叶则获取后继元素
            Entry<K, V> s = successor(entry);
            entry.key = s.key;
            entry.value = s.value;
            entry = s;
        }
        Entry<K,V> e = entry.left!=null?entry.left:entry.right;// 只有一个的时候
        if(e!=null){
            if(entry.parent==null){
                root = e;
            }
            e.parent = entry.parent;
            if(entry==leftOf(parentOf(entry))){
                parentOf(entry).left = null;
            }
            if(entry==rightOf(parentOf(entry))){
                parentOf(entry).right = null;
            }
            entry.parent=null;
            entry.right=null;
            entry.left=null;
            if(colorOf(entry)==BLACK){
                // todo fix
                fixAfterDelete(entry);
            }
        }else if(entry==null){
            root = null;
        }else{
            if(colorOf(entry)==BLACK){
                // todo fix
                fixAfterDelete(entry);
            }
            if(parentOf(entry)==null){
                root = entry;
            }
            if(entry==leftOf(parentOf(entry))){
                parentOf(entry).left = null;
            }
            if(entry==rightOf(parentOf(entry))){
                parentOf(entry).right = null;
            }
        }
        return null;
    }

    // case1:x是"黑+黑"节点，x的兄弟节点是红色。(此时x的父节点和x的兄弟节点的子节点都是黑节点)。
    //(01) 将x的兄弟节点设为“黑色”。
    //(02) 将x的父节点设为“红色”。
    //(03) 对x的父节点进行左旋。
    //(04) 左旋后，重新设置x的兄弟节点。
    // case2:x是“黑+黑”节点，x的兄弟节点是黑色，x的兄弟节点的两个孩子都是黑色。
    //(01) 将x的兄弟节点设为“红色”。
    //(02) 设置“x的父节点”为“新的x节点”。
    // case3:x是“黑+黑”节点，x的兄弟节点是黑色；x的兄弟节点的左孩子是红色，右孩子是黑色的。
    //(01) 将x兄弟节点的左孩子设为“黑色”。
    //(02) 将x兄弟节点设为“红色”。
    //(03) 对x的兄弟节点进行右旋。
    //(04) 右旋后，重新设置x的兄弟节点。
    // case4:x是“黑+黑”节点，x的兄弟节点是黑色；x的兄弟节点的右孩子是红色的，x的兄弟节点的左孩子任意颜色。
    //(01) 将x父节点颜色 赋值给 x的兄弟节点。
    //(02) 将x父节点设为“黑色”。
    //(03) 将x兄弟节点的右子节设为“黑色”。
    //(04) 对x的父节点进行左旋。
    //(05) 设置“x”为“根节点”
    private void fixAfterDelete(Entry<K,V> x){
        while (x!=null&&colorOf(x)==BLACK) {
            Entry<K,V> brother;
            if(x==leftOf(parentOf(x))) {// 当前节点是左节点
                brother = rightOf(parentOf(x));
            }else{// 当前节点是右节点
                brother = leftOf(parentOf(x));
            }
            if (colorOf(parentOf(x)) == RED) {// 兄弟节点是红色
                // 将x兄弟节点设为黑色
                brother.color=BLACK;
                // x的父节点设为红色
                parentOf(brother).color=RED;
                // x的父节点左旋
                left(parentOf(x));
                // 重新设置x的兄弟节点
                if(x==leftOf(parentOf(x))) {// 当前节点是左节点
                    brother = rightOf(parentOf(x));
                }else{// 当前节点是右节点
                    brother = leftOf(parentOf(x));
                }
            }
            if(colorOf(brother)==BLACK&&colorOf(leftOf(brother))==BLACK&&colorOf(rightOf(brother))==BLACK){//兄弟节点为黑色并且叶节点为黑色
                brother.color=RED;
                x = parentOf(x);
            }
            if(colorOf(leftOf(brother))==RED&&colorOf(rightOf(brother))==BLACK){// 兄弟节点是黑色，兄弟节点的左孩子是红色右孩子是黑色
                // 兄弟节点的左孩子设置为黑色
                leftOf(brother).color=BLACK;
                // 兄弟节设为红色
                brother.color=RED;
                // 右旋兄弟节点
                right(brother);
                // 重新定义兄弟节点
                if(x==leftOf(parentOf(x))) {// 当前节点是左节点
                    brother = rightOf(parentOf(x));
                }else{// 当前节点是右节点
                    brother = leftOf(parentOf(x));
                }
            }
            if(colorOf(rightOf(brother))==RED){// x的兄弟节点的右孩子是红色的，x的兄弟节点的左孩子任意颜色。
                brother.color=colorOf(parentOf(x));
                parentOf(x).color=BLACK;
                rightOf(brother).color=BLACK;
                left(parentOf(x));
                root = x;
            }
        }
    }

    private Entry<K,V> successor(Entry<K,V> e){
        Entry<K,V> result = e;
        if(result.right!=null){// 有右子叶
            result = rightOf(result);
            while (result.left!=null){// 获取到最左边
                result = result.left;
            }
        }else{
           Entry<K,V> p = result.parent;
           while (p!=null&&result==rightOf(p)){
               result = p;
               p = p.parent;
           }
        }
        return result;
    }

    private Entry<K,V> getEntry(K k){
        Entry<K,V> e = root;
        while (e!=null){
            int compare = k.compareTo(e.key);
            if(compare>0){
                e = e.right;
            }else if(compare<0){
                e = e.left;
            }else{
                break;
            }
        }
        return e;
    }

    // 左旋
    private void left(Entry<K,V> p){
        Entry<K,V> right = p.right;// p的右孩子
        p.right = right.left;
        if(right.left!=null)right.left.parent=p;// 把左孩子的父节点设置成原来的p
        if(parentOf(p)==null)
            root = right;
        // 判断当前是左孩子还是右孩子
        if(p==leftOf(parentOf(p)))// 左孩子
            parentOf(p).left=right;
        else
            parentOf(p).right=right;
        right.parent=p.parent;
        p.parent=right;
        right.left=p;
    }

    // 右旋
    private void right(Entry<K,V> p){
        Entry<K,V> left = p.left;// 当前节点的左孩子
        p.left = left.right;
        if(left.right!=null)left.right.parent=p;// 把右孩子的父节点设置成p
        if(parentOf(p)==null)
            root = p;
        // 判断p是左孩子还是右孩子
        if(p==leftOf(parentOf(p)))
            parentOf(p).left=left;
        else
            parentOf(p).right=left;
        left.parent=p.parent;
        p.parent=left;
        left.right=p;
    }

    private Entry<K,V> parentOf(Entry<K,V> p){
        return p.parent;
    }

    private Entry<K,V> leftOf(Entry<K,V> p){
        return p.left;
    }

    private Entry<K,V> rightOf(Entry<K,V> p){
        return p.right;
    }

    private Boolean colorOf(Entry<K,V> p){
        return p.color;
    }

    class Entry<K,V>{
        private Entry<K,V> left;
        private Entry<K,V> right;
        private Entry<K,V> parent;
        private K key;
        private V value;
        private boolean color = BLACK;

        Entry(K key,V value,Entry<K,V> parent){
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

    }

    public static void main(String[] args) {
//        Map<Integer,Integer> map = new TreeMap<>();
//        map.put(1,1);
//        map.put(2,2);
//        map.put(3,3);
//        map.put(4,4);
//        map.put(5,5);
//        map.put(6,6);
//        map.remove(5);
//        System.out.println(map.toString());
        RBTree<Integer,Integer> map = new RBTree<>();
    }

}
