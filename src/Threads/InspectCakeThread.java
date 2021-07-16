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


public class InspectCakeThread extends Thread{

    private AutomatController automatController;
    private AutomatEventHandler automatEventHandler;
    private int sleep;
    private SimulationType simulationType;
    private Random r = new Random(Long.parseLong("0123456789"));
    private AutomatSimWrapper automatSimWrapper;


    public InspectCakeThread(AutomatSimWrapper automatSimWrapper, AutomatController automatController, AutomatEventHandler automatEventHandler, int sleep, SimulationType simulationType){
        this.automatSimWrapper = automatSimWrapper;
        this.automatEventHandler = automatEventHandler;
        this.automatController = automatController;
        this.sleep = sleep;
        this.simulationType = simulationType;
    }

    public Integer rollIndex(int maxIndex) {
        return r.nextInt(maxIndex);
    }

    private void inspectRandomCake() throws Exception {
        Automat a = automatController.getAutomat();
        List<VerkaufsKuchen> kuchen = a.getKuchen();
        Map<DataType, Object> tempMap = new HashMap<>();
        if(kuchen.size() > 0) {
            Integer random = rollIndex(kuchen.size());
            tempMap.put(DataType.fachnummer, kuchen.get(random).getFachnummer());
            a.aktualisiereInspektionsdatum(kuchen.get(random).getFachnummer());
        }
        sleep(sleep);
    }

   /* private void inspectCakeSimSpecific(Map<DataType, Object> tempMap) throws InterruptedException {
        switch (this.simulationType){
            case sim1:
                AutomatEvent automatEvent = new AutomatEvent(this, tempMap, AutomatOperationType.inspectKuchen);
                automatEventHandler.handle(automatEvent);
                break;
            case sim2:
                automatSimWrapper.deleteCake(tempMap);
                break;
            case sim3:
                break;
        }
    }*/

    public void run() {
        while(true){
            try {
                inspectRandomCake();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

