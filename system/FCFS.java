package system;
// this is a linked list queue that implements the Queue<E> interface defined here
// it can be a singlely linked list

import java.util.LinkedList;

public class FCFS<E> extends LinkedList<E> implements Queue<E>{



    
//<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATIONS
//<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    public void enqueue(E e){
        this.add(e);
    }    

    public E dequeue(){
        E temp = this.getFirst();        
        this.removeFirst();
        return temp;
    }
    public E first(){
        return this.getFirst();
    }
    public int size(){
        return this.size();
    }
    public boolean isEmpty(){
        return this.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}