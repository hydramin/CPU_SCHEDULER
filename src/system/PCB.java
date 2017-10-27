package system;

public class PCB {
    /*
    * 3.1.3 Process Control Block: it is linked to a process as it is in the queue
        * Each process is represented in the operating system by a
        * process control block ( PCB )—also called a task control block.
        * A PCB is shown in Figure 3.3. It contains many pieces of information
        * associated with a specific process
            * Process state: New, Ready, Running, Waiting, Terminated
            * Program counter : indicates the address of the next instruction
                            * to be executed
            * CPU registers : The 32 registers as in EECS-2021
            * CPU-Scheduling info: includes
                * a process priority,
                * pointers to scheduling queues, and
                * any other scheduling parameters. (See Ch 6)
            * Memory-management info
            * Accounting info
                * CPU time used
                * Real time used
                * Time limits
                * account numbers
                * Process numbers
            * I/O status info: list of I/O devices allocated to the process, a list of open files
        *
    * */

    /*The above fields will be the attributes of this class*/

    static int  pid;                // Process identifier
    int arivalTimel;                // Time when process p first makes request for execution from CPU scheduler
    /**
    ProcessState currentState;
     */
    /**
     * Suggestion: we could represent the state as an int;
     *          0 for New, 1 for Ready, 2 for Running, and 3 for Waiting.
     */
    int processState;               // The state of the process. 0 for New, 1 for Ready, 2 for Running, and 3 for Waiting
    int timeSlice;                  // CPU Scheduling info, amt of time given for the process is paused and other information
    int processPriority;            // Non-negative integer representing the priority of the process. lower number = higher priority
    int CPUTime;                    // Non-negative integer representing time needed for process to execute on CPU
    int IOTime;                     // Non-negative integer representing time needed for I/O operation
    int completionTime;             // Time when the process has completed
    int turnAroundTime;             // Total time taken by process between starting state and ending state
    int waitingTime;                // Time for which process is in the ready queue, no yet executing
    PCB nextProcess;                // Reference to the next process in the queue. AKA program counter
}
