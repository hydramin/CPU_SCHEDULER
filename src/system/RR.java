package system;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class RR{

    private LinkedBlockingQueue<Process> list;    

    public void setList(LinkedBlockingQueue<Process> l){
       list = l;       
    }

    public void numPrint(){
        // Iterator<Process> it = list.iterator();
        while(true){
            if(list.size()!=0){
                list.poll().count();                
                System.out.println("");
            }else{
                System.exit(0);
            }
            

            try{
            Thread.sleep(500L);
            }catch (InterruptedException e){
                System.out.println(e);
            }
        }
    }
}