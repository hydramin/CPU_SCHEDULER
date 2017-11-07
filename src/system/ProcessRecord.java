package system;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;


public class ProcessRecord implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pID;    // the social security number of the customer
    private int creationTime;
    private int arrivalTime;
    private int currentTime;
    private int terminationTime;
    private int priority;
    private int state;
    private String arrPrinted;
    private String burstSpec;
    // private int cpuBurstTime;
    // private int ioBurstTime;

    // private ArrayList<AccountActivity> processLog = new ArrayList<>(); // stores every instance info about a single process from start to end. 
    
   
    public ProcessRecord(){
        this.pID = 0;    // the social security number of the customer
        this.creationTime = 0;
        this.arrivalTime = 0;
        this.currentTime = 0;
        this.terminationTime = 0;
        this.priority = 0;
        this.state = 0;
        this.arrPrinted = null;
        this.burstSpec = null;
        // this.cpuBurstTime = 0;
        // this.ioBurstTime = 0;        
    }
    
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////


    // /**
    //  * @Description This method is a getter that returns the account log.
    //  *                           Which contains records of type AccountActivity.
    //  *
    //  * @return an ArrayList of AccountActivities.
    //  */
    // public  ArrayList<AccountActivity> getAccountLog() {
	// 	return processLog;
	// }

    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////

    // /**
    //  * @param processLog the processLog to set
    //  */
    // public void addProcessLog(ArrayList<AccountActivity> processLog) {
    //     this.processLog.add(processLog);
    // }

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

    // /**
    //  * @param cpuBurstTime the cpuBurstTime to set
    //  */
    // public void setCpuBurstTime(int cpuBurstTime) {
    //     this.cpuBurstTime = cpuBurstTime;
    // }

    // /**
    //  * @param ioBurstTime the ioBurstTime to set
    //  */
    // public void setIoBurstTime(int ioBurstTime) {
    //     this.ioBurstTime = ioBurstTime;
    // }
    //////////////////////////////////////////////////  OPERATIONS  ///////////////////////////////////////////////////
    //////////////////////////////////////////////////  OPERATIONS  ///////////////////////////////////////////////////
    //////////////////////////////////////////////////  OPERATIONS  ///////////////////////////////////////////////////
    //////////////////////////////////////////////////  OPERATIONS  ///////////////////////////////////////////////////
    



   

   /* public void sortAccountLog() {
        AccountActivity temp;
        for (int i = 1; i < processLog.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (processLog.get(j).SIN < processLog.get(j-1).SIN) {
                    temp = processLog.get(j);
                    processLog.set(j, processLog.get(j-1));
                    processLog.set(j-1, temp);
                }
                else if (processLog.get(j).SIN == processLog.get(j-1).SIN) {
                    if (processLog.get(j).transactionDate.getTime() < processLog.get(j-1).transactionDate.getTime()) {
                        temp = processLog.get(j);
                        processLog.set(j, processLog.get(j-1));
                        processLog.set(j-1, temp);
                    }
                }
            }
        }
    }
    */

   

    /**
     *@Description This method creates a text file and writes to it the records in account log.
     *@Preconditon: accountLog arraylist must not be null
     *@Postcondition: it saves the object to a file for later use
     * @throws FileNotFoundException
     */
    // public void saveAccountLog() throws IOException {

    // 	FileOutputStream writer = new FileOutputStream("output.ser");
    // 	ObjectOutputStream stream = new ObjectOutputStream(writer);
    // 	stream.writeObject(processLog);
    // 	stream.close();
    // }
    
   

    @Override
    public String toString() {
        return String.format("PID: %d \nCur Time: %d \nCreation: %d \nArrival: %d \nTermination: %d \nPriority: %d \nState: %d \nPrint Activity: %s \nBurst Specifics: %s\n", pID,currentTime,creationTime,arrivalTime,terminationTime,priority,state,arrPrinted,burstSpec);
    }
}
