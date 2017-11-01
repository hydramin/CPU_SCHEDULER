package system;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;

public class CPU implements  Runnable{

    private ScheduledExecutorService execute;
    private static PCB current;
    private static CPU single;    
    private static int time;  
    private static FCFS<PCB> schedule; 
    private static Iterator<PCB> it;

    private CPU(){
        timeThread();  
        this.current = null; 
        it = null;                 
    }    

    /**
     * @param schedule the schedulMethod to set
     */
    public static void setSchedule(FCFS<PCB> schedule) {
        CPU.schedule = schedule;
        CPU.it = CPU.schedule.iterator(); 
    }

    public static void getInstance(){
        if(CPU.single == null){
            single = new CPU();
        }
    }
    
    @Override
    public void run() {
        while(true){
            if(CPU.time() % 5==0){
                System.out.println("Hi, Mare!" + schedule.get(1)); // in this instant change reference
                if(!schedule.isEmpty()){
                    System.out.println("Hi, Mare!");
                    // schedule.getFirst().run();
                    // schedule.removeFirst();
                }
                sleep();                
            }
            // if(schedule.isEmpty()){
            //     for (PCB pcb : schedule) {
            //         pcb.run();
            //     }
            //   }
            // while (it.hasNext()){
            //     // it.next().run(); 
            //     System.out.println("Hi, Mare! "+CPU.time());       
            // }
            
        }
    }

    public static void setCurrent(PCB current){

    }

    void timeThread() {
        System.out.println("Class thread called.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        this.execute = Executors.newSingleThreadScheduledExecutor();
        // this.execute.scheduleAtFixedRate(this, 0, 1000, TimeUnit.MILLISECONDS); // checks
        this.execute.execute(this);

    }

    public static  long time() {
		long timeMillis = System.currentTimeMillis();
        long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);        
		return timeSeconds;
    }
    
    private static void sleep(){
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
