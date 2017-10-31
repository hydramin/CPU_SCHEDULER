package system;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;


public class FCFS implements Runnable{

    private LinkedBlockingQueue<Process> list;
    private LinkedBlockingQueue<Process> deviceList;
    // private ExecutorService thread;
    
    public FCFS(){
        // timeThread();
    }

    public LinkedBlockingQueue<Process> getList(){
        return this.list;
    }

    public void setList(LinkedBlockingQueue<Process> l){
       list = l;       
    }

    @Override
    public void run(){
        numPrint();
    }

    public void numPrint(){
        // Iterator<Process> it = list.iterator();
        Process p;
        while(true){
            if(list.size()!=0){
                p = list.poll();
                for(int i=0;i<p.getMaxPrint();i++){                    
                    p.run();                                        
                 }                                           
                System.out.println("");
            }else{
                // System.exit(0);
                break;                
            }

            try{
            Thread.sleep(500L);
            }catch (InterruptedException e){
                System.out.println(e);
            }
        }
    }
}