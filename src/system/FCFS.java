package system;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;


public class FCFS implements Runnable{

    private LinkedBlockingQueue<Process> list;    
    // private ExecutorService thread;
    
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATION
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public FCFS(){
        // timeThread();
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> GETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    public LinkedBlockingQueue<Process> getList(){
        return this.list;
    }
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> SETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public void setList(LinkedBlockingQueue<Process> l){
       list = l;       
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATIONS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    @Override
    public void run(){
        numPrint1();
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

    public void numPrint1(){
        // Iterator<Process> it = list.iterator();
        Process p;
        while(true){
            if(list.size()!=0){
                p = list.poll();
                for(int i=0;i<p.getTimeSpec().size();i++){ // sequence of instructions
                    System.out.println("CPU BURST HAPPENS" + p);
                    for(int j = 0; j<p.getTimeSpec().getFirst().getCpuTime();j++){
                        p.run(); // fills array
                        // System.out.printf("This is CPU time: ",p.getTimeSpec()[i].getCpuTime());
                    }
                    
                    System.out.println("IO BURST HAPPENS" + p);
                    /* This loop deals when there is IO burst following the above CPU burst*/
                    if(p.getTimeSpec().getFirst().getIoTime() != 0){ // if there is an IO burst following CPU burst
                        // move this process into the Device queue
                        // the device thread runs every second and it executes any IO from the queue's head

                        Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p);
                        // System.out.println(d == null);
                        d.deviceEnqueu(p);
                        d.setReturnQueue(list);
                    }
                    /*for(int k = 0; k<p.getTimeSpec()[i].getIoTime();k++){
                        p.ioRun(p.getTimeSpec()[i].getDevice());
                        // System.out.printf("This is I/O time: ",p.getTimeSpec()[i].getIoTime());
                    }*/
                }
            }else{
                // System.exit(0);
                // break;     
                System.out.println("Empty Queue");           
            }

            System.out.println("{}{}{}{}{}<><><> "+list);
    
            try{
            Thread.sleep(500L);
            }catch (InterruptedException e){
                System.out.println(e);
            }
        }
    }
}

