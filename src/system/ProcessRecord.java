package system;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;


public class ProcessRecord{
    
	private int pID;       // holds process id
    private int creationTime; // the names are self explanatory
    private int arrivalTime;
    private int currentTime;
    private int terminationTime;
    private int priority;
    private int state;
    private String arrPrinted;
    private String burstSpec; // holds the string value of the burst time specification (BurstSpec)
    
   
    public ProcessRecord(){
        this.pID = 0;    
        this.creationTime = 0;
        this.arrivalTime = 0;
        this.currentTime = 0;
        this.terminationTime = 0;
        this.priority = 0;
        this.state = 0;
        this.arrPrinted = null;
        this.burstSpec = null;       
    }
    
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////

    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////

    /**
     * @param pID the pID to set
     */
    public void setpID(int pID) {
        this.pID = pID;
    }

    /**
     * @param creationTime the creationTime to set
     */
    public void setCreationTime(int creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * @param arrivalTime the arrivalTime to set
     */
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setCurrentTime(int time){
        this.currentTime = time;
    }

    /**
     * @param terminationTime the terminationTime to set
     */
    public void setTerminationTime(int terminationTime) {
        this.terminationTime = terminationTime;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @param arrPrinted the arrPrinted to set
     */
    public void setArrPrinted(String arrPrinted) {
        this.arrPrinted = arrPrinted;
    }

    /**
     * @param burstSpec the burstSpec to set
     */
    public void setBurstSpec(String burstSpec) {
        this.burstSpec = burstSpec;
    }
    //////////////////////////////////////////////////  OPERATIONS  ///////////////////////////////////////////////////
    //////////////////////////////////////////////////  OPERATIONS  ///////////////////////////////////////////////////
    //////////////////////////////////////////////////  OPERATIONS  ///////////////////////////////////////////////////
    //////////////////////////////////////////////////  OPERATIONS  ///////////////////////////////////////////////////
    
    /**
     * Method converts a state integer value to string
     * Runs O(1)
     * @param num must be an integer from 0 to 4 inclusive
    */
    private String stateText(int num){
        String s = "";
        switch(num){
            case 0:
            s = "New";
            break;

            case 1:
            s = "Ready";
            break;

            case 2:
            s = "Running";
            break;

            case 3:
            s = "IO wait";
            break;

            case 4:
            s = "Terminated";
            break;
        }
        return s;
    }



    @Override
    public String toString() {
        return String.format("PID: %d \nCur Time: %d \nCreation: %d \nArrival: %d \nTermination: %d \nPriority: %d \nState: %s \nPrint Activity: %s \nBurst Specifics: %s\n\n", pID,currentTime,creationTime,arrivalTime,terminationTime,priority,this.stateText(state),arrPrinted,burstSpec);
    }
}
