package system;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Device implements Runnable{
/* Device is a multiton class Key:Value pair is Device_Num : Device_Instance*/    
    private LinkedBlockingQueue<Process> deviceQueue; // Each device has a queue of its own
    private static LinkedBlockingQueue<Process> returnQueue; // Each device has a queue of its own    
    private static HashMap<Integer, Device> currentDevices = new HashMap<>(); // stores all devices instantiated
    ExecutorService executorService = Executors.newFixedThreadPool(1); // thread pool for a Device thread

/* Has to be a singleton that runns once*/
    private Device(Process pr){ 
        deviceQueue = new LinkedBlockingQueue<>();
        deviceQueue.offer(pr);       
        timeThread();
    }

    /**
     * Method instantiates a Device thread if it doesn't exist or returns the existing device based on its id
     * Runs O(1)
     * @pre n, device id must be greater than 0 and pr and returnQueue must not be null
     * @post instantiates new or existing Decive based on the specified device number
    */
    public static Device getDevice(int n, Process pr, LinkedBlockingQueue<Process> returnQueue){        
        if (!currentDevices.containsKey(n)){
            currentDevices.put(n, new Device(pr));
        }        
        Device.returnQueue = returnQueue;
        return currentDevices.get(n);
    }
  
    /**
     * Method adds a process that requests IO to the device queue
     * runs O(1)
     * @pre p must not be null
     * @post enqueues the process in the device queue
     * @param p a process that requests IO, is not null
    */
    public void deviceEnqueu(Process p){   
        if(!deviceQueue.contains(p))     
            deviceQueue.offer(p);        
    }

    /**
     * Method simulates the IO wait time by running a delayed loop for the requied IO time
     * runs O(n)
     * @pre deviceQueue must not be empty
     * @post simulates the device wait time for the Process that requests io, it sets termination time if Process has no more CPU time requirement
    */
    private void ioService(){ // this service runs the ioPrint of the process
        
        Process p = deviceQueue.element(); // take the first Process from the device queue in a FCFS manner
        p.upIoBurst(-Utility.time());      // record the IO burst time here and at the end of loop
        for(int i = 0;i < p.getTimeSpec().getFirst().getIoTime();i++){ // run loop for the IO time requirement
            p.ioRun(p.getTimeSpec().getFirst().getDevice());           // call ioRun(...) function and pass the device number for display purpose
            Utility.sleep(); // delay the loop by a second
        }
        p.upIoBurst(Utility.time()); // register the IO time by adding to the initial time recorded above

        p.getTimeSpec().removeFirst();  // once IO time is done remove the head of the BurstSpec class of the process that had the current IO specification
        
        if(!p.getTimeSpec().isEmpty()){ // if there is CPU time left , send the process back to the ready queue
            p.setProcessState(1); // ready state when it goes back in ready queue
            Device.returnQueue.offer(p);
        }else{
            p.setProcessState(4);
            p.setTerminationTime(Utility.time()); // if process has no more CPU time, assign termination time and dont send it back to the ready queue, it will be terminated
        }
        deviceQueue.remove(p); // remove process fromt the devie queue
    }

    /**
     * This method runs infinitely until system shutdown
    */
    @Override
    public void run() {          
        while(true){                
            if(this.deviceQueue.size()!=0){  
                ioService();
            }
        }                
    }

    /**
     * This method initiates a device thread upon instantiation of the Decive
    */
    private void timeThread() {    
        try{
            System.out.println("Device thread called.>>>>>>>>");                                                                                
            executorService.execute(this); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}