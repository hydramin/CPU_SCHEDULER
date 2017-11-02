package system;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class FCFS implements Runnable{

    private static LinkedBlockingQueue<Process> list;    
    private ArrayList<Process> copyList; 
    // private ExecutorService thread;
    
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATION
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public FCFS(){
        list = new LinkedBlockingQueue<Process>();
        copyList = new ArrayList<Process>();
        // timeThread();
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> GETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    public static LinkedBlockingQueue<Process> getList(){
        return FCFS.list;
    }
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> SETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public void setList(LinkedBlockingQueue<Process> l){        
       FCFS.list = l;              
    }

    public void makeCopy(Process p){
        if(!copyList.contains(p))
            copyList.add(p);
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATIONS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    @Override
    public void run(){
        numPrint1();
    }

    public void sleep(){
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*public void numPrint(){
        // Iterator<Process> it = list.iterator();
        Process p;
        while(true){
            if(list.size()!=0){
                p = list.poll();
                for(int i=0;i<p.getMaxPrint();i++){                    
                    p.run();  
                    sleep();                                      
                 }                                           
                System.out.println("");
            }else{
                // System.exit(0);
                break;                
            }
            sleep();
        }
    }*/

    // private static  long time() {
	// 	long timeMillis = System.currentTimeMillis();
    //     long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);        
	// 	return timeSeconds;
	// }

    int i=0;
    public void numPrint1(){
        // Iterator<Process> it = list.iterator();
        Process p;
        while(true){
            if(list.size()!=0){
                System.out.println("FCFS LIST "+list);
                p = list.poll();
                makeCopy(p);
                System.out.println("POLLED : "+p);
                //for(int i=0;i<p.getTimeSpec().size();i++){ // sequence of instructions
                    // System.out.println("CPU BURST HAPPENS" + p);
                    p.setProcessState(2); // enters state 2 (running) before running
                    for(int j = 0; j<p.getTimeSpec().getFirst().getCpuTime();j++){
                        p.run(); // fills array
                        sleep();
                        // System.out.printf("This is CPU time: ",p.getTimeSpec()[i].getCpuTime());
                    }
                    
                    // System.out.println("IO BURST HAPPENS" + p);
                    /* This loop deals when there is IO burst following the above CPU burst*/
                    if(p.getTimeSpec().getFirst().getIoTime() != 0){ // if there is an IO burst following CPU burst
                        // move this process into the Device queue
                        // the device thread runs every second and it executes any IO from the queue's head
                        p.setProcessState(3); // waiting state as it enters IO waiting queue.
                        Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p, FCFS.getList());
                        // System.out.println(d == null);
                        // Device.setReturnQueue(FCFS.getList());
                        d.deviceEnqueu(p);
                    }
                    /*for(int k = 0; k<p.getTimeSpec()[i].getIoTime();k++){
                        p.ioRun(p.getTimeSpec()[i].getDevice());
                        // System.out.printf("This is I/O time: ",p.getTimeSpec()[i].getIoTime());
                    }*/
                //}
                i = 0;
            }else{
                // System.exit(0);
                // break;     
                // System.out.println("==>"+i); 
                i++;            
            }
            
            if(i == 30){
                break;
            }  
            // System.out.println("{}{}{}{}{}<><><> "+list);
    
            sleep();
        }

        System.out.println("\n\n\nBEHOLD, BEHOLD, BEHOLD\n");
        for (Process pr: copyList) {
            // System.out.printf("Process %c IO ==> %d \n CPU ==> %d\n",pr.chr ,pr.getIoBurst(), pr.getCpuBurst());
            pr.setProcessState(4);
            System.out.println(pr);
        }
        int first=0, second=0;
        double avgWaiting=0;
        for(int i = 0; i<copyList.size();i++){
            if(i>0)
                second+=copyList.get(i).getArrivalTime();
            if(i<copyList.size()-1)
                first+=copyList.get(i).getArrivalTime();
        }
        avgWaiting = (second - first)/copyList.size();
        System.out.println(avgWaiting);
    }
}

