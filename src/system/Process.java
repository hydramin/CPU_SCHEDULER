package system;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class Process{
    private long arrivalTime; // the time the process requests CPU/ readyqueue entry
                            // the arrivalTime has to be set by the Queue 
    private int cpuBurst; // time being processed on the cpu, 
                        // it starts from zero and any time the run is called it is incremented by one 
    private int ioBurst; // time the program waits for io
                        // at random it 
    private int processPriority; // 1 highest 10 lowest, for priority schedueling
    private int processState; // 0-new, 1-running, 2-ready, 3-io wait, 4-terminated

    private boolean run;
    private ArrayList<Character> processSite;
    public char c;
    private int numOfprint;        
    private final BurstSpec spec[] = {
        new BurstSpec(12,4,0),
        new BurstSpec(7,3,1),
        new BurstSpec(4,2,2)
    };
    private LinkedList<BurstSpec> timeSpec = new LinkedList<>(Arrays.asList(spec));
    private int maxPrint = 15;

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> CONSTRUCTOR
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public Process(){
        arrivalTime = 0;
        cpuBurst = 0;
        ioBurst = 0;
        processPriority = 0;
        processState = 0;
        run = true;
        c = 'A';
        numOfprint = 0;
        processSite = new ArrayList<>();
        setMaxPrint();

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

    /**
     * @return the c
     */
    public char getC() {
        return c;
    }

    /**
     * @return the timeSpec
     */
    public LinkedList<BurstSpec> getTimeSpec() {
        return timeSpec;
    }

    /**
     * @return the numOfprint
     */
    public int getNumOfprint() {
        return numOfprint;
    }

    /**
     * @return the maxPrint
     */
    public int getMaxPrint() {
        return maxPrint;
    }

    public ArrayList<Character> getProcessSite(){
        return this.processSite;
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> SETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    public void setArrivalTime(long time){
        this.arrivalTime = time;
    }

    public void setCpuBurst(int burst){
        this.cpuBurst = burst;
    }

    public void setIoBurst(int burst){
        this.ioBurst = burst;
    }

    public void setPriority(int priority){
        this.processPriority = priority;
    }

    public void setProcessState(int state){
        this.processState = state;
    }

    public void setRun(boolean ctrl){
        this.run = ctrl;
    }

    private void upNumOfPrint(){
        this.numOfprint++;
    }

    private void setMaxPrint(){
        
        for(int i = 0;i<this.timeSpec.length;i++){
            this.maxPrint += this.timeSpec[i].getCpuTime();
        }
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATION
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    public void fillArray(){
        System.out.println("ArrayFilled!");
        this.processSite.add(c++);
    }

    public void run(){
        fillArray();
        // print();
        upNumOfPrint();
        sleep();
    }

    public void ioRun(int d){
        System.out.println("I/O wait active! Decive-"+d);
        sleep();
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
        return "current char -> "+ c;
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
    }
}

/*io burst for the process
*/