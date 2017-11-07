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
    // private ExecutorService thread;
    private static Comparator<Process> sjfComparator = new Comparator<Process>(){
        @Override
        public int compare(Process p1,Process p2){
            
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
    public NP_SJF(){
        list = new LinkedBlockingQueue<Process>();
        copyList = new ArrayList<Process>();
        // timeThread();
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

    // public void sleep(){
    //     try {
    //         Thread.sleep(500L);
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }

    private Process sjfSorter(LinkedBlockingQueue<Process> list){                
        Process process = Collections.min(list,NP_SJF.sjfComparator);
        list.remove(process);
        return process;
    }

    int i=0;
    public void cpuProccess(){
        
        Process p;
        while(true){
            if(list.size()!=0){
                // System.out.println("NP_SJF LIST "+list);
                // p = list.poll();
                p = sjfSorter(list);
            
                makeCopy(p);
                // System.out.println("POLLED : "+p);

                p.setProcessState(2); // enters state 2 (running) before running
                p.upCpuBurst(-Utility.time());
                for(int j = 0; j<p.getTimeSpec().getFirst().getCpuTime();j++){
                    p.run(); // fills array
                    Utility.sleep();
                    // System.out.printf("This is CPU time: ",p.getTimeSpec()[i].getCpuTime());
                }
                p.upCpuBurst(Utility.time());
                
                // System.out.println("IO BURST HAPPENS" + p);
                /* This loop deals when there is IO burst following the above CPU burst*/
                if(p.getTimeSpec().getFirst().getIoTime() != 0){ // if there is an IO burst following CPU burst
                    // move this process into the Device queue
                    // the device thread runs every second and it executes any IO from the queue's head
                    p.setProcessState(3); // waiting state as it enters IO waiting queue.
                    Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p, NP_SJF.getList());
                    // System.out.println(d == null);
                    // Device.setReturnQueue(FCFS.getList());
                    d.deviceEnqueu(p);
                }
                
                i = 0;
            }else{
                // System.exit(0);
                // break;     
                // System.out.println("==>"+i); 
                i++;            
            }
            
            if(i == 10){
                break;
            }  
            // System.out.println("{}{}{}{}{}<><><> "+list);
    
            Utility.sleep();
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

