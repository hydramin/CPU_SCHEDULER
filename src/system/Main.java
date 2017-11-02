package system;


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Main{

    public static void main(String[] args) {        
        LinkedBlockingQueue<Process> list = new LinkedBlockingQueue<>(); // the list to be passed around
        // for(int i=65;i<65+2;i++){
        //     Process p = new Process((char) i);
        //     list.offer(p);
        // }
        System.out.println("list size "+list.size());
        FCFS f = new FCFS();
        // RR f = new RR();
        // ProcessGen.generate(list);
        
        
        System.out.println("RR created");
        f.setList(list);
        ProcessGen.generate(FCFS.getList());
        System.out.println("RR took in list: "+ list.hashCode());
        f.numPrint1();
        System.out.println("Die Fool!");
    }
}