package system;


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Main{

<<<<<<< HEAD
    public static void main(String[] args) {
        ArrayList<PCB> t = Main.create();
        Iterator<PCB> it = t.iterator();
//        CPU.getInstance();
//        CPU.setCurrent(t);
        int i = 0;
        while (it.hasNext()){
            i++;
            PCB p = it.next();
            p.run();
            if(CPU.time()%2 == 0){

                /*try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }
        System.out.println("Ending here!");
=======
    public static void main(String[] args) {        
        LinkedBlockingQueue<Process> list = new LinkedBlockingQueue<>();
        // for(int i=65;i<65+2;i++){
        //     Process p = new Process();
        //     list.offer(p);
        // }
        System.out.println("hi there");
        FCFS f = new FCFS();
        // RR f = new RR();
        // ProcessGen.generate(list);
        
        
        System.out.println("RR created");
        f.setList(list);
        ProcessGen.generate(f.getList());
        System.out.println("RR took in list");
        f.numPrint1();
        System.out.println("Die Fool!");
>>>>>>> 9767ef5f16632bb47ce3bc35126c78a050e06f03
    }
}