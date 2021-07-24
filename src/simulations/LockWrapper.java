package simulations;

import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.AutomatOperationType;
import control.automat.events.CakeDataType;
import control.lib.SimulationLib;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockWrapper {
    Lock lock = new ReentrantLock();
    Condition deleteCondition = lock.newCondition();
    Condition createCondition = lock.newCondition();
    private AutomatController automatController;
    private AutomatEventHandler automatEventHandler;
    Random r;

    public LockWrapper(AutomatController automatController, AutomatEventHandler automatEventHandler){
        this.automatController = automatController;
        this.automatEventHandler = automatEventHandler;
        this.r = new Random(Long.parseLong("0123456789"));
    }
    public LockWrapper(AutomatController automatController,AutomatEventHandler automatEventHandler, Random r){
        this.automatController = automatController;
        this.automatEventHandler = automatEventHandler;
        this.r = r;
    }

    public void createCakeUnsynchronized(SimulationType simulationType, boolean isTest) throws Exception {
        Map<CakeDataType, Object> cakeData = SimulationLib.rollCake(r);
        AutomatEvent automatEvent;
        automatEvent = new AutomatEvent(this,cakeData, AutomatOperationType.cKuchen);
        this.automatEventHandler.handle(automatEvent);
        if(!isTest && simulationType != SimulationType.sim1) deleteCondition.signal();
    }
    public void deleteCakeUnsynchronized(SimulationType simulationType, boolean isTest) throws Exception {
        Map<CakeDataType, Object> cakeData;
        switch (simulationType){
            case sim2:
            case sim3:
                cakeData = SimulationLib.getOldestCake(r, automatController.getAutomat().getKuchen());
                break;
            default:
                cakeData = SimulationLib.rollFachnummer(r, automatController.getAutomat().getKuchen());
                break;
        }
        AutomatEvent automatEvent;
        automatEvent = new AutomatEvent(this,cakeData, AutomatOperationType.dKuchen);
        if(cakeData==null) {
            System.out.println("simulations/Threads/ActualSims/LockWrapper.java: lineNumber: 45: " + "Keine Kuchen zum loeschen vorhanden!");
        } else {
            this.automatEventHandler.handle(automatEvent);
            if(!isTest && simulationType != SimulationType.sim1) createCondition.signal();
        }
    }


    public void createCakeSynchronized(SimulationType simulationType){
        lock.lock();
        try{
            while(automatController.getAutomat().isFull()) {
                createCondition.await();
            }
            this.createCakeUnsynchronized(simulationType,false);
        } catch( Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public void deleteCakeSynchronized(SimulationType simulationType, boolean isTest){
        lock.lock();
        try{
            while(automatController.getAutomat().isEmpty())
                deleteCondition.await();
            if(simulationType == SimulationType.sim3){
                Integer randomInt = SimulationLib.rollIndex(r,automatController.getAutomat().getKuchen().size()+1);
                for (int i = 1; i <= randomInt; i++)  {
                    this.deleteCakeUnsynchronized(simulationType, isTest);
                }
                System.out.println("simulations/Threads/ActualSims/LockWrapper.java: lineNumber: 90: " + randomInt + " älteste Kuchen aufeinmal geloescht");
            } else {
                this.deleteCakeUnsynchronized(simulationType, isTest);
            }
        } catch( Exception e) {

        } finally {
            lock.unlock();
        }
    }

    public void inspectUnsynchronized() throws Exception {
        Map<CakeDataType, Object> cakeData = SimulationLib.rollFachnummer(r, automatController.getAutomat().getKuchen());
        AutomatEvent automatEvent;
        automatEvent = new AutomatEvent(this,cakeData, AutomatOperationType.inspectKuchen);
        if(cakeData==null) {
            System.out.println("simulations/Threads/ActualSims/LockWrapper.java: lineNumber: 45: " + "Keine Kuchen zum inspizieren vorhanden!");
        } else {
            this.automatEventHandler.handle(automatEvent);
            System.out.println("simulations/Threads/AutomatSimWrapper.java: lineNumber: 51: " + "Kuchen inspiziert");
        }
    }

}
