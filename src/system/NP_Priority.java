package system;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class NP_Priority {

    private static LinkedBlockingQueue<Process> list;    
    private ArrayList<Process> copyList; 
    
    /*This Comparator defiinition is the same as the SJF but it compares based on the 
    process's priority as opposed to CPU burst time*/
    private static Comparator<Process> sjfComparator = new Comparator<Process>(){
        @Override
        public int compare(Process p1,Process p2){
            if(p1 == null) return -1;            
            if(p2 == null) return 1;

            int p1Prio = p1.getProcessPriority(); // save the process priority value
            int p2Prio = p2.getProcessPriority();
            long p1Arrive = p1.getArrivalTime();
            long p2Arrive = p2.getArrivalTime();
            
            if(p1Prio != p2Prio){
                return (p1Prio > p2Prio)? 1 : -1; // return the highest priority, 
            } 
            return  (p1Arrive > p2Arrive)? 1 : -1; // return the highest arrival time
        }
    };
    
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATION
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public NP_Priority(){
        list = new LinkedBlockingQueue<Process>();
        copyList = new ArrayList<Process>();        
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> GETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    public static LinkedBlockingQueue<Process> getList(){
        return NP_Priority.list;
    }
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> SETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public void setList(LinkedBlockingQueue<Process> l){        
        NP_Priority.list = l;              
    }

    public void makeCopy(Process p){
        if(!copyList.contains(p))
            copyList.add(p);
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATIONS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    
    private Process sjfSorter(LinkedBlockingQueue<Process> list){        
        Process process = Collections.min(NP_Priority.list,NP_Priority.sjfComparator); // choose the lowest priority based on the comparator      
        list.remove(process);
        return process;
    }

    int i=0;
    public void cpuProccess(){
        
        Process p;
        while(true){
            if(list.size()!=0){
                p = sjfSorter(list); // gets the min priority value
            
                makeCopy(p);
                p.setProcessState(2); // enters state 2 (running) before running
                p.upCpuBurst(-Utility.time());
                for(int j = 0; j<p.getTimeSpec().getFirst().getCpuTime();j++){
                    p.run(); // fills array
                    Utility.sleep();
                }
                p.upCpuBurst(Utility.time());
                
                
                /* This loop deals when there is IO burst following the above CPU burst*/
                if(p.getTimeSpec().getFirst().getIoTime() != 0){ // if there is an IO burst following CPU burst
                    // move this process into the Device queue
                    // the device thread runs every second and it executes any IO from the queue's head
                    p.setProcessState(3); // waiting state as it enters IO waiting queue.
                    Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p, NP_Priority.getList());                                      
                    d.deviceEnqueu(p);
                }
                
                i = 0;
            }else{ 
                i++;            
            }
            
            if(i == 10){
                break;
            }  
            Utility.sleep();
        }
        Utility.calculate(copyList); 
    }
}

