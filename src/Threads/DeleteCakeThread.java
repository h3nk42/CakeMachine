package Threads;

import control.automat.Automat;
import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.AutomatOperationType;
import control.automat.events.DataType;
import model.automat.verkaufsobjekte.kuchen.VerkaufsKuchen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class DeleteCakeThread extends Thread{

    private AutomatController automatController;
    private AutomatEventHandler automatEventHandler;
    private int sleep;
    private SimulationType simulationType;
    private Random r;
    private AutomatSimWrapper automatSimWrapper;


    public DeleteCakeThread(AutomatSimWrapper automatSimWrapper, AutomatController automatController, AutomatEventHandler automatEventHandler, int sleep, boolean isTest, SimulationType simulationType){
        this.automatSimWrapper = automatSimWrapper;
        this.automatEventHandler = automatEventHandler;
        this.automatController = automatController;
        this.sleep = sleep;
        this.simulationType = simulationType;
        if (isTest)  this.r = new Random(Long.parseLong("0123456789"));
        else this.r = new Random();
    }

    public Integer rollIndex(int maxIndex) {
        return r.nextInt(maxIndex);
    }

    private void deleteRandomCake() throws Exception {
        Automat a = automatController.getAutomat();
        List<VerkaufsKuchen> kuchen = a.getKuchen();
        Map<DataType, Object> tempMap = new HashMap<>();
        if(kuchen.size() > 0) {
            Integer random = rollIndex(kuchen.size());
            VerkaufsKuchen vk = kuchen.get(random);
            tempMap.put(DataType.fachnummer, vk.getFachnummer());
            deleteCakeSimSpecific(tempMap);
        }
        sleep(sleep);
    }

    private void deleteCakeSimSpecific(Map<DataType, Object> tempMap) throws InterruptedException {
        switch (this.simulationType){
            case sim1:
                AutomatEvent automatEvent = new AutomatEvent(this, tempMap, AutomatOperationType.dKuchen);
                automatEventHandler.handle(automatEvent);
                break;
            case sim2:
            case sim3:
                automatSimWrapper.deleteCake(r, simulationType);
                break;
        }
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

