package system;

public class FirstComeFirstServe {
    private ReadyQueue<PCB> queue;
    private PCB currentProcess;

    public FirstComeFirstServe(ReadyQueue<PCB> q) {
        this.queue = q;
    }

    public void schedule() {
        while (!queue.isEmpty()) {
            currentProcess = pickNextProcess();
            CPU.run(currentProcess);
        }
    }

    public PCB pickNextProcess() {
        return queue.list.removeFirst();
    }

    public double getAvgTurnaroundTime() {
        return 0;
    }

    public double getAvgWaitTime() {
        return 0;
    }
}
