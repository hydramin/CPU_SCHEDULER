package system;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class RR{

    private LinkedBlockingQueue<Process> list;   
    private int runLimit;
    
    public RR(){
        runLimit = 4;
    }

    public void setList(LinkedBlockingQueue<Process> l){
       list = l;       
    }

    public LinkedBlockingQueue<Process> getList(){
        return this.list;
    }

    public void numPrint(){
        // Iterator<Process> it = list.iterator();
        Process p;
        while(true){
            if(list.size()!=0){
                p = list.poll();
                for(int i=0;i<runLimit;i++){
                    if(p.getNumOfprint() == p.getMaxPrint()){                        
                        break;
                    }else{
                        p.run();                    
                    }
                 }
                 if(p.getNumOfprint() != p.getMaxPrint()){                        
                    list.offer(p);
                 }                           
                System.out.println("");
            }else{
                // System.exit(0);
                break;                
            }

            try{
            Thread.sleep(500L);
            }catch (InterruptedException e){
                System.out.println(e);
            }
        }
    }

    @Override
    public String toString() {
        return list.element().toString();
    }
}