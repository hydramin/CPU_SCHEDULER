package system;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Arrays;

/**
 * @author Amin Adam 
 * Run times for methods: all methods in this class run O(1)
 */
public class Process{
    private static final int referenceTime = Utility.time(); // a reference time for all other times
    private int creationTime; // time the process is created
    private int terminationTime; // termination time for the process    
    private int arrivalTime; // The time when the FCFS      
    private int cpuBurst; // time being processed on the cpu, 
                        // it starts from zero and any time the run is called it is incremented by one 
    private int ioBurst; // time the program waits for io
                        // at random it 
    private int processPriority; // 1 highest 10 lowest, for priority schedueling
    private int processState; // 0-new, 2-running, 1-ready, 3-io wait, 4-terminated

    private ArrayList<Character> processSite; // one process action is filling the array.
    private char c; // the character printed into the processSite
    
    private String bSpec; // information holder for the process
    private LinkedList<BurstSpec> timeSpec = new LinkedList<>();

    // private int maxPrint = 15;
    private int processID;    
    private ArrayList<ProcessRecord> myRecord;

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> CONSTRUCTOR
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
    /**
     * A constructor that instantiates a proces without any ID number
    */
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
        processSite = new ArrayList<>();        
        this.myRecord = new ArrayList<>();       

    }

    /**
     * A constructor that takes in a process ID and instantiates a Process
     * @param integer value used for identifying a process
    */
    public Process(int processID){
        this();
        this.processID = processID; // identifies the partifular process        
    }
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> GETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
 
    /**
     * A method that returns all the activity records of this Process
     * @return the myRecord ArrayList 
     */
    public ArrayList<ProcessRecord> getMyRecord() {
        return myRecord;
    }

    /** 
     * @return the arrivalTime
     */
    public int getArrivalTime() {        
        return arrivalTime;        
    }

    /**
     * @return the current total CPU burst time for the process
     */
    public int getCpuBurst() {
        return cpuBurst;
    }

    /**
     * @return the the current total IO burst time for the process
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
     * 0-New 1-Ready 2-Running 3-IO wait 4-Terminated
     * @return an integer value of the processState
     */
    public int getProcessState() {
        return processState;
    }

    /**
     * @return the timeSpec, a list of CPU/IO time requirements
     */
    public LinkedList<BurstSpec> getTimeSpec() {
        return timeSpec;
    }

    /**
     * This array list simulates the idea of a process by filling out the array list with characters
     * @return the ArrayList that is filled as a result of a process
    */
    public ArrayList<Character> getProcessSite(){
        return this.processSite;
    }

    /**
     *@return the time the process is created relative to the start time of the software 
     */
    public int getCreationTime(){
        return this.creationTime;
    }

    /**
     * returns the termination time relative to the start time of the software
    */
    public int getTerminationTime(){
        return this.terminationTime;
    }

    /**
     * @return the process ID number
     */
    public int getProcessID() {
        return processID;
    }

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> SETTERS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    /**
     * Sets the creation time of this process, and adjusts it in relation to the software start time or referenceTime
     * @pre time is non negative
     * @post sets the creation time in relation to the referenceTime or software start time
     * @param the system time in seconds 
    */
    public void setCreationTime(int time){
        this.creationTime = time - referenceTime;
        record();
    }

    /**
     * Sets the arrival time in relation to the referenceTime attribute
     * @pre time is non negative
     * @post sets the arrival time in relation to the referenceTime
     * @param the system time in seconds
    */
    public void setArrivalTime(int time){ // getting in the FCFS
        this.arrivalTime = time - referenceTime;
        record();
    }

    /**
     * Sets the termination time in relation to the referenceTime attribute
     * @pre time is non negative
     * @post sets the termination time in relation to the referenceTime
     * @param the system time in seconds
    */
    public void setTerminationTime(int time){ // getting in the FCFS
        this.terminationTime = time - referenceTime;
        record();
    }

    /**
     * It is used to measure the number of seconds that passed during CPU operation
     * @pre time is an integer, negative or positive
     * @post sets the CPU burst time in seconds
     * @param the system time in seconds
    */
    public void upCpuBurst(int time){
        this.cpuBurst+= time;              
    }

    /**
     * It is used to measure the number of seconds that passed during IO wait
     * @pre time is an integer, negative or positive
     * @post sets the IO burst time in seconds
     * @param the system time in seconds
    */
    public void upIoBurst(int time){
        this.ioBurst+= time;        
    }

   /**
     * Sets the priority of the process
     * @pre priority is non negative
     * @post sets the priority of the process
     * @param int value greater than 0
    */
    public void setPriority(int priority){
        this.processPriority = priority;        
    }

    /**
     * Sets the state of the process
     * @pre state is an integer from 0 to 4 inclusive
     * @post sets the state of the process
     * @param int value between 0 and 4 inclusive
    */
    public void setProcessState(int state){
        this.processState = state;        
    }

    /**
     * Sets the CPU/IO requirement times for the process by storing the requirements in the BurstSpec instance
     * @pre cpu, io and device are positive integers
     * @post sets the CPU/IO time requirements and a device number
     * @param cpu value greater than 0
     * @param io value is greater than 0
     * @param device value greater than 0
    */
    public void setTimeSpec(int cpu, int io, int device){   
        BurstSpec b = new BurstSpec(cpu,io,device);     
        this.timeSpec.add(b);
        bSpec+=b.toString();
    }


    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> OPERATION
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    /**
     * Method is used to fill an array to simulate the idea of an actual tangable process
     * @pre c must have an initial character declared
     * @post it adds once character when called
    */
    public void fillArray(){
        System.out.println(String.format("Process %d running.",this.processID));
        this.processSite.add(c++);        
    }

    /**
     * Method is called inside CPU to call fillArray(), though it is redundunt it has a better name
    */
    public void run(){
        fillArray();
        record();
     
    }

    /**
     * Method is called in IO device to inform user that process is in IO
     * @param d is the device number passed from the IO device
    */
    public void ioRun(int d){ 
        System.out.println(String.format("Process %d IO Device %d",this.processID,d));
        record();
    }
    
    /**
     * Method is used to print out summary of the process at any time
    */
    public String data(){
        return String.format("Process: %d Creation: %d Arrival: %d,Termination: %d Priority: %d , State: %d, Arr: %s, BS: %s\n",processID, creationTime,arrivalTime,terminationTime,processPriority,processState,this.processSite,bSpec);
    }

    /**
     * Method is used to store a record of a processes activity detail in the myRecord arraylist
    */
    private void record(){
	    ProcessRecord records = new ProcessRecord();
	    records.setpID(this.getProcessID());
        records.setCurrentTime(Utility.time()-referenceTime);        
        records.setCreationTime(this.getCreationTime());        
        records.setArrivalTime(this.getArrivalTime());
        if(this.getProcessState() == 4)
            records.setTerminationTime(this.getTerminationTime());
        records.setPriority(this.getProcessPriority());
        records.setState(this.getProcessState());
        records.setArrPrinted(this.processSite.toString());        
        records.setBurstSpec(this.timeSpec.toString());
        
        myRecord.add(records);
    }   

    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> BURSTSPEC CLASS
    // <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

    /*a private class to hold the CPU, I/O burst and 
    device instructions for the Process. This inner class encapsulates the 3 related
    values that hold CPU burst time, IO burst time and IO device number*/
    public class BurstSpec {
        int cpuTime; // the specified CPU time
        int ioTime; // the specified IO time that follows a CPU time
        int device; // the specified IO device the process waits in
        String k;  // a sting that stores the details a Burst spec class


        /**
         * Constructor instantiates a BurstSpec (Burst specification) class for a Process
         * @param c must be positive
         * @param i must be positive
         * @param d must be positive
         * @pre all parameters must be positive
         * @post it instantiates a BurstSpec class witha CPU, IO and device requirement. 
        */
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
