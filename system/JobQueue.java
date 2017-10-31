package system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class JobQueue implements Queue<PCB>{
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public void enqueue(PCB p) {
    }

    public PCB first() {
        return null;
    }

    public PCB dequeue() {
        return  null;
    }
}
