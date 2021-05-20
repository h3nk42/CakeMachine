package Threads;

import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.DataType;
import control.automat.events.OperationType;
import model.automat.verkaufsobjekte.kuchen.VerkaufsKuchen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class DeleteCakeThread extends Thread{

    private AutomatController automatController;
    private int sleep;
    private AutomatSimWrapper automatSimWrapper;
    private Random r = new Random(Long.parseLong("0123456789"));

    public DeleteCakeThread(AutomatSimWrapper automatSimWrapper, AutomatController automatController, int sleep){
        this.automatSimWrapper = automatSimWrapper;
        this.automatController = automatController;
        this.sleep = sleep;
    }

    public Integer rollIndex(int maxIndex) {
        return r.nextInt(maxIndex);
    }

    private void deleteRandomCake() throws Exception {

        List<VerkaufsKuchen> kuchen = automatController.getKuchen();
        Map<DataType, Object> tempMap = new HashMap<>();
        if(kuchen.size() > 0) {
            System.out.println("Threads/DeleteCakeThread.java: size: " + automatController.getKuchen().size());
            Integer random = rollIndex(kuchen.size());
            System.out.println("Threads/DeleteCakeThread.java: random: " + random);
            VerkaufsKuchen vk = kuchen.get(random);
            System.out.println("Threads/DeleteCakeThread.java: fachNummer: " + vk.getFachnummer());
            tempMap.put(DataType.fachnummer, vk.getFachnummer());
            automatSimWrapper.deleteCake(tempMap);
            System.out.println("Threads/DeleteCakeThread.java: lineNumber: 14: " + "removed cake!");
        }
        sleep(sleep);
    }

    public void run() {
        while(true){
            try {
                deleteRandomCake();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

