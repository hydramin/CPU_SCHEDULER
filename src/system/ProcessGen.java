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
    private static LinkedBlockingQueue<Process> queue;
    private static LinkedBlockingQueue<Process> jobQueue;
    private static int numOfProcAdded;
    public static ScheduledExecutorService pay;
    private int processIDs;
    private static ArrayList<LinkedBlockingQueue<Process>> queueList;
    // private ExecutorService executorService = Executors.newFixedThreadPool(1);

/* Has to be a singleton that runns once*/
    private ProcessGen(/*LinkedBlockingQueue<Process> queue, LinkedBlockingQueue<Process> queue1*/){
        // ProcessGen.queue = queue;          
        ProcessGen.jobQueue  = new LinkedBlockingQueue<Process>();
        
        processIDs = 0; 
        numOfProcAdded = 0;  
        jobProcessEnqueu(); // there must be atleast one job in the job queue 
        //<><><><><><>><><><><><><><><><><>
        queueList = new ArrayList<>();
        // addScheduleQueues(queue);
        // if(queue1 != null)
        //     addScheduleQueues(queue1);
        //<><><><><><><><><><><><><><><><><>   
        
    // Process p1 = new Process(1);
    //     p1.setCreationTime(time());
    //     p1.setPriority(3);
    //     p1.setTimeSpec(10,5,0);
    //     p1.setTimeSpec(4,2,1);
    //     p1.setTimeSpec(1,4,2);
    // Process p2 = new Process(2);
    //     p2.setCreationTime(time()+5);
    //     p2.setPriority(2);
    //     p2.setTimeSpec(7,5,0);
    //     p2.setTimeSpec(5,2,1);
    //     p2.setTimeSpec(3,4,2);
    // Process p3 = new Process(3);
    //     p3.setCreationTime(time()+10);
    //     p3.setPriority(1);
    //     p3.setTimeSpec(9,5,0);
    //     p3.setTimeSpec(1,2,1);
    //     p3.setTimeSpec(2,4,2);
    // ProcessGen.jobQueue.offer(p1);
    // ProcessGen.jobQueue.offer(p2);
    // ProcessGen.jobQueue.offer(p3);
        // System.out.println("<<==>>==>><<==>> \n"+ProcessGen.jobQueue);
    timeThread();
        
    }

    // private ProcessGen(LinkedBlockingQueue<Process> queue){        
    //         this(queue,null);
    // }

    public static void generate(/*LinkedBlockingQueue<Process> queue, LinkedBlockingQueue<Process> queue1*/){
        if(pr == null)
            pr = new ProcessGen(/*queue,queue1*/);        
    }

    // public static void generate(/*LinkedBlockingQueue<Process> queue*/){
    //     if(pr == null)
    //         pr = new ProcessGen(/*queue*/);                
    // }    

    public static void addScheduleQueues(LinkedBlockingQueue<Process> schedule){
        ProcessGen.queueList.add(schedule);
        System.out.println("List added");
    }

    private void multiProcessEnqueu(){
        //random number generator that randomly chooses an index from the queueList
        
        int index = (r(ProcessGen.queueList.size()) - 1);
        System.out.println("<><><><><><><>Index<><><> "+index);      

        Process p = ProcessGen.jobQueue.poll();        

        p.setProcessState(1); // ready as it is loaded into the ready queue
        p.setArrivalTime(Utility.time());  
        // System.out.println(p.toString());      
        // System.out.println("<<==>> MultiEnqueue List 2 <<==>> "+queueList.get(1));
        // System.out.println("<<==>> MultiEnqueue List 1 <<==>> "+queueList.get(0));
        ProcessGen.queueList.get(index).offer(p);
        numOfProcAdded++;

        if(numOfProcAdded == 10){
            ProcessGen.pay.shutdown();
            off();
            System.out.println("Is now Shut! "+ProcessGen.pay.isTerminated());
        }
    }

    public static void off(){ // for device
        try {            
            ProcessGen.pay.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * @Description: it fills the jobQueue in a FIFO way, it is not the ready queue, 
     * every 10 seconds one is taken and passed into the ready queue by processEnqueu
    */
    private void jobProcessEnqueu(){
        // System.out.println("<><><><>ENQUEUE<><><><>");
        
        Process p = new Process(++processIDs); 
        
        p.setTimeSpec(r(5),r(4),r(1));
        p.setTimeSpec(r(8),r(5),r(1));
        p.setTimeSpec(r(6),r(6),r(1));

        p.setCreationTime(Utility.time());
        p.setPriority(r(10));        
        p.setProcessState(0); // 0 for new                
        jobQueue.offer(p);                
    }


    /**
     * @Description: it takes the first process from the jobQueue and passes it into the FIFO ready queue
    */
    /*private void processEnqueu(){ // transfer from the job queue to the ready queue
        if(numOfProcAdded == 3)
            ProcessGen.pay.shutdown();
        Process p = ProcessGen.jobQueue.poll();        
        p.setProcessState(1); // ready as it is loaded into the ready queue
        p.setArrivalTime(Utility.time());
        ProcessGen.queue.offer(p);
        numOfProcAdded++;
        
    }*/

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
    
    private int r(int i){
        Random rn = new Random();        
        return (1+ rn.nextInt(i));
    }

    // private static long time() {
	// 	long timeMillis = System.currentTimeMillis();
	// 	long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
	// 	return timeSeconds;
	// }

    @Override
    public void run() {
        // while(true){  
            if(Utility.time() % 1 == 0){
                jobProcessEnqueu();                
                // System.out.println("<<-->>\n"+jobQueue);
            }

            if(Utility.time() % 4 == 0){      
                // processEnqueu();
                System.out.println("<><><>Job Queue Stat: "+jobQueue.isEmpty());   
                if(!jobQueue.isEmpty())
                    multiProcessEnqueu();
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