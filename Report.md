
Modeling of a Deterministic CPU schedueling algorithm.

- A Deterministic CPU .... is this and that. ..

FCFS
    - If the a process in a FIFO schedule has both a CPU burst and IO burst times then when the IO burst time for this particular process takes place, the CPU would stay idle until this same process is returned back to it. If the IO burst for this process is 6 seconds the CPU will lose 6 valuable seconds of processing. To Make the FIFO efficient, the second process following the current one will access the CPU when the first process goes into IO. When the first goes into IO device queue, the second will take over CPU and take time until it completes all processing or until it leaves CPU and goes into IO queue. 