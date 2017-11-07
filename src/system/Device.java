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
    // private ScheduledExecutorService pay; // Each device is a thread of its own
    private static HashMap<Integer, Device> currentDevices = new HashMap<>();
    ExecutorService executorService = Executors.newFixedThreadPool(1);

/* Has to be a singleton that runns once*/
    private Device(Process pr/*, LinkedBlockingQueue<Process> returnQueue*/){             
        // this.deviceEnqueu(pr);
        // System.out.println("DEVICE CREATED!");
        deviceQueue = new LinkedBlockingQueue<>();
        // Device.returnQueue = returnQueue;
        deviceQueue.offer(pr);       
        // currentDevices = new HashMap<>(); 
        timeThread();
    }

    // public static HashMap<Integer, Device> getCurrentDevices() {
    //     return Device.currentDevices;
    // }

    public ExecutorService getExecutor(){
        return this.executorService;
    }

    public static Device getDevice(int n, Process pr, LinkedBlockingQueue<Process> returnQueue){
        // System.out.println(currentDevices.containsKey(n));
        if (!currentDevices.containsKey(n)){
            currentDevices.put(n, new Device(pr));
        }        
        Device.returnQueue = returnQueue;
        return currentDevices.get(n);
    }
   

    
    public LinkedBlockingQueue<Process> getReturnQueue(){
        return Device.returnQueue;
    }
    
    public void deviceEnqueu(Process p){   
        if(!deviceQueue.contains(p))     
            deviceQueue.offer(p);        
    }

    private void ioService(){ // this service runs the ioPrint of the process
        
        Process p = deviceQueue.element();
        p.upIoBurst(-Utility.time());
        for(int i = 0;i < p.getTimeSpec().getFirst().getIoTime();i++){
            p.ioRun(p.getTimeSpec().getFirst().getDevice());            
            Utility.sleep();
        }
        p.upIoBurst(Utility.time());

        p.getTimeSpec().removeFirst();                
        
        if(!p.getTimeSpec().isEmpty()){
            p.setProcessState(1); // ready state when it goes back in ready queue
            Device.returnQueue.offer(p);
        }else{
            
            p.setTerminationTime(Utility.time());
        }
            
        deviceQueue.remove(p);

        if(deviceQueue.isEmpty()){}
            // this.executorService.shutdown();
    }

    @Override
    public void run() {          
        while(true){                
            if(this.deviceQueue.size()!=0){  
                ioService();
            }
        }        
        // System.out.println("<><><><><><><><> CURRENT QUEUE "+this.queue);
    }

    private void timeThread() {    
        try{
            System.out.println("Device thread called.>>>>>>>>");                                                                                
            executorService.execute(this); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}