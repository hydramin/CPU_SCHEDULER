package system;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// import java.util.concurrent.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main{    
    private static LinkedBlockingQueue<Process> list;
    private static LinkedBlockingQueue<Process> list1;
    private static Scanner choice = new Scanner(System.in);
    // ExecutorService

    private static int boundaryValidator(int from, int to) { // used for the choices
		int x = Main.choice.nextInt();
		while(x <from && x > to){
			System.out.println("Choose from the given choices.");
			x = Main.choice.nextInt();
		}
		return x;
	}

    private static void firstPage(){
        while(true){
        System.out.println("CPU Schedueling Algorithms\n=======================================");
        System.out.println("First of all, my apologies for you have to "
                            + "re-run the program after running each algoritm.\n"
                            + "\n=======================================");
        System.out.println("Please choose an algorithm to run.");
        System.out.println("1. Non-Premptive First Come First Served.");
        System.out.println("2. Nonpreemptive Shortest-Job-First (SJF).");
        System.out.println("3. Preemptive SJF (Shortest-Remaining-Time-First).");
        System.out.println("4. Nonpreemptive Priority Scheduling.");
        System.out.println("5. Preemptive Priority Scheduling");
        System.out.println("6. Preemptive Round-Robin (RR) Scheduling");
        System.out.println("7. Multilevel Queue Scheduling");
        System.out.println("8. Multilevel Feedback Queue Scheduling");

            switch (boundaryValidator(1,8)) {
                case 1: // Non-Premptive First Come First Served.
                    npfcfs();
                    System.exit(0);
                    break;
                case 2: // Nonpreemptive Shortest-Job-First (SJF).
                    npsjf();
                    System.exit(0);
                break;
                case 3: // Preemptive SJF (Shortest-Remaining-Time-First).
                    psjf();
                    System.exit(0);
                break;
                case 4: // Nonpreemptive Priority Scheduling.
                    npp();
                    System.exit(0);
                break;
                case 5: // Preemptive Priority Scheduling.
                    pp();
                    System.exit(0);
                break;
                case 6: // Preemptive Round-Robin (RR) Scheduling
                    prr();
                    System.exit(0);
                break;
                case 7: // Multilevel Queue Scheduling
                    mlq();
                    System.exit(0);
                break;
                case 8:
                    mlfq();
                    System.exit(0);
                break;
            
                default:
                    break;
            }
        }
    }

    private static void npfcfs(){
        list = new LinkedBlockingQueue<>(); // the list to be passed around        

        FCFS f = new FCFS();
        f.setList(list);
        // ProcessGen.generate(FCFS.getList());
        ProcessGen.generate();
        ProcessGen.addScheduleQueues(FCFS.getList());
        f.cpuProccess();
    }

    private static void npsjf(){
        list = new LinkedBlockingQueue<>(); // the list to be passed around        

        NP_SJF f = new NP_SJF();
        f.setList(list);
        // ProcessGen.generate(NP_SJF.getList());
        ProcessGen.generate();
        ProcessGen.addScheduleQueues(NP_SJF.getList());
        f.cpuProccess();
        
    }

    private static void psjf(){
        list = new LinkedBlockingQueue<>(); // the list to be passed around        

        P_SJF f = new P_SJF();
        f.setList(list);
        // ProcessGen.generate(P_SJF.getList());
        ProcessGen.generate();
        ProcessGen.addScheduleQueues(P_SJF.getList());
        f.cpuProccess();
    }

    private static void npp(){
        list = new LinkedBlockingQueue<>(); // the list to be passed around        
        
        NP_Priority f = new NP_Priority();
        f.setList(list);
        // ProcessGen.generate(NP_Priority.getList());
        ProcessGen.generate();
        ProcessGen.addScheduleQueues(NP_Priority.getList());
        f.cpuProccess();
    }

    private static void pp(){
        list = new LinkedBlockingQueue<>(); // the list to be passed around        

        P_Priority f = new P_Priority();
        f.setList(list);
        // ProcessGen.generate(P_Priority.getList());
        ProcessGen.generate();
        ProcessGen.addScheduleQueues(P_Priority.getList());
        f.cpuProccess();
    }

    private static void prr(){
        list = new LinkedBlockingQueue<>(); // the list to be passed around        
        
        RR f = new RR();
        f.setList(list);
        // ProcessGen.generate(RR.getList());
        ProcessGen.generate();
        ProcessGen.addScheduleQueues(RR.getList());
        f.cpuProccess();
    }

    private static void mlq(){
        list = new LinkedBlockingQueue<>(); // the list to be passed around        
        list1 = new LinkedBlockingQueue<>(); // the list to be passed around        
                
        // ProcessGen.addScheduleQueues(list1);
        // ProcessGen.addScheduleQueues(list);
        Multilevel m = new Multilevel(list, list1);
        // ProcessGen.generate(Multilevel.getRrQueue(), Multilevel.getFcfsQueue());
        ProcessGen.generate();
        ProcessGen.addScheduleQueues(Multilevel.getRrQueue());
        ProcessGen.addScheduleQueues(Multilevel.getFcfsQueue());
        m.cpuProcess();
        // Multilevel f = new Multilevel();
        // f.setList(list);
        // ProcessGen.generate();
        // ProcessGen.addScheduleQueues(Multilevel.getList());
        // f.cpuProccess();
    }

    private static void mlfq(){
        // list = new LinkedBlockingQueue<>(); // the list to be passed around        
        
        // Multilevel f = new Multilevel();
        // f.setList(list);
        // ProcessGen.generate();
        // ProcessGen.addScheduleQueues(Multilevel.getList());
        // f.cpuProccess();
    }


    public static void main(String[] args) {   
        firstPage();  
        
            
        // LinkedBlockingQueue<Process> list = new LinkedBlockingQueue<>(); // the list to be passed around
        // LinkedBlockingQueue<Process> list1 = new LinkedBlockingQueue<>(); // the list to be passed around
        

        // System.out.println("list size "+list.size());
        
        // RR f = new RR();
        // NP_SJF f = new NP_SJF();
        // P_SJF f = new P_SJF();
        // NP_Priority f = new NP_Priority();
        // P_Priority f = new P_Priority();

        // Multilevel m = new Multilevel(list, list1);
        // ProcessGen.generate();
        // ProcessGen.addScheduleQueues(list);
        // ProcessGen.addScheduleQueues(list1);
        // m.cpuProcess();
        
        
        // System.out.println("RR created");
        // f.setList(list);
        // ProcessGen.generate();
        // ProcessGen.generate();
        // ProcessGen.addScheduleQueues(FCFS.getList());
        // ProcessGen.addScheduleQueues(NP_SJF.getList());
        // ProcessGen.addScheduleQueues(P_SJF.getList());
        // ProcessGen.addScheduleQueues(NP_Priority.getList());
        // ProcessGen.addScheduleQueues(P_Priority.getList());        
        // ProcessGen.addScheduleQueues(RR.getList());
       
        // f.cpuProccess();        
        System.out.println("Die Fool! ");
        // System.exit(0);
        // closeThreads();
        // Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        
        // for (Thread var : threadSet) {
        //     System.out.println("laskdjf;laks");
        //     var.stop();
        // }
        

        //====================================================================
        //====================================================================
        //====================================================================
        
        
        
    }
}