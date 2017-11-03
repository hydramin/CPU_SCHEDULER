package system;


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Main{

    public static void main(String[] args) {        
        LinkedBlockingQueue<Process> list = new LinkedBlockingQueue<>(); // the list to be passed around
        
        // for(int i=0;i<3;i++){
        //     Process p = new Process(i);
        //     list.offer(p);
        // }
        System.out.println("list size "+list.size());
        // FCFS f = new FCFS();
        // RR f = new RR();
        NP_SJF f = new NP_SJF();
        // ProcessGen.generate(list);
        
        
        System.out.println("RR created");
        f.setList(list);
        // ProcessGen.generate(RR.getList());
        // ProcessGen.generate(FCFS.getList());
        ProcessGen.generate(NP_SJF.getList());
        System.out.println("RR took in list: "+ list.hashCode());
        f.cpuProccess();
        System.out.println("Die Fool!");
    }
}