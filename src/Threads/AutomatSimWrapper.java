package Threads;

import control.automat.Automat;
import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.DataType;
import control.automat.events.AutomatOperationType;

import java.util.Map;

import static java.lang.Thread.sleep;

public class AutomatSimWrapper {

    AutomatController automatController;
    private AutomatEventHandler aHandler;

    public AutomatSimWrapper(AutomatController automatController,AutomatEventHandler aHandler) {
        this.automatController = automatController;
        this.aHandler = aHandler;
    }

    public synchronized void addCake(Map<DataType, Object> cakeData) throws InterruptedException {
        Automat a = automatController.getAutomat();
        if(a.isFull()) wait();
        AutomatEvent automatEvent = new AutomatEvent(this,cakeData, AutomatOperationType.cKuchen);
        aHandler.handle(automatEvent);
        notify();
    }
    public synchronized void deleteCake( Map<DataType, Object> tempMap) throws InterruptedException {
        Automat a = automatController.getAutomat();
        if(a.isEmpty()) wait();
        AutomatEvent automatEvent = new AutomatEvent(this, tempMap, AutomatOperationType.dKuchen);
        aHandler.handle(automatEvent);
        notify();
    }

}
