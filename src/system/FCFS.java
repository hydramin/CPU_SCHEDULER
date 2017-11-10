package system;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class FCFS{
    private static LinkedBlockingQueue<Process> list;  // the ready queue , processes are removed once they are done 
    private ArrayList<Process> copyList;     // a copy of the Processes in the ready queue, for reserve
    
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATION
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    /**
     * Constructor for First-Come-First-Serve scheduler.  
     */
    public FCFS(){
        list = new LinkedBlockingQueue<Process>();
        copyList = new ArrayList<Process>();        
    }
    
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> GETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    /**
     * Getter of the ready queue, runs O(1)
     * @return the ready queue
     */
    public static LinkedBlockingQueue<Process> getList(){
        return FCFS.list;
    }
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> SETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    /**
     * Setter of the ready queue,  runs O(1) 
     * @pre parameter must not be null
     * @post assigns the argument as the ready queue reference
     * @return void
     */
    public void setList(LinkedBlockingQueue<Process> l){        
       FCFS.list = l;              
    }
    
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATIONS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
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



    int i=0; // the shutdown-scheduler wait time counter is started 
    /**
     * Setter of the ready queue, runs O(n^2)
     * @pre ready queue must not be null
     * @post it executes the process based on the CPU/IO specifications in the process
     * @return void
     */
    public void cpuProccess(){        
        Process p;                  // temporary Process, holds the polled first element of the ready queue
        while(true){ // infinite loop of the process
            if(list.size()!=0){ // loop continues until ready queue is empty
                p = list.poll(); // first element of ready queue
                makeCopy(p);    // the reference of the process is saved
                p.setProcessState(2); // process enters state 2 (running) before running
                
                p.upCpuBurst(-Utility.time()); // time before entering the CPU, subtracted
                for(int j = 0; j<p.getTimeSpec().getFirst().getCpuTime();j++){ // get the specified CPU run time and loop that many times
                    p.run(); // call the run() function that fills the ArrayList<Character>
                    Utility.sleep();        // Sleep for a second to slow down the process
                }                    
                p.upCpuBurst(Utility.time());   // time after entering CPU, added; thus gives the change in time
                
                /* Below, is an if statement that checks if IO requirement is specified*/
                if(p.getTimeSpec().size() !=0 && p.getTimeSpec().getFirst().getIoTime() != 0){ // if there is an IO burst following CPU burst
                    // move this process into the Device queue
                    // the device thread runs every second and it holds the Process for the alloted IO time
                    p.setProcessState(3); // waiting state (3 - IO wait) as it enters IO waiting queue.                    
                    // The Device multiton is created with Device number, the Process and the return Ready queue
                    Device d = Device.getDevice(p.getTimeSpec().getFirst().getDevice(), p, FCFS.getList());                                        
                    /* This method is used if the device is already created and the getDevice arguments
                       dont change the current list and process passed*/
                    d.deviceEnqueu(p); 
                }else{ // if divice doesn't need to be used, the current BurstSpec (specification) is removed
                    if( p.getTimeSpec().size() != 0 && p.getTimeSpec().getFirst().getIoTime() == 0){
                        p.getTimeSpec().removeFirst(); // the first CPU/IO specification is removed
                        list.offer(p); // The process is enqueued to the tail of the ready queue
                    }
                }                          
                i = 0; // the shutdown-scheduler wait time counter is reset to 0
            }else{
                i++; // if the ready queue is empty shutdown counter is incremented by 1
                if(i == 10){ // if the counter reaches 10, infinite loop breaks out and ends the scheduler
                    break;
                }            
            }
            Utility.sleep(); // this represents the Context-Switch time, runs O(1)
        }
        Utility.calculate(copyList); // When the FCFS process ends, a summary is printed, runs O(n) 
    }
}

