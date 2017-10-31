package system;

public class Process{
    private long arrivalTime; // the time the process requests CPU/ readyqueue entry
    private int cpuBurst; // time being processed on the cpu
    private int ioBurst; // time the program waits for io
    private int processPriority; // 1 highest 10 lowest, for priority schedueling
    private int processState; // 0-new, 1-running, 2-ready, 3-io wait, 4-terminated

    private boolean run;
    public char c;
    private int numOfprint;

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> CONSTRUCTOR
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    public Process(char a, int k){
        arrivalTime = k;
        cpuBurst = 0;
        ioBurst = 0;
        processPriority = 0;
        processState = 0;
        run = true;
        c = a;
        numOfprint = 0;
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> GETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    
    public long getArrivalTime(){
        return this.arrivalTime;
    }

    public int getCpuBurst(){
        return this.cpuBurst;
    }

    public int getIoBurst(){
        return this.ioBurst;
    }

    public int getProcessPriority(){
        return this.processPriority;
    }

    public int getProcessState(){
        return this.processState;
    }

    public char getC(){
        return this.c;
    }

    /**
     * @return the numOfprint
     */
    public int getNumOfprint() {
        return numOfprint;
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

    public void count(){
        for(int i=0;i<3;i++){            
            print(i);
            upNumOfPrint();
            sleep();
        }
    }

    public void sleep(){
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void print(int a){
        System.out.printf("%c ",c);
    }
    
    @Override
    public String toString() {
        return String.format("%c",this.c);
    }
}