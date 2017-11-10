package system;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class P_SJF {

    private static LinkedBlockingQueue<Process> list;    
    private ArrayList<Process> copyList; 
    private int quantum; // this field is the same as the rumLimit for RR scheduler
    private static Comparator<Process> sjfComparator = new Comparator<Process>(){ // same comparator as the non preemptive sjf
        @Override
        public int compare(Process p1,Process p2){
            if(p1 == null) return -1;            
            if(p2 == null) return 1;
            
            int p1cpu = p1.getTimeSpec().getFirst().getCpuTime();           
            int p2cpu = p2.getTimeSpec().getFirst().getCpuTime();           
            long p1Arrive = p1.getArrivalTime();
            long p2Arrive = p2.getArrivalTime();
            
            if(p1cpu != p2cpu){
                return (p1cpu > p2cpu)? 1 : -1;
            } 
            return  (p1Arrive > p2Arrive)? 1 : -1;
        }
    };
    
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATION
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public P_SJF(){
        list = new LinkedBlockingQueue<Process>();
        copyList = new ArrayList<Process>();
        quantum = 2;
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> GETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    public static LinkedBlockingQueue<Process> getList(){
        return P_SJF.list;
    }
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> SETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public void setList(LinkedBlockingQueue<Process> l){        
        P_SJF.list = l;              
    }

    public void makeCopy(Process p){
        if(!copyList.contains(p))
            copyList.add(p);
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATIONS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    private Process sjfSorter(LinkedBlockingQueue<Process> list){                
        Process process = Collections.min(P_SJF.list,P_SJF.sjfComparator);
        list.remove(process);
        return process;
    }

    int i=0;
    public void cpuProccess(){
        
        Process p;
        while(true){
            if(list.size()!=0){
                p = sjfSorter(list);
            
                makeCopy(p);
                p.setProcessState(2); // enters state 2 (running) before running
                p.upCpuBurst(-Utility.time());
               
                /* CPU runs for the time slice specified in the constructor*/
                for(int j = 0; j < quantum; j++){ 
                    if(p.getTimeSpec().getFirst().getCpuTime()==0){ // breaks if the print is done                       
                        break;
                    }else{
                        p.run(); // fills array
                        p.getTimeSpec().getFirst().downCpuTime(); // decrease the CPU time till it gets to zero
                        Utility.sleep();                    
                    }                    
                }                
                p.upCpuBurst(Utility.time());

                if(p.getTimeSpec().getFirst().getCpuTime() != 0){                        
                    list.offer(p);
               } 
                /* This loop deals when there is IO burst following the above CPU burst*/
                if(p.getTimeSpec().getFirst().getIoTime() != 0 && p.getTimeSpec().getFirst().getCpuTime() == 0){ // if there is an IO burst following CPU burst
                    // move this process into the Device queue
                    // the device thread runs every second and it executes any IO from the queue's head
                    p.setProcessState(3); // waiting state as it enters IO waiting queue.
                    Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p, P_SJF.getList());                    
                    d.deviceEnqueu(p);
                }else{
                    if(p.getTimeSpec().getFirst().getIoTime() == 0)
                        p.getTimeSpec().removeFirst();
                }
                
                i = 0;
            }else{; 
                i++;            
                if(i == 10){
                    break;
                }  
            }
            Utility.sleep(); // context switch time
        }
        Utility.calculate(copyList); 
    }
}

