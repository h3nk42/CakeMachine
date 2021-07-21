package Threads.ActualSims;

import Threads.SimulationType;
import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatOperationType;
import control.automat.events.DataType;
import lib.SimulationLib;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockWrapper {
    Lock lock = new ReentrantLock();
    Condition deleteCondition = lock.newCondition();
    Condition createCondition = lock.newCondition();
    private AutomatController automatController;
    Random r;

    public LockWrapper(AutomatController automatController){
        this.automatController = automatController;
        this.r = new Random(Long.parseLong("0123456789"));
    }
    public LockWrapper(AutomatController automatController, Random r){
        this.automatController = automatController;
        this.r = r;
    }

    public void createCakeUnsynchronized(){
        Map<DataType, Object> cakeData = SimulationLib.rollCake(r);
        AutomatEvent automatEvent;
        automatEvent = new AutomatEvent(this,cakeData, AutomatOperationType.cKuchen);
        automatController.getAutomatEventHandler().handle(automatEvent);
        deleteCondition.signal();
    }
    public void deleteCakeUnsynchronized(SimulationType simulationType){
        Map<DataType, Object> cakeData;
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
            System.out.println("Threads/ActualSims/LockWrapper.java: lineNumber: 45: " + "Keine Kuchen zum loeschen vorhanden!");
        } else {
            automatController.getAutomatEventHandler().handle(automatEvent);
            createCondition.signal();
        }
    }


    public void createCakeSynchronized(){
        lock.lock();
        try{
            while(automatController.getAutomat().isFull()) {
                createCondition.await();
            }
            this.createCakeUnsynchronized();
        } catch( Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public void deleteCakeSynchronized(SimulationType simulationType){
        lock.lock();
        try{
            while(automatController.getAutomat().isEmpty())
                deleteCondition.await();
            if(simulationType == SimulationType.sim3){
                Integer randomInt = SimulationLib.rollIndex(r,automatController.getAutomat().getKuchen().size()+1);
                for (int i = 1; i <= randomInt; i++)  {
                    this.deleteCakeUnsynchronized(simulationType);
                }
                System.out.println("Threads/ActualSims/LockWrapper.java: lineNumber: 90: " + randomInt + " älteste Kuchen aufeinmal geloescht");
            } else {
                this.deleteCakeUnsynchronized(simulationType);
            }
        } catch( Exception e) {

        } finally {
            lock.unlock();
        }
    }

    public void inspectUnsynchronized(){
        Map<DataType, Object> cakeData = SimulationLib.rollFachnummer(r, automatController.getAutomat().getKuchen());
        AutomatEvent automatEvent;
        automatEvent = new AutomatEvent(this,cakeData, AutomatOperationType.inspectKuchen);
        if(cakeData==null) {
            System.out.println("Threads/ActualSims/LockWrapper.java: lineNumber: 45: " + "Keine Kuchen zum inspizieren vorhanden!");
        } else {
            automatController.getAutomatEventHandler().handle(automatEvent);
            System.out.println("Threads/AutomatSimWrapper.java: lineNumber: 51: " + "Kuchen inspiziert");
        }
    }

}
