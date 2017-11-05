package system ;

import java.util.concurrent.TimeUnit;

public class Utility{

    public static void sleep(){
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep2(){ // for device
        try {
            Thread.sleep(500L);            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  static long time() {
		long timeMillis = System.currentTimeMillis();
        long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);                 
		return timeSeconds;
    }

    public static void stopDevices(){
        System.out.println("Stop Devices!");
        for (Integer i : Device.getCurrentDevices().keySet()) {
            Device.getCurrentDevices().get(i).getExecutor().shutdown();
        }
    }

}