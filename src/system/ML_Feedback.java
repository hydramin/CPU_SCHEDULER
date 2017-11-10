package system;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ML_Feedback{

    private static LinkedBlockingQueue<Process> rrQueue0; // the high priority ready queue
    private static LinkedBlockingQueue<Process> rrQueue1; // the second priotity ready queue
    private static LinkedBlockingQueue<Process> fcfsQueue; // the third priority ready queue
    private ArrayList<Process> copyList;     

    public ML_Feedback(LinkedBlockingQueue<Process> rrQueue0){
        ML_Feedback.rrQueue0 = rrQueue0;
        ML_Feedback.rrQueue1 = new  LinkedBlockingQueue<Process>();
        ML_Feedback.fcfsQueue = new  LinkedBlockingQueue<Process>();
        copyList = new ArrayList<Process>();        
    }

    public static LinkedBlockingQueue<Process> getList(){
        return ML_Feedback.rrQueue0;
    }

    public void makeCopy(Process p){        
        if(!copyList.contains(p))
            copyList.add(p);
    }

    int i = 0;
    /**
     * Method is the CPU that manages the running priority of the 3 queues used
     * It runs O(n^2), this while loop runs O(n) and each inner cpuProcessXX method run O(n)
    */
    public void cpuProcess(){
        boolean swich = false;
        while(true){
            if(!rrQueue0.isEmpty()){
                swich = true;
                System.out.println("Priority Queue 1 Processes Running!");
                this.cpuProccessRR(swich, rrQueue0, 6, rrQueue1, null);
                swich = false;
                i=0;
            }else if(!rrQueue1.isEmpty()){
                swich = true;
                System.out.println("Priority Queue 2 Processes Running!");
                this.cpuProccessRR(swich, rrQueue1, 12, fcfsQueue, rrQueue1);
                swich = false;
                i=0;
            }else if(!fcfsQueue.isEmpty()) {
                swich = true;
                System.out.println("Priority Queue 3 Processes Running!");
                this.cpuProccessFC(swich, fcfsQueue, rrQueue1);
                swich = false;
                i=0;
            }else{
                i++;
                if(i==10)
                    break;
            }
            Utility.sleep();
        }
        System.out.println("Finished work.");
        Utility.calculate(copyList);  
    }

    /**
     * This method is a copy of the round robin cpu, it is used to simulate a round robin scheduler for the 
     * Multilevel scheduler. It runs O(n^2)
     * @pre rrQueue must not be empty and runLimit must not be zero and swich must be true
     * @post it simulates a round robin scheduler based on the input list and the time slice specified
     * @param swich is used to run the method a single time, must be true upon entrance
     * @param list is the ready current ready queue
     * @param next is the demotion ready queue
     * @param above is the promotion ready queue
     * @param runLimit is the time slice specification
    */
    public void cpuProccessRR(boolean swich, LinkedBlockingQueue<Process> list, int runLimit, LinkedBlockingQueue<Process> next, LinkedBlockingQueue<Process> above){        
        Process p;
        while(swich){ // true when the process starts
            swich = false;
            if(!list.isEmpty()){
               
                System.out.println("RR_Queue ID "+runLimit);
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

                if(p.getTimeSpec().getFirst().getCpuTime() != 0){                        
                    next.offer(p); // if Process doesn't finish CPU run in the time slice given its demoted to the next lower ready queue
                }  
               
                /* This loop deals when there is IO burst following the above CPU burst*/
                if(p.getTimeSpec().getFirst().getIoTime() != 0 && p.getTimeSpec().getFirst().getCpuTime() == 0){
                    LinkedBlockingQueue<Process> temp = (above == null)? next :above ; // if a process goes into IO it is promoted up to a higher priority queue
                                                                                       // if the process is in the top most queue it is enqueued back to the same queue
                    Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p, temp);                        
                    d.deviceEnqueu(p);
                }
            } 
        }
    }

    /**
     * This method is a copy of the FCFS cpu, it is used to simulate a FCFS scheduler for the 
     * Multilevel scheduler. It runs O(n^2)
     * @pre rrQueue must not be empty and runLimit must not be zero and swich must be true
     * @post it simulates a FCFS scheduler based on the input list 
     * @param swich is used to run the method a single time, must be true upon entrance
     * @param list is the ready current FCFS ready queue   
     * @param above is the promotion ready queue
    */
    public void cpuProccessFC(boolean swich, LinkedBlockingQueue<Process> list, LinkedBlockingQueue<Process> above){        
        Process p;
        while(swich){
            swich = false;
            if(list.size()!=0){
                
                System.out.println("FCFS LIST Running!");
                
                p = list.poll();
                makeCopy(p);
               
                p.setProcessState(2); // enters state 2 (running) before running                    
                p.upCpuBurst(-Utility.time());
                for(int j = 0; j<p.getTimeSpec().getFirst().getCpuTime();j++){
                    p.run(); // fills array
                    Utility.sleep();                        
                }                    
                p.upCpuBurst(Utility.time());
                
                /* This loop deals when there is IO burst following the above CPU burst*/
                if(p.getTimeSpec().size() !=0 && p.getTimeSpec().getFirst().getIoTime() != 0){ // if there is an IO burst following CPU burst                     
                    p.setProcessState(3); // waiting state as it enters IO waiting queue.
                    
                    Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p, above);                       
                    d.deviceEnqueu(p);
                }else{
                    if( p.getTimeSpec().size() != 0 && p.getTimeSpec().getFirst().getIoTime() == 0)
                        p.getTimeSpec().removeFirst();
                }   
            }             
        }
    }
}