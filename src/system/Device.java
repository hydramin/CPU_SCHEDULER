package system;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Device implements Runnable{
/* Device is a multiton class Key:Value pair is Device_Num : Device_Instance*/    
    private LinkedBlockingQueue<Process> deviceQueue; // Each device has a queue of its own
    private LinkedBlockingQueue<Process> returnQueue; // Each device has a queue of its own
    private ScheduledExecutorService pay; // Each device is a thread of its own
    private static HashMap<Integer, Device> currentDevices;

/* Has to be a singleton that runns once*/
    private Device(Process pr){             
        // this.deviceEnqueu(pr);
        deviceQueue.offer(pr);       
        currentDevices = new HashMap<>(); 
        timeThread();
    }

    public static Device getDevice(int n, Process pr){
        if (!currentDevices.containsKey(n))
            currentDevices.put(n, new Device(pr));
        return currentDevices.get(n);
    }

    /**
     * @param returnQueue the returnQueue to set
     */
    public void setReturnQueue(LinkedBlockingQueue<Process> returnQueue) {
        if(returnQueue == null)
            this.returnQueue = returnQueue;
    }

    // public void deviceEnqueu(Process p){        
    //     deviceQueue.offer(p);
    // }

    private void ioService(){ // this service runs the ioPrint of the process
        Process p = deviceQueue.element();
        for(int i = 0;i < p.getTimeSpec().getFirst().getIoTime();i++){
            p.ioRun(d);
            // sleep();
        }
        p.getTimeSpec().removeFirst();
        returnQueue.offer(p);
        deviceQueue.remove(p);

        if(deviceQueue.isEmpty())
            this.pay.shutdown();
    }

    @Override
    public void run() {        
        ioService();        
        // System.out.println("<><><><><><><><> CURRENT QUEUE "+this.queue);
    }

    public void sleep(){
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void timeThread() {    
        try{
            System.out.println("Class thread called.>>>>>>>>");
            this.pay = Executors.newSingleThreadScheduledExecutor();
            this.pay.scheduleAtFixedRate(this, 5, 6, TimeUnit.SECONDS);                                                                    
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}