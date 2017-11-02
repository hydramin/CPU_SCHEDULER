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
    // private ExecutorService executorService = Executors.newFixedThreadPool(1);

/* Has to be a singleton that runns once*/
    private ProcessGen(LinkedBlockingQueue<Process> queue){
        ProcessGen.queue = queue;  
        ProcessGen.jobQueue  = new LinkedBlockingQueue<Process>();
        numOfProcAdded = 0;  
        jobProcessEnqueu(); // there must be atleast one job in the job queue   
        timeThread();
    }

    public static void generate(LinkedBlockingQueue<Process> queue){
        if(pr == null)
            pr = new ProcessGen(queue);        
    }

    public void jobProcessEnqueu(){
        // System.out.println("<><><><>ENQUEUE<><><><>");   

        Process p = new Process(randChar());  
        p.setPriority(randPriority());        
        p.setProcessState(0); // 0 for new        
        jobQueue.offer(p);        
    }

    public void processEnqueu(){ // transfer from the job queue to the ready queue
        if(numOfProcAdded == 5)
            ProcessGen.pay.shutdown();
        Process p = ProcessGen.jobQueue.poll();
        p.setArrivalTime(time());
        p.setProcessState(1); // ready as it is loaded into the ready queue
        ProcessGen.queue.offer(p);
        numOfProcAdded++;
        
    }

    private char randChar(){
        Random r = new Random();
        return (char) (65 + r.nextInt(26));
    }
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
            if(time() % 10 == 0){      
                processEnqueu();
                // System.out.println("<><><><><><><><> CURRENT QUEUE "+this.queue);
            }
            if(time() % 5 == 0){
                jobProcessEnqueu();
                System.out.println("<<-->>\n"+jobQueue);
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