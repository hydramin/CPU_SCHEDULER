package system;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ML_Feedback{

    private static LinkedBlockingQueue<Process> rrQueue0;
    private static LinkedBlockingQueue<Process> rrQueue1;
    private static LinkedBlockingQueue<Process> fcfsQueue;
    // private RR_MLFQ roundRobin0;
    // private RR_MLFQ roundRobin1;
    // private FCFS fcfServe;
    private ArrayList<Process> copyList;     

    public ML_Feedback(LinkedBlockingQueue<Process> rrQueue0/*, LinkedBlockingQueue<Process> rrQueue1, LinkedBlockingQueue<Process> fcfsQueue*/){
        ML_Feedback.rrQueue0 = rrQueue0;
        ML_Feedback.rrQueue1 = new  LinkedBlockingQueue<Process>();
        ML_Feedback.fcfsQueue = new  LinkedBlockingQueue<Process>();
        copyList = new ArrayList<Process>();
        
        // roundRobin0 = new RR_MLFQ();
        // roundRobin1 = new RR_MLFQ();
        // fcfServe = new FCFS();        
        // roundRobin0.setList(ML_Feedback.rrQueue0);
        // roundRobin1.setList(ML_Feedback.rrQueue1);
        // fcfServe.setList(ML_Feedback.fcfsQueue);
    }

    public static LinkedBlockingQueue<Process> getList(){
        return ML_Feedback.rrQueue0;
    }

    public void makeCopy(Process p){        
        if(!copyList.contains(p))
            copyList.add(p);
    }
    int i = 0;
    public void cpuProcess(){
        boolean swich = false;
        while(true){
            if(!rrQueue0.isEmpty()){
                swich = true;
                this.cpuProccessRR(swich, rrQueue0, 6, rrQueue1, null);
                swich = false;
            }else if(!rrQueue1.isEmpty()){
                swich = true;
                this.cpuProccessRR(swich, rrQueue1, 12, fcfsQueue, rrQueue1);
                swich = false;
            }else if(!fcfsQueue.isEmpty()) {
                swich = true;
                this.cpuProccessFC(swich, fcfsQueue, rrQueue1);
                swich = false;
            }else{
                // loop exit
                i++;
                if(i==10)
                    break;
            }
            Utility.sleep();
        }
        System.out.println("Finished work.");
        calculate();
    }

    
    public void cpuProccessRR(boolean swich, LinkedBlockingQueue<Process> list, int runLimit, LinkedBlockingQueue<Process> next, LinkedBlockingQueue<Process> above){        
        Process p;
        while(swich){
            swich = false;
            if(!list.isEmpty()){
                // System.out.println("RR_MLFQ LIST "+list);
                System.out.println("RR_Queue ID "+runLimit);
                p = list.poll();
                makeCopy(p);
                // System.out.println("POLLED : "+p);
                
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

                    if(p.getTimeSpec().getFirst().getCpuTime() != 0){                        
                        next.offer(p);
                    }  
                    
                    // System.out.println("IO BURST HAPPENS" + p);
                    /* This loop deals when there is IO burst following the above CPU burst*/
                    if(p.getTimeSpec().getFirst().getIoTime() != 0 && p.getTimeSpec().getFirst().getCpuTime() == 0){
                        LinkedBlockingQueue<Process> temp = (above == null)? next :above ;
                        Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p, temp);                        
                        d.deviceEnqueu(p);
                    }
            }else{
                // System.exit(0);
                // break;     
                // System.out.println("==>"+i); 
                           
            }    
        }
    }

    public void cpuProccessFC(boolean swich, LinkedBlockingQueue<Process> list, LinkedBlockingQueue<Process> above){        
        Process p;
        while(true){
            if(list.size()!=0){
                // System.out.println("FCFS LIST "+FCFS.list);
                System.out.println("FCFS LIST Running!");
                
                p = list.poll();
                makeCopy(p);
                // System.out.println("POLLED : "+p);
               
                    p.setProcessState(2); // enters state 2 (running) before running                    
                    p.upCpuBurst(-Utility.time());
                    for(int j = 0; j<p.getTimeSpec().getFirst().getCpuTime();j++){
                        p.run(); // fills array
                        Utility.sleep();                        
                    }                    
                    p.upCpuBurst(Utility.time());
                    
                    // System.out.println("IO BURST HAPPENS" + p);
                    /* This loop deals when there is IO burst following the above CPU burst*/
                    if(p.getTimeSpec().size() !=0 && p.getTimeSpec().getFirst().getIoTime() != 0){ // if there is an IO burst following CPU burst                     
                        p.setProcessState(3); // waiting state as it enters IO waiting queue.
                        
                        Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p, above);                       
                        d.deviceEnqueu(p);
                    }else{
                        if( p.getTimeSpec().size() != 0 && p.getTimeSpec().getFirst().getIoTime() == 0)
                            p.getTimeSpec().removeFirst();
                    }   
            }else{
                // System.exit(0);
                // break;     
                // System.out.println("==>"+i); 
                           
            }                
        }
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
        
        for (Process var : copyList) {
            System.out.println(var.tester);
        }
    }

    /**
     * @return the rrQueue0
     */
    public static LinkedBlockingQueue<Process> getRrQueue0() {
        return ML_Feedback.rrQueue0;
    }

     /**
     * @return the rrQueue1
     */
    public static LinkedBlockingQueue<Process> getRrQueue1() {
        return ML_Feedback.rrQueue1;
    }

    /**
     * @return the fcfsQueue
     */
    public static LinkedBlockingQueue<Process> getFcfsQueue() {
        return ML_Feedback.fcfsQueue;
    }
}