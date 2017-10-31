package system;


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Main{

    public static void main(String[] args) {        
        LinkedBlockingQueue<Process> list = new LinkedBlockingQueue<>();
        for(int i=65;i<65+5;i++){
            Process p = new Process((char) i);
            list.offer(p);
        }
        System.out.println("hi there");
        // FCFS f = new FCFS();
        RR f = new RR();
        
        
        System.out.println("RR created");
        f.setList(list);
        ProcessGen.generate(f.getList());
        System.out.println("RR took in list");
        f.numPrint();
        System.out.println("Die Fool!");
    }
}