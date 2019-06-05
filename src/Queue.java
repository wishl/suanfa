import java.util.Iterator;
import java.util.Scanner;

public class Queue<Item> implements Iterable<Item>  
{  
    private Node first; // ָ��������ӵĽ�������
    
    private Node last; // ָ�������ӵĽ�������
    
    private int N; // ������Ԫ�ص����� 
    
    private class Node  
    {  
        Item item;  
        Node next;  
    }  
    
    public boolean isEmpty() 
    { 
    	return first == null;  // Or: N == 0.  
    }
    
    public int size() 
    { 
    	return N;   
    }
    
    public void enqueue(Item item)  
    { //���β���Ԫ��  
        Node oldlast = last;  
        last = new Node();  
        last.item = item;  
        last.next = null;  
        
        if (isEmpty()) 
    	{
        	first = last;  
    	}
        else 
    	{
        	oldlast.next = last;    	
    	}
        N++;  
    }  
    
    public Item dequeue()  
    { // �ӱ�ͷɾ��Ԫ��
        Item item = first.item;  
        first = first.next;  
        if (isEmpty()) 
    	{
        	last = null;  
    	}
        N--;  
        return item;  
    }  
    
    public Iterator<Item> iterator()  
    {
    	return new ListIterator(); 
    }
    
    private class ListIterator implements Iterator<Item>  //ʵ�ֵ�����
    {  
        private Node current = first; 
        
        public boolean hasNext()  
        { 
        	return current != null;
        }  
        public void remove() 
        { 
        	//null
        }  
        public Item next()  
        {  
	        Item item = current.item;  
	        current = current.next;  
	        return item;  
        }
    }
    
    public static void main(String[] args)  
    { // Create a queue and enqueue/dequeue Double.  
    	Queue<Double> q = new Queue<Double>(); 
    	String data = "10.0 20.0 30.0 40.0";
	    Scanner sc = new Scanner(data);
	    while (sc.hasNext())  
	    {	    	
	    	q.enqueue(sc.nextDouble());
	    }
        sc.close();
        
        int N = q.size();   
        for (int i = 0; i < N; i++)  
        {
        	System.out.println(q.dequeue());
        }        
    } 
} 
