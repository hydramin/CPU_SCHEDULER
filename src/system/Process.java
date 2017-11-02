package system;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class Process{
    private long arrivalTime; // The time when the FCFS      
    private int cpuBurst; // time being processed on the cpu, 
                        // it starts from zero and any time the run is called it is incremented by one 
    private int ioBurst; // time the program waits for io
                        // at random it 
    private int processPriority; // 1 highest 10 lowest, for priority schedueling
    private int processState; // 0-new, 2-running, 1-ready, 3-io wait, 4-terminated

    private ArrayList<Character> processSite; // one process action is filling the array.
    public char c;
    // private int numOfprint;        
    private final BurstSpec spec[] = {
        new BurstSpec(6,10,0),
        new BurstSpec(7,3,0),
        new BurstSpec(4,2,0)
    };
    private LinkedList<BurstSpec> timeSpec = new LinkedList<>(Arrays.asList(spec));
    // private int maxPrint = 15;
    public char chr;

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> CONSTRUCTOR
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public Process(char chr){
        arrivalTime = 0;
        cpuBurst = 0;
        ioBurst = 0;
        processPriority = 0;
        processState = 0;
        // run = true;
        c = 'A';
        this.chr = chr; // identifies the partifular process
        // numOfprint = 0; 
        processSite = new ArrayList<>();
        // setMaxPrint();

    }
    public Process(){}
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

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> SETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    public void setArrivalTime(long time){ // getting in the FCFS
        this.arrivalTime = time;
    }

    private void upCpuBurst(){
        ++this.cpuBurst;
    }

    private void upIoBurst(){
        ++this.ioBurst;
    }

    public void setPriority(int priority){
        this.processPriority = priority;
    }

    public void setProcessState(int state){
        this.processState = state;
    }

    // private void upNumOfPrint(){
    //     this.numOfprint++;
    // }

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
        upCpuBurst();
        // upNumOfPrint();
        // sleep();
    }

    public void ioRun(int d){
        // System.out.println("I/O wait active! Decive-"+d);
        System.out.printf("I/O wait active! Decive- %d Process- %c\n",d,chr);
        upIoBurst();
        // sleep();
    }

    public void sleep(){
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void print(){
        // System.out.printf("%c ",c);
        System.out.println(this.processSite);
    }

    @Override
    public String toString() {
        return String.format("Name: %c, Arrival: %d, Priority: %d , State: %d\n",chr, arrivalTime,processPriority,processState);
    }

    /*a private class to hold the CPU, I/O burst and 
    device instructions for the Process*/
    public class BurstSpec {
        int cpuTime;
        int ioTime;
        int device;

        public BurstSpec(int c, int i, int d){
            this.cpuTime = c;
            this.ioTime = i;
            this.device = d;
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

        @Override
        public String toString() {
            return String.format("\nCP: %d, IO: %d, DN: %d\n",cpuTime, ioTime, device);
        }
    }
}

/*io burst for the process
*/