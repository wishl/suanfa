package com.izuche.zookeeper;
/**
 * 翻转链表
 */
public class NodeTest {

    static class Node{
        Node next;
        String data;

        public void setData(String data) {
            this.data = data;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return next;
        }

        public String getData() {
            return data;
        }
    }


    public static Node overturn(Node node){
        Node tmp = new Node();
        tmp.setData(node.getData());
        tmp.setNext(node.getNext());
        String data;
        int size = 0;
        while (node!=null){
            node = node.next;
            size++;
        }
        while (size>0) {
            for (int index = size; index > 0; index--) {
                if (node!=null && node.next != null) {
                    data = node.getData();
                    node.setData(node.next.getData());
                    node.next.setData(data);
                    node = node.next;
                }
            }
            node = tmp;
            size--;
        }
        return node;
    }

    public static void main(String[] args) {
        Node son = new Node();
        Node node = null;
        for (int i = 0; i < 3; i++) {
            node = new Node();
            node.setData(i+"");
            node.setNext(son);
            son = node;
        }
        Node n = node;
        while (node!=null){
            System.out.println(node.data);
            node = node.next;
        }
        Node node1 = overturn(n);
        System.out.println("------");
        while (node1!=null){
            System.out.println(node1.data);
            node1 = node1.next;
        }
    }

}
