package system;


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Main{

    public static void main(String[] args) {        
        LinkedBlockingQueue<Process> list = new LinkedBlockingQueue<>();
        for(int i=65;i<65+5;i++){
            Process p = new Process((char) i, i-60);
            list.offer(p);
        }
        System.out.println("hi there");
        FCFS f = new FCFS();
        
        System.out.println("hi there");
        f.setList(list);
        System.out.println("hi there");
        f.numPrint();
        System.out.println("Die Fool!");
    }
}