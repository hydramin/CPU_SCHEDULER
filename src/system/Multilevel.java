package system;

import java.util.concurrent.LinkedBlockingQueue;

public class Multilevel{

    private static LinkedBlockingQueue<Process> rrQueue;
    private static LinkedBlockingQueue<Process> fcfsQueue;
    private RR roundRobin;
    private FCFS fcfServe;

    public Multilevel(LinkedBlockingQueue<Process> rrQueue, LinkedBlockingQueue<Process> fcfsQueue){
        Multilevel.rrQueue = rrQueue;
        Multilevel.fcfsQueue = fcfsQueue;
        roundRobin = new RR();
        fcfServe = new FCFS();        
    }

    public void cpuProcess(){
        roundRobin.setList(Multilevel.rrQueue);
        roundRobin.cpuProccess();        
       
        fcfServe.setList(Multilevel.fcfsQueue);
        fcfServe.cpuProccess();
    }

    /**
     * @return the rrQueue
     */
    public static LinkedBlockingQueue<Process> getRrQueue() {
        return Multilevel.rrQueue;
    }

    /**
     * @return the fcfsQueue
     */
    public static LinkedBlockingQueue<Process> getFcfsQueue() {
        return Multilevel.fcfsQueue;
    }
}