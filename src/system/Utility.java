package system ;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.io.IOException;

public class Utility{
    /**
     *The method makes the thread it is called in to delay by a second
     *@pre: none
     *@post: causes a 1 second delay 
     */
    public static void sleep(){
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method gives current system time in seconds
     * @pre none
     * @post gives system time in seconds
     * @return integer value of current system time
     */
    public  static int time() {
		long timeMillis = System.currentTimeMillis();
        long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);                 
		return (int) timeSeconds;
    }

    /**
     * Method calculates Average Turnaround time, Average Waiting time, Average CPU time and Average IO time for the ArrayList of processes,  
     * runs O(n)
     * @pre copyList must not be null
     * @post prints the Average Turnaround time and Average Waiting time, Average CPU time and Average IO time for all processes
     * @param ArrayList of processes saved from a ready queue
     */
    public static void calculate(ArrayList<Process> copyList){
        System.out.println("\n\nSummary of the Scheduler\n");
        for (Process pr: copyList) { // loop through all the processes, runs O(n)           
            System.out.println(pr.data());   // print the final state of the process
        }
        double avgCpuBurst = 0; // variable to hold the average CPU burst 
        double avgIoBurst = 0;  // variable to hold the average IO burst
        double avgWaiting=0;    // variable to hold the average waiting time
        double turnaroundTime=0;// variable to hold the average turnaround time
        Process temp;           
        for(int i = 0; i<copyList.size();i++){ // loop through all processes, O(n)
            temp = copyList.get(i);
            turnaroundTime += (temp.getTerminationTime() - temp.getCreationTime()); // total termination time for all processes
            avgCpuBurst+=(temp.getCpuBurst()); // total CPU burst time
            avgIoBurst+=(temp.getIoBurst());   // total IO burst time
        }
        avgIoBurst = avgIoBurst/copyList.size(); // total divided by total number of processes
        avgCpuBurst = avgCpuBurst/copyList.size();
        turnaroundTime = turnaroundTime/copyList.size();
        avgWaiting = turnaroundTime - avgCpuBurst; // waiting time is total life of the process minus the time it is active in CPU
        System.out.printf("Avg Waiting time: %.2f \nAvg Turnaround time: %.2f \nAvg CPU burst time: %.2f \nAvg IO burst time: %.2f\n",avgWaiting,turnaroundTime, avgCpuBurst,avgIoBurst);
        
        try{
            Utility.saveProcessDetail(copyList); // additional detail of the processes is saved to file output.txt
        }catch(IOException e){
            e.printStackTrace();
        }    
    }

     /**
     *This method creates a text file and writes to it the ProcessRecords in a Process.
     *@pre: copyList arraylist must not be null
     *@post: it saves the detail information to a file for reference
     *@param ArrayList or Processes, copyList     
     * @throws FileNotFoundException
     */
    public static void saveProcessDetail(ArrayList<Process> copyList) throws IOException {

        BufferedWriter bufferedWriter = null;
                
        try {
            String strContent = "";  
            for (Process var : copyList) {
                for (ProcessRecord va : var.getMyRecord()) {
                    strContent += va.toString();                  
                }
            }
            File myFile = new File("output.txt");
            // check if file exist, otherwise create the file before writing
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            Writer writer = new FileWriter(myFile);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(strContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                if(bufferedWriter != null) bufferedWriter.close();
            } catch(Exception ex){
                 
            }
        }       
    }    
}