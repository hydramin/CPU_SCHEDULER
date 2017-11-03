
Modeling of a Deterministic CPU schedueling algorithm.

- A Deterministic CPU .... is this and that. ..

Simulating a CPU schedueling service is not an easy task. In this assignment we attempt to build a dynamic CPU scheduling sysytem. It is dynamic in that the program has no hard-coded processes or all processes are randomly generated just as an actual system generates many diverse processes with varrying amounts of CPU and IO time requirements. 

In this assignment a process is defined as filling an array with 'characters'. A Process class has the method run(); when this method is called once it adds a character to the ArrayList of the process - processSite, i.e. processSite will add one character element to its set.

When a process has finished its temporaty CPU bound process (i.e. the filling of the ArrayList) it is transfered to the an IO queue. The process spends a designated amount of time in the IO queue (i.e. the class Device). The time that the process spends in the IO is specified by the Process.BurstSpec inner class. After it is done with the IO wait time, the process returns back to the ready queue of the current schedueler.

The Process class
    - The process class encapsulates the essential features of a process. Intuitively a process is an activity that takes time from start to finish. As mentioned above an instance of a Process class has list of Process.BurstSpec instances that each specify a CPU burst time, an IO burst time, and the device number associated with the IO burst. For example a a list of Process.BurstSpec can be like this: 

    Exampe 1: pre-determinded CPU and IO burst times for a process

        private final BurstSpec spec[] = {
            new BurstSpec(6,10,0),
            new BurstSpec(7,3,0),
            new BurstSpec(4,2,0)
        };

    The first of the above array of BurstSpec inner classes specifies that when this particular process runs it must have 6 seconds of CPU burst time followed by 10 seconds of IO burst time in device number 0;

    Other attributes of the Process class are:
    arrivalTime: 
        set by the process generator class (ProcessGen) at the time when this process is transfered to the ready queue of a schedueling service.
    cpuBurst:
        is the total amount of time the process spends in the CPU. For example, consider the above pre-determined specification in Example 1; the total CPU burst time will be 6 + 7 + 4 = 17 quanta (time slices). However, in an attempt to make this assignment a dynamic simulator, the CPU and IO burst times are not calculated automatically by the class. The determination of the CPU and IO burst times are discussed later.
    ioBurst:
        is the total amount of time the process spends in the IO device queue. Similar to the calculation of the CPU burst time above, the IO burst time is calculated by adding the IO specification in the list of BurstSpec inner-classes. Taking Example 1, we would have ioBurst = 10 + 3 + 2 = 15.
    processPriority:
        is the priority assigned to a process upon creation. This value is used only in a priority scheduling service. Low values are for higher priorities. 
    processSite:
        is an ArrayList that is filled by one character when the process is executed for one CPU time unit. 
    processID:
        is the ID of a process assigned to it at the time of creation.

Generation of Processes

    - Processes are generated automatically by programs running in the coumputer. At any particular time the schedueling algoritm of the operating system loads a process into the CPU. When the current process requests for an IO activity this process is removed from the CPU and is transfered to the IO queue, then, upon completion of its wait time it is taken back to the ready queue. 

    - To emulate this automatic process, we have used the class ProcessGen. The ProcessGen class is a singleton class that runs its own thread upon instantiation. This class can wait a designated amount of time and it creates a new Process class and enqueues it to a temporary field named 'jobQueue'. Then it transfers the Processes in the jobQueue to the ready queue found in the currently used schedueling service(FCFS, SJF,...). The thread stops after adding a certain number of processes into the ready queue.

FCFS
    - If the a process in a FCFS schedule has both a CPU burst and IO burst times then when the IO burst time for this particular process takes place, the CPU would stay idle until this same process is returned back to it. If the IO burst for this process is 6 seconds the CPU will lose 6 valuable seconds of processing. To Make the FIFO efficient, the second process following the current one will access the CPU when the first process goes into IO. When the first goes into IO device queue, the second will take over CPU and takeover the process until it completes all processing or until it leaves CPU and goes into IO queue. 

    - The implementation of the FCFS scheduler is said to be among the simplest schedueling algorithms. It involves processesing the first element util completion and moving on to the next. Different processes have their own CPU and IO time specifications. So if the first process is on its IO burst, the CPU will be left idle with nothing to process until the currently running process finishes its IO burst and returns to the CPU for further processing. This results in an unefficient usage of the CPU. Therefore, when a currently running process goes into its IO waiting phase the next process will take over and the first process will be processes after the second is done or until it passes into its IO waiting phase. Implementing the FCFS in this way reduces the idling time of the CPU but it doesn't completely prevent idling. 

FCFS implementation
    - The FCFS class contains a readyQueue named 'list'. This readyQueue is a static LinkedBlockingQueue<Process> to prevent the creation of several ready queues; that is, a FCFS scheduler should only contain one ready queue. 

    - The heart of the FCFS is a method called the cpuProcess(). This method controls what gets processed, the number of time-slices(quanta) the process takes and, when required, passing the process into the IO queue into a particular IO device. 

