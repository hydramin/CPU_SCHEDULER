package system;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CPU implements  Runnable{

    private ScheduledExecutorService execute;
    private static PCB current;
    private static CPU single;
    private static int timeCounter = 0;
    ArrayList<PCB> pcbs;
    public static int time;

    private CPU(){
        timeThread();
        time = 0;
    }
    int i = 0;

    public static void getInstance(){
        if(CPU.single == null){
            single = new CPU();
        }
    }

    @Override
    public void run() {
        time++;
//        System.out.printf("%d ",++i);
        if(time%10 == 0){

        }
        this.current.run();
    }

    public static void setCurrent(PCB current){
//        CPU.current = current;
//        timeCounter = CPU.current.timeReq;

    }

    void timeThread() {
        System.out.println("Class thread called.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        this.execute = Executors.newSingleThreadScheduledExecutor();
        this.execute.scheduleAtFixedRate(this, 0, 300, TimeUnit.MILLISECONDS); // checks
        // every 5
        // seconds
    }


}
