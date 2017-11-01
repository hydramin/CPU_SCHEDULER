package system;

public class ReadyQueue<PCB> implements Queue<PCB> {
    // Processes in the Ready state are placed in the ready queue.// CH 2.1
    // The processes that are residing in main memory
    // and are ready and waiting to execute are kept on a list called the ready queue.
    // This queue is generally stored as a linked list.
    // A ready-queue header contains pointers to the first
    // and final PCB s in the list. Each PCB includes a pointer field
    // that points to the next PCB in the ready queue.
    // IT IS A DOUBLY LINKED LIST OF PCBs
    DoublyLinkedList<PCB> list = new DoublyLinkedList<>();

    public ReadyQueue() {}

    @Override
    public int size() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void enqueue(PCB p) {
        list.addLast(p);
    }

    @Override
    public PCB first() {
        return list.first();
    }

    @Override
    public PCB dequeue() {
        return list.removeFirst();
    }
}
