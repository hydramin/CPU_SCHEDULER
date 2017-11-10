package system;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class RR{
    private static LinkedBlockingQueue<Process> list; // ready queue
    private ArrayList<Process> copyList;    
    private int runLimit;                   // the time slice designated for the Round Robin scheduler
    
    public RR(){
        runLimit = 2;
        copyList = new ArrayList<Process>();
    }
 /**
     * Setter of the ready queue,  runs O(1) 
     * @pre parameter must not be null
     * @post assigns the argument as the ready queue reference
     * @return void
     */
    public void setList(LinkedBlockingQueue<Process> l){
       list = l;       
    }

    public static LinkedBlockingQueue<Process> getList(){
        return RR.list;
    }
/**
     * Method that stores a unique reference of all Process classes  runs O(n)
     * @pre arugument must not be null
     * @post saves a reference of the argument in the arraylist copyList
     * @return void
     */
    public void makeCopy(Process p){        
        if(!copyList.contains(p))
            copyList.add(p);
    }
       

    int i=0;
    /**
     * Method is used to simulate a CPU that accepts processes from a round robin scheduler
     * This method runs O(n^2), the while loop may be infinite but it stops when the ready queue is empty
     * @pre ready queue must not be empty
     * @post it schedules processes in round robin fashion based on the time slice specified
    */
    public void cpuProccess(){
        Process p;
        while(true){
            if(list.size()!=0){
                p = list.poll();
                makeCopy(p);
                p.setProcessState(2); // enters state 2 (running) before running

                p.upCpuBurst(-Utility.time());                    
                for(int j = 0; j<runLimit;j++){                        
                    if(p.getTimeSpec().getFirst().getCpuTime()==0){ // breaks if the print is done                       
                        break;
                    }else{
                        p.run(); // fills array
                        p.getTimeSpec().getFirst().downCpuTime();
                        Utility.sleep();                    
                    }
                }                    
                p.upCpuBurst(Utility.time());

                if(p.getTimeSpec().getFirst().getCpuTime() != 0){// if the process has unfinished CPU burst time, it is enqueued back                        
                        list.offer(p);
                }  
                
                /* This loop deals when there is IO burst following the above CPU burst*/
                // if there is an IO burst following CPU burst and the CPU burst time is done to 0, process is transfered to device queue
                if(p.getTimeSpec().getFirst().getIoTime() != 0 && p.getTimeSpec().getFirst().getCpuTime() == 0){ 
                    // move this process into the Device queue
                    // the device thread runs every second and it executes any IO from the queue's head
                    p.setProcessState(3); // waiting state as it enters IO waiting queue.
                    Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p, RR.getList());
                    
                    d.deviceEnqueu(p);
                    i = 0;
                }
                    
            }else{
                i++;            
                if(i == 10){ // 10 second wait before closing RR scheduler
                    break;
                }  
            }
            Utility.sleep(); // context switch time
        }

        Utility.calculate(copyList); 
    }
}