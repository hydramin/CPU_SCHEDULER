# CPUSchedulingAlgorithms
A system for deterministic modelling of CPU scheduling algorithms

========================================================
Snippets from the Required Reading:
Operating System Concepts: CPU Scheduling.
========================================================
6.1.3 Preemptive Scheduling 
    CPU-scheduling decisions may take place under the following four circumstances: 
        non-Preemptive scheduling: cooperative.
            ==> 1. When a process switches from the running state to the waiting state (for example, as the result of an I/O request or an invocation of wait() for the termination of a child process) 264 Chapter 6 CPU Scheduling 
            ==> 4. When a process terminates For situations 1 and 4, there is no choice
        Preemptive scheduling:
            ==> 2. When a process switches from the running state to the ready state (for example, when an interrupt occurs) 
            ==> 3. When a process switches from the waiting state to the ready state (for example, at completion of I/O) 
       

==>Note that the ready queue is not necessarily a first-in, first-out (FIFO) queue. 
==>As we shall see when we consider the various scheduling algorithms, a ready queue can be implemented as a 
    FIFO queue, 
    Priority queue, 
    Tree, or simply an 
    Unordered Linked list. 
Conceptually, however, all the processes in the ready queue are lined up waiting for a chance to run on the CPU. The records in the queues are generally Process Control Blocks (PCBs) of the processes.

6.2 Scheduling Criteria

criteria have been suggested for comparing CPU-scheduling algorithms

CPU utilization. 
    We want to keep the CPU as busy as possible. Conceptually, CPU utilization can range from 0 to 100 percent. In a real system, it should range from 40 percent (for a lightly loaded system) to 90 percent (for a heavily loaded system).
Throughput. 
    If the CPU is busy executing processes, then work is being done. 
    ==>One measure of work is the number of processes that are completed per time unit, called throughput. 
    ==>For long processes, this rate may be one process per hour; for short transactions, it may be ten processes per second.
Turnaround time. 
    ==>From the point of view of a particular process, the important criterion is how long it takes to execute that process. 
    The interval from the time of submission of a process to the time of completion is the turnaround time. 
    ==>Turnaround time is the sum of the periods spent waiting to get into memory, waiting in the ready queue, executing on the CPU, and doing I/O.
Waiting time. 
    The CPU-scheduling algorithm does not affect the amount of time during which a process executes or does I/O. 
    ==>It affects only the amount of time that a process spends waiting in the ready queue.Waiting time is the sum of the periods spent waiting in the ready queue.
Response time. 
    In an interactive system, turnaround time may not be the best criterion. Often, a process can produce some output fairly early and can continue computing new results while previous results are being output to the user. 
    ==>Thus, another measure is the time from the submission of a request until the first response is produced. 
    ==>This measure, called response time, is the time it takes to start responding, not the time it takes to output the response. The turnaround time is generally limited by the speed of the output device.

6.3.1 First-Come, First-Served Scheduling
    ==> The implementation of the FCFS policy is
easily managed with a FIFO queue.
    ==> Note also that the FCFS scheduling algorithm is nonpreemptive. Once the CPU has been allocated to a process, that process keeps the CPU until it releases the CPU, either by terminating or by requesting I/O.
    ==> When a process enters the ready queue, its PCB is linked onto the tail of the queue. When the CPU is free, it is allocated to the process at the head of the queue. The running process is then removed from the queue.
    
    ==> The FCFS algorithm is thus particularly troublesome for time-sharing systems, where it is important that each user get a share of the CPU at regular intervals.

6.3.2 Shortest-Job-First Scheduling 

    ==> A different approach to CPU scheduling is the shortest-job-first (SJF) scheduling algorithm. 
    
    ==> This algorithm associates with each process the length of the process’s next CPU burst. When the CPU is available, it is assigned to the process that has the smallest next CPU burst. 
    If the next CPU bursts of two processes are the same, FCFS scheduling is used to break the tie. 
    
    Note that a more appropriate term for this scheduling method would be the shortest-next- CPU-burst algorithm,because scheduling depends on the length of the next CPU burst of a process, rather than its total length.

    Although the SJF algorithm is optimal, it cannot be implemented at the level of short-term CPU scheduling. With short-term scheduling, there is no way to know the length of the next CPU burst.
        One approach to this problem is to try to approximate SJF scheduling. We may not know the length of the next CPU burst, but we may be able to predict its value.

        The next CPU burst is generally predicted as an exponential average of the measured lengths of previous CPU bursts. LOOK AT FORMULA PG 9

        The SJF algorithm can be either preemptive or nonpreemptive. The choice arises when a new process arrives at the ready queue while a previous process is still executing.

6.3.3 Priority Scheduling 
    The SJF algorithm is a special case of the general priority-scheduling algorithm. A priority is associated with each process, and the CPU is allocated to the process with the highest priority. 
    
    Equal-priority processes are scheduled in FCFS order.
    
    An SJF algorithm is simply a priority algorithm where the priority (p) is the inverse of the (predicted) next CPU burst.     
        The larger the CPU burst, the lower the priority, and vice versa. 
    
    Note that we discuss scheduling in terms of high priority and low priority. Priorities are generally indicated by some fixed range of numbers, such as 0 to 7 or 0 to 4,095. However, there is no general agreement on whether 0 is the highest or lowest priority. 
    
    ==> In this text, we assume that low numbers represent high priority.

    Priorities can be defined either internally or externally. 
        Internally defined priorities use some measurable quantity or quantities to compute the priority of a process. For example, 
            -time limits, 
            -memory requirements, 
            -the number of open files,
            -the ratio of average I/O burst to average CPU burst 
        External priorities are set by criteria outside the operating system, such as the 
            -importance of the process, 
            -the type and amount of funds being paid for computer use, 
            -the department sponsoring the work, and other, often 
            -political, factors.
    Priority scheduling can be either preemptive or nonpreemptive. 
    
    When a process arrives at the ready queue, its priority is compared with the priority of the currently running process. 
        A preemptive priority scheduling algorithm will preempt the CPU if the priority of the newly arrived process is higher than the priority of the currently running process.

    A major problem with priority scheduling algorithms is indefinite blocking, or starvation. A process that is ready to run but waiting for the CPU can be considered blocked.    
        A priority scheduling algorithm can leave some lowpriority processes waiting indefinitely.
    
    Asolution to the problem of indefinite blockage of low-priority processes is aging. 
        Aging involves gradually increasing the priority of processes that wait in the system for a long time. 
        
        For example, if priorities range from 127 (low) to 0 (high), we could increase the priority of a waiting process by 1 every 15 minutes.

6.3.4 Round-Robin Scheduling 
    The round-robin (RR) scheduling algorithm is designed especially for timesharing systems. 
    
    It is similar to FCFS scheduling, but preemption is added to enable the system to switch between processes. 
    
    A small unit of time, called a time quantum or time slice, is defined. A time quantum is generally from 10 to 100 milliseconds in length. The ready queue is treated as a circular queue. 272 Chapter 6 CPU Scheduling The CPU scheduler goes around the ready queue, allocating the CPU to each process for a time interval of up to 1 time quantum. To implement RR scheduling, we again treat the ready queue as a FIFO queue of processes. New processes are added to the tail of the ready queue. The CPU scheduler picks the first process from the ready queue, sets a timer to interrupt after 1 time quantum, and dispatches the process. One of two things will then happen. The process may have a CPU burst of less than 1 time quantum. In this case, the process itself will release the CPU voluntarily. The scheduler will then proceed to the next process in the ready queue. If the CPU burst of the currently running process is longer than 1 time quantum, the timer will go off and will cause an interrupt to the operating system. A context switch will be executed, and the process will be put at the tail of the ready queue. The CPU scheduler will then select the next process in the ready queue. The average waiting

6.8.1 Deterministic Modeling 
    One major class of evaluation methods is analytic evaluation. 
    
    Analytic evaluation uses the given algorithm and the system workload to produce a formula or number to evaluate the performance of the algorithm for that workload. 
        Deterministic modeling 
            is one type of analytic evaluation. This method takes a particular predetermined workload and defines the performance of each algorithm for that workload.

            It gives us exact numbers, allowing us to compare the algorithms. However, it requires exact numbers for input, and its answers apply only to those cases.