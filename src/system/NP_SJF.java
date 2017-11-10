package system;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class NP_SJF {

    private static LinkedBlockingQueue<Process> list;    
    private ArrayList<Process> copyList; 
    /* This comparator is used to select the Process with the shortest CPU time, if CPU time is 
    equal, then arrival time is chosen*/
    private static Comparator<Process> sjfComparator = new Comparator<Process>(){
        @Override
        public int compare(Process p1,Process p2){
            if(p1 == null) return -1;            
            if(p2 == null) return 1;

            int p1cpu = p1.getTimeSpec().getFirst().getCpuTime(); // get CPU  time for Process 1
            int p2cpu = p2.getTimeSpec().getFirst().getCpuTime(); // get CPU time for Process 2
            long p1Arrive = p1.getArrivalTime();                    // get arrival time for Process 1
            long p2Arrive = p2.getArrivalTime();                // get arrival time for Process 2
            
            if(p1cpu != p2cpu){                 
                return (p1cpu > p2cpu)? 1 : -1; // return the 1 for the process that has greater CPU time
            } 
            return  (p1Arrive > p2Arrive)? 1 : -1; // else return the 1 for the process that has later arrival time time
        }
    };
    
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATION
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public NP_SJF(){
        list = new LinkedBlockingQueue<Process>();
        copyList = new ArrayList<Process>();
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> GETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    public static LinkedBlockingQueue<Process> getList(){
        return NP_SJF.list;
    }
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> SETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public void setList(LinkedBlockingQueue<Process> l){        
        NP_SJF.list = l;              
    }

    public void makeCopy(Process p){
        if(!copyList.contains(p))
            copyList.add(p);
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATIONS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    /**
     * This method returns the process that has the minimum CPU time or the earliest arrival time from the ready queue
     */
    private Process sjfSorter(LinkedBlockingQueue<Process> list){                
        Process process = Collections.min(list,NP_SJF.sjfComparator); // use the comparator above and the Collections.min function
        list.remove(process); // remove the process from the ready queue
        return process; // return the process and pass it to CPU simulating loop loop
    }

    int i=0;
    public void cpuProccess(){
        
        Process p;
        while(true){
            if(list.size()!=0){
                p = sjfSorter(list);   // obtain the Process with the least CPU time, every time the while loop returns
                makeCopy(p);           
                p.setProcessState(2); // enters state 2 (running) before running
                p.upCpuBurst(-Utility.time());
                for(int j = 0; j<p.getTimeSpec().getFirst().getCpuTime();j++){ // run for as much as the CPU time requires
                    p.run(); // fills array
                    Utility.sleep(); // delay for a second
                }
                p.upCpuBurst(Utility.time());
                
                /* This loop deals when there is IO burst following the above CPU burst*/
                if(p.getTimeSpec().getFirst().getIoTime() != 0){ // if there is an IO burst following CPU burst
                    // move this process into the Device queue
                    // the device thread runs every second and it executes any IO from the queue's head
                    p.setProcessState(3); // waiting state as it enters IO waiting queue.
                    Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p, NP_SJF.getList());
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

