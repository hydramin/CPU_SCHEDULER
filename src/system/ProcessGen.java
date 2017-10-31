package system;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProcessGen implements Runnable{
/* Genetates a random Process with random attributes*/
    private Process p;
    private static ProcessGen pr;
    private LinkedBlockingQueue<Process> queue;
    private static ScheduledExecutorService pay;

/* Has to be a singleton that runns once*/
    private ProcessGen(LinkedBlockingQueue<Process> queue){
        super();
        this.queue = queue;        
        timeThread();
    }

    public static void generate(LinkedBlockingQueue<Process> queue){
        if(pr == null)
            pr = new ProcessGen(queue);        
    }

    public void processEnqueu(){
        // System.out.println("<><><><>ENQUEUE<><><><>");
        p = new Process('U');  
        if(!queue.isEmpty())
            queue.offer(p);    
        else{                
            ProcessGen.pay.shutdown();
        }
    }

    public void processEnqueu(char ch){
        // System.out.println("<><><><>ENQUEUE<><><><>");
        p = new Process(ch);  
        if(!queue.isEmpty())
            queue.offer(p);    
        else{                
            ProcessGen.pay.shutdown();
        }
    }

    @Override
    public void run() {        
        processEnqueu();
        // System.out.println("<><><><><><><><> CURRENT QUEUE "+this.queue);
    }

    void timeThread() {    
        try{
            System.out.println("Class thread called.>>>>>>>>");
            ProcessGen.pay = Executors.newSingleThreadScheduledExecutor();
            ProcessGen.pay.scheduleAtFixedRate(this, 10, 20, TimeUnit.SECONDS);                                                                    
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}