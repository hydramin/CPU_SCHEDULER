package system;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ProcessGen implements Runnable{
/* Genetates a random Process with random attributes*/    
    private static ProcessGen pr;
    private static LinkedBlockingQueue<Process> queue;
    private static LinkedBlockingQueue<Process> jobQueue;
    private static int numOfProcAdded;
    private static ScheduledExecutorService pay;
    private int processIDs;
    // private ExecutorService executorService = Executors.newFixedThreadPool(1);

/* Has to be a singleton that runns once*/
    private ProcessGen(LinkedBlockingQueue<Process> queue){
        ProcessGen.queue = queue;  
        ProcessGen.jobQueue  = new LinkedBlockingQueue<Process>();
        processIDs = 0; 
        numOfProcAdded = 1;  
        //jobProcessEnqueu(); // there must be atleast one job in the job queue    
        
    Process p1 = new Process(1);
        p1.setCreationTime(time());
        p1.setTimeSpec(10,5,0);
        p1.setTimeSpec(4,2,0);
        p1.setTimeSpec(1,4,0);
    Process p2 = new Process(2);
        p2.setCreationTime(time()+5);
        p2.setTimeSpec(7,5,0);
        p2.setTimeSpec(5,2,0);
        p2.setTimeSpec(3,4,0);
    Process p3 = new Process(3);
        p3.setCreationTime(time()+10);
        p3.setTimeSpec(9,5,0);
        p3.setTimeSpec(1,2,0);
        p3.setTimeSpec(2,4,0);
    ProcessGen.jobQueue.offer(p1);
    ProcessGen.jobQueue.offer(p2);
    ProcessGen.jobQueue.offer(p3);
        System.out.println("<<==>>==>><<==>> \n"+ProcessGen.jobQueue);
        timeThread();
    }

    public static void generate(LinkedBlockingQueue<Process> queue){
        if(pr == null)
            pr = new ProcessGen(queue);        
    }

    /**
     * @Description: it fills the jobQueue in a FIFO way, it is not the ready queue, 
     * every 10 seconds one is taken and passed into the ready queue by processEnqueu
    */
    public void jobProcessEnqueu(){
        // System.out.println("<><><><>ENQUEUE<><><><>");   

        Process p = new Process(++processIDs);  
        p.setCreationTime(time());
        p.setPriority(randPriority());        
        p.setProcessState(0); // 0 for new        
        jobQueue.offer(p);        
    }

    /**
     * @Description: it takes the first process from the jobQueue and passes it into the FIFO ready queue
    */
    public void processEnqueu(){ // transfer from the job queue to the ready queue
        if(numOfProcAdded == 4)
            ProcessGen.pay.shutdown();
        Process p = ProcessGen.jobQueue.poll();
        p.setArrivalTime(time());
        p.setProcessState(1); // ready as it is loaded into the ready queue
        ProcessGen.queue.offer(p);
        numOfProcAdded++;
        
    }

    /**
     * @Description: it creates a random character as an identity for the process created by jopProcessEnqueue
     * @return: returns a random letter from A to Z
    */

    // private char setID(){
    //     processIDs = null;
    // }

    /**
     * @Description: it generates a random number from 1 to 10 to specify priority of a process
     * @return: returns a random number from 1 to 10
    */
    private int randPriority(){
        Random r = new Random();
        return (1 + r.nextInt(10));
    }

    private static long time() {
		long timeMillis = System.currentTimeMillis();
		long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
		return timeSeconds;
	}

    @Override
    public void run() {
        // while(true){  
            if(time() % 2 == 0){
                // jobProcessEnqueu();
                // System.out.println("<<-->>\n"+jobQueue);
            }

            if(time() % 4 == 0){      
                processEnqueu();
                // System.out.println("<><><><><><><><> CURRENT QUEUE "+this.queue);
            }
            
        // }
    }

    void timeThread() {    
        try{
            System.out.println("ProcessGen thread called.>>>>>>>>");
            ProcessGen.pay = Executors.newSingleThreadScheduledExecutor();
            ProcessGen.pay.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);                                                                    
            // executorService.execute(this); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        
    }
    
}