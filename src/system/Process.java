package system;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class Process{
    private long creationTime; // time the process is created
    private long terminationTime; // termination time for the process    
    private long arrivalTime; // The time when the FCFS      
    private int cpuBurst; // time being processed on the cpu, 
                        // it starts from zero and any time the run is called it is incremented by one 
    private int ioBurst; // time the program waits for io
                        // at random it 
    private int processPriority; // 1 highest 10 lowest, for priority schedueling
    private int processState; // 0-new, 2-running, 1-ready, 3-io wait, 4-terminated

    private ArrayList<Character> processSite; // one process action is filling the array.
    private char c;
    
    String bSpec;
    private LinkedList<BurstSpec> timeSpec = new LinkedList<>();

    // private int maxPrint = 15;
    private int processID;
    public ArrayList<Long> tester = new ArrayList<>();

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> CONSTRUCTOR
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public Process(){
        creationTime = 0;
        terminationTime = 0;
        arrivalTime = 0;
        cpuBurst = 0;
        ioBurst = 0;
        processPriority = 0;
        processState = 0;
        bSpec="";
        c = 'A';
        
        // numOfprint = 0; 
        processSite = new ArrayList<>();

    }
    public Process(int processID){
        this();
        this.processID = processID; // identifies the partifular process
    }
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> GETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
 

    /**
     * @return the arrivalTime
     */
    public long getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @return the cpuBurst
     */
    public int getCpuBurst() {
        return cpuBurst;
    }

    /**
     * @return the ioBurst
     */
    public int getIoBurst() {
        return ioBurst;
    }

    /**
     * @return the processPriority
     */
    public int getProcessPriority() {
        return processPriority;
    }

    /**
     * @return the processState
     */
    public int getProcessState() {
        return processState;
    }

    // /**
    //  * @return the c
    //  */
    // public char getC() {
    //     return c;
    // }

    /**
     * @return the timeSpec
     */
    public LinkedList<BurstSpec> getTimeSpec() {
        return timeSpec;
    }

    // /**
    //  * @return the numOfprint
    //  */
    // public int getNumOfprint() {
    //     return numOfprint;
    // }

    // /**
    //  * @return the maxPrint
    //  */
    // public int getMaxPrint() {
    //     return maxPrint;
    // }

    public ArrayList<Character> getProcessSite(){
        return this.processSite;
    }

    public long getCreationTime(){
        return this.creationTime;
    }

    public long getTerminationTime(){
        return this.terminationTime;
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> SETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    public void setCreationTime(long time){
        this.creationTime = time;
    }

    public void setArrivalTime(long time){ // getting in the FCFS
        this.arrivalTime = time;
    }

    public void setTerminationTime(long time){ // getting in the FCFS
        this.terminationTime = time;
        System.out.println("Termination Time Set: "+ time);
    }

    public void upCpuBurst(long time){
        this.cpuBurst+= time;        
    }

    public void upIoBurst(long time){
        this.ioBurst+=time;
        tester.add(time);
    }

    public void setPriority(int priority){
        this.processPriority = priority;
    }

    public void setProcessState(int state){
        this.processState = state;
    }

    public void setTimeSpec(int cpu, int io, int device){   
        BurstSpec b = new BurstSpec(cpu,io,device);     
        this.timeSpec.add(b);
        bSpec+=b.toString();
    }

    // private void setMaxPrint(){
        
    //     for(int i = 0;i<this.timeSpec.size();i++){
    //         this.maxPrint += this.timeSpec.get(i).getCpuTime();
    //     }
    // }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATION
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    public void fillArray(){
        //System.out.println("ArrayFilled!");
        this.processSite.add(c++);
    }

    public void run(){
        fillArray();
        // print();
        
        // upNumOfPrint();
        // sleep();
    }

    public void ioRun(int d){
        
        System.out.printf("I/O wait active! Decive- %d Process- %d\n",d,processID);
        // upIoBurst();
        // sleep();
    }

    public void sleep(){
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String data(){
        return String.format("Process: %d Creation: %d Arrival: %d,Termination: %d Priority: %d , State: %d, Arr: %s, BS: %s\n",processID, creationTime,arrivalTime,terminationTime,processPriority,processState,this.processSite,bSpec);
    }

    private void print(){
        // System.out.printf("%c ",c);
        // System.out.printf("pID: %d, pStat: %d,   Arr: %s\n",processID, processState, this.processSite);
        // this.toString();
    }

    @Override
    public String toString() {
        return String.format("Process: %d, Creation: %d Arrival: %d,Termination: %d, Priority: %d , State: %d, Arr: %s, BS: %s\n",processID, creationTime,arrivalTime,terminationTime,processPriority,processState,this.processSite,this.timeSpec);
    }

    /*a private class to hold the CPU, I/O burst and 
    device instructions for the Process*/
    public class BurstSpec {
        int cpuTime;
        int ioTime;
        int device;
        String k;

        public BurstSpec(int c, int i, int d){
            this.cpuTime = c;
            this.ioTime = i;
            this.device = d;
            k = String.format(" CP: %d, IO: %d, DN: %d --",cpuTime, ioTime, device);
        }

        /**
         * @return the cpuTime
         */
        public int getCpuTime() {
            return cpuTime;
        }

        /**
         * @return the device
         */
        public int getDevice() {
            return device;
        }

        /**
         * @return the ioTime
         */
        public int getIoTime() {
            return ioTime;
        }

        public void downCpuTime(){
            --this.cpuTime;
        }

        @Override
        public String toString() {
            //return String.format("CP: %d, IO: %d, DN: %d",cpuTime, ioTime, device);
            return k;
        }
    }
}

/*io burst for the process
*/