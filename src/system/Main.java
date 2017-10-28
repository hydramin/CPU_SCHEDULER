package system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ScheduledExecutorService;

public class Main{

    ScheduledExecutorService p;

    public static ArrayList<PCB> create(){
        ArrayList<PCB> pcbs = new ArrayList<>();
        for(int i=0;i<=5;i++){
            PCB pcb = new PCB(i,i+1);
            pcbs.add(pcb);
        }
        return pcbs;
    }

    public static void main(String[] args) {
        ArrayList<PCB> t = Main.create();
        Iterator<PCB> it = t.iterator();
        CPU.getInstance();
        CPU.setCurrent(t);
//        while (it.hasNext()){
//            it.next().run();
//        }
        System.out.println("Ending here!");
    }
}