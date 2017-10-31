package system;

import java.util.ArrayList;

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
    private final int maxPrint = 15;
    private final BurstSpec timeSpec[] = {
        new BurstSpec(12,4,0),
        new BurstSpec(7,3,1),
        new BurstSpec(4,2,2)
    };

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
    public BurstSpec[] getTimeSpec() {
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

    public ArrayList<Integer> getProcessSite(){
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

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATION
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    public void fillArray(){
        this.processSite.add((char) c++);
    }

    public void run(){
        print();
        upNumOfPrint();
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
        System.out.printf("%c ",c);
    }

    @Override
    public String toString() {
        return "current char -> "+ c;
    }

    /*a private class to hold the CPU, I/O burst and device instructions for the Process*/
    private class BurstSpec{
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