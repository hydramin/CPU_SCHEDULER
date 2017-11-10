package system;

import java.util.concurrent.LinkedBlockingQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Multilevel{

    private static LinkedBlockingQueue<Process> rrQueue0;    // ready queue for foreground processes
    private static LinkedBlockingQueue<Process> fcfsQueue;    // ready queue for background processes
    private ArrayList<Process> copyList;     

    public Multilevel(LinkedBlockingQueue<Process> rrQueue, LinkedBlockingQueue<Process> fcfsQueue){
        Multilevel.rrQueue0 = rrQueue;        
        Multilevel.fcfsQueue = fcfsQueue;
        copyList = new ArrayList<Process>();
    }

    public static LinkedBlockingQueue<Process> getRRQueue(){
        return Multilevel.rrQueue0;
    }

    public static LinkedBlockingQueue<Process> getFCFSQueue(){
        return Multilevel.fcfsQueue;
    }

    public void makeCopy(Process p){        
        if(!copyList.contains(p))
            copyList.add(p);
    }

    int i = 0;
    /**
     * Method always gives first priority to the forground ready queue and then to the background ready queue
     * This method runs O(n^2)
    */
    public void cpuProcess(){
        boolean swich = false;
        while(true){
            if(!rrQueue0.isEmpty()){
                swich = true;
                this.cpuProccessRR(swich, rrQueue0, 6);
                swich = false;    
                i=0;        
            }else if(!fcfsQueue.isEmpty()) {
                swich = true;
                this.cpuProccessFC(swich, fcfsQueue);
                swich = false;
                i=0;
            }else{
                // loop exit
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
     * Multilevel scheduler. It runs O(n): the while loop only runs O(1) per value given the inner loop runs O(n)
     * @pre rrQueue must not be empty and runLimit must not be zero and swich must be true
     * @post it simulates a round robin scheduler based on the input list and the time slice specified
     * @param swich is used to run the method a single time, must be true upon entrance
     * @param list is the ready foreground ready queue
     * @param runLimit is the time slice specification
    */
    public void cpuProccessRR(boolean swich, LinkedBlockingQueue<Process> list, int runLimit){        
        Process p;
        while(swich){
            swich = false;
            if(!list.isEmpty()){                
                System.out.println("ForeGround Processes Active.");
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
                    list.offer(p); 
                }                  
                /* This loop deals when there is IO burst following the above CPU burst*/
                if(p.getTimeSpec().getFirst().getIoTime() != 0 && p.getTimeSpec().getFirst().getCpuTime() == 0){
                    Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p, list);                        
                    d.deviceEnqueu(p);
                }
            }  
        }
    }

      /**
     * This method is a copy of the FCFS cpu, it is used to simulate a FCFS scheduler for the 
     * Multilevel scheduler. It runs O(n)
     * @pre rrQueue must not be empty and runLimit must not be zero and swich must be true
     * @post it simulates a FCFS scheduler based on the input list 
     * @param swich is used to run the method a single time, must be true upon entrance
     * @param list is the ready background ready queue     
    */
    public void cpuProccessFC(boolean swich, LinkedBlockingQueue<Process> list){        
        Process p;
        while(swich){
            swich = false;
            if(list.size()!=0){
                System.out.println("Background Processes Running!");
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
                    
                    Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p, list);                       
                    d.deviceEnqueu(p);
                }else{
                    if( p.getTimeSpec().size() != 0 && p.getTimeSpec().getFirst().getIoTime() == 0)
                        p.getTimeSpec().removeFirst();
                }   
            }               
        }
    }
}