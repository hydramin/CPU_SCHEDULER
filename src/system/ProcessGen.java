package system;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ProcessGen implements Runnable{
/* Genetates a random Process with random attributes*/    
    private static ProcessGen pr;
    // private static LinkedBlockingQueue<Process> queue;
    private static LinkedBlockingQueue<Process> jobQueue;
    private static int numOfProcAdded;
    public static ScheduledExecutorService pay;
    private int processIDs;
    private static ArrayList<LinkedBlockingQueue<Process>> queueList;
    // private ExecutorService executorService = Executors.newFixedThreadPool(1);

/* Has to be a singleton that runs once*/
    private ProcessGen(){             
        ProcessGen.jobQueue  = new LinkedBlockingQueue<Process>();
        processIDs = 0; 
        numOfProcAdded = 0;  
        jobProcessEnqueu(); // there must be atleast one job in the job queue         
        queueList = new ArrayList<>();
        timeThread();
        
    }

    public static void generate(/*LinkedBlockingQueue<Process> queue, LinkedBlockingQueue<Process> queue1*/){
        if(pr == null)
            pr = new ProcessGen(/*queue,queue1*/);        
    } 

    public static void addScheduleQueues(LinkedBlockingQueue<Process> schedule){
        ProcessGen.queueList.add(schedule);
        System.out.println("List added");
    }

    private void multiProcessEnqueu(){
        //random number generator that randomly chooses an index from the queueList        
        int index = (r(ProcessGen.queueList.size()) - 1);
        Process p = ProcessGen.jobQueue.poll();        

        p.setProcessState(1); // ready as it is loaded into the ready queue
        p.setArrivalTime(Utility.time());          
        ProcessGen.queueList.get(index).offer(p);
        numOfProcAdded++;

        if(numOfProcAdded == 2){
            ProcessGen.pay.shutdown();
            off();
            System.out.println("Is now Shut! "+ProcessGen.pay.isTerminated());
        }
    }

    public static void off(){ // for device
        try {            
            ProcessGen.pay.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * @Description: it fills the jobQueue in a FIFO way, it is not the ready queue, 
     * every 10 seconds one is taken and passed into the ready queue by processEnqueu
    */
    private void jobProcessEnqueu(){
        Process p = new Process(++processIDs); 
        
        p.setTimeSpec(r(8),r(6),r(3));
        p.setTimeSpec(r(8),r(6),r(3));
        p.setTimeSpec(r(8),r(6),r(3));

        p.setCreationTime(Utility.time());
        p.setPriority(r(10));        
        p.setProcessState(0); // 0 for new                
        jobQueue.offer(p);                
    }
    
    private int r(int i){
        Random rn = new Random();        
        return (1+ rn.nextInt(i));
    }

    @Override
    public void run() {        
            if(Utility.time() % 2 == 0){
                jobProcessEnqueu();                                
            }
            if(Utility.time() % 4 == 0){                       
                if(!jobQueue.isEmpty())
                    multiProcessEnqueu();                
            }
    }

    void timeThread() {    
        try{
            System.out.println("ProcessGen thread called.>>>>>>>>");
            ProcessGen.pay = Executors.newSingleThreadScheduledExecutor();
            ProcessGen.pay.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS); 
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
}