package system;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class P_Priority {

    private static LinkedBlockingQueue<Process> list;    
    private ArrayList<Process> copyList; 
    private int quantum;
    private static Comparator<Process> sjfComparator = new Comparator<Process>(){
        @Override
        public int compare(Process p1,Process p2){
            if(p1 == null) return -1;            
            if(p2 == null) return 1;

            int p1Prio = p1.getProcessPriority();
            int p2Prio = p2.getProcessPriority();
            long p1Arrive = p1.getArrivalTime();
            long p2Arrive = p2.getArrivalTime();
            
            if(p1Prio != p2Prio){
                return (p1Prio < p2Prio)? 1 : -1;
            } 
            return  (p1Arrive > p2Arrive)? 1 : -1;
        }
    };
    
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATION
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public P_Priority(){
        list = new LinkedBlockingQueue<Process>();
        copyList = new ArrayList<Process>();
        quantum = 2;
        // timeThread();
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> GETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    public static LinkedBlockingQueue<Process> getList(){
        return P_Priority.list;
    }
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> SETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public void setList(LinkedBlockingQueue<Process> l){        
        P_Priority.list = l;              
    }

    public void makeCopy(Process p){
        if(!copyList.contains(p))
            copyList.add(p);
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATIONS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    public void sleep(){
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // private static  long time() {
	// 	long timeMillis = System.currentTimeMillis();
    //     long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);        
	// 	return timeSeconds;
    // }

    
    
    private Process sjfSorter(LinkedBlockingQueue<Process> list){                
        Process process = Collections.max(P_Priority.list,P_Priority.sjfComparator);
        list.remove(process);
        return process;
    }

    int i=0;
    public void cpuProccess(){
        
        Process p;
        while(true){
            if(list.size()!=0){
                // System.out.println("P_Priority LIST "+list);
                
                p = sjfSorter(list);
            
                makeCopy(p);
                // System.out.println("POLLED : "+p);

                p.setProcessState(2); // enters state 2 (running) before running
                p.upCpuBurst(-Utility.time());
               
                for(int j = 0; j < quantum; j++){
                    if(p.getTimeSpec().getFirst().getCpuTime()==0){ // breaks if the print is done                       
                        break;
                    }else{
                        p.run(); // fills array
                        p.getTimeSpec().getFirst().downCpuTime();
                        sleep();                    
                    }                    
                }                
                p.upCpuBurst(Utility.time());

                if(p.getTimeSpec().getFirst().getCpuTime() != 0){                        
                    list.offer(p);
               } 
                
                // System.out.println("IO BURST HAPPENS" + p);
                /* This loop deals when there is IO burst following the above CPU burst*/
                if(p.getTimeSpec().getFirst().getIoTime() != 0 && p.getTimeSpec().getFirst().getCpuTime() == 0){ // if there is an IO burst following CPU burst
                    // move this process into the Device queue
                    // the device thread runs every second and it executes any IO from the queue's head
                    p.setProcessState(3); // waiting state as it enters IO waiting queue.
                    Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p, P_Priority.getList());
                    // System.out.println(d == null);
                    // Device.setReturnQueue(FCFS.getList());
                    d.deviceEnqueu(p);
                }else{
                    if(p.getTimeSpec().getFirst().getIoTime() == 0)
                        p.getTimeSpec().removeFirst();
                }
                
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
    
            Utility.sleep(); // context switch time
        }

        calculate();
    }

    private void calculate(){
        System.out.println("\n\n\nBEHOLD, BEHOLD, BEHOLD\n");
        for (Process pr: copyList) {
            // System.out.printf("Process %c IO ==> %d \n CPU ==> %d\n",pr.chr ,pr.getIoBurst(), pr.getCpuBurst());
            pr.setProcessState(4);
            System.out.println(pr.data());
        }
        double avgCpuBurst = 0;
        double avgIoBurst = 0;
        double avgWaiting=0;
        double turnaroundTime=0;
        Process temp;
        for(int i = 0; i<copyList.size();i++){
            temp = copyList.get(i);
            turnaroundTime += (temp.getTerminationTime() - temp.getCreationTime());
            avgCpuBurst+=(temp.getCpuBurst());
            avgIoBurst+=(temp.getIoBurst());
        }
        avgIoBurst = avgIoBurst/copyList.size();
        avgCpuBurst = avgCpuBurst/copyList.size();
        turnaroundTime = turnaroundTime/copyList.size();
        avgWaiting = turnaroundTime - avgCpuBurst;
        System.out.printf("Avg Waiting: %.2f \nAvg Turnaround: %.2f \nAvg Avg CPU: %.2f \nAvg Avg IO: %.2f\n",avgWaiting,turnaroundTime, avgCpuBurst,avgIoBurst);
        
        System.out.println("\nTime   Record");
        for (Process var : copyList) {
            for (ProcessRecord r : var.getMyRecord()) {
                System.out.printf("%s \n",r);                
            }
            System.out.println("********************************\n*******************************");
        }
    }
}

