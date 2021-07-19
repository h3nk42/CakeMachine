package Threads;

import control.automat.Automat;
import control.automat.AutomatController;
import control.automat.events.AutomatEvent;
import control.automat.events.AutomatEventHandler;
import control.automat.events.DataType;
import control.automat.events.AutomatOperationType;
import model.automat.verkaufsobjekte.kuchen.KuchenComparators;
import model.automat.verkaufsobjekte.kuchen.VerkaufsKuchen;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

public class AutomatSimWrapper {

    AutomatController automatController;
    private AutomatEventHandler aHandler;
    Lock lock = new ReentrantLock();
    Condition deleteCondition = lock.newCondition();
    Condition createCondition = lock.newCondition();
    Automat a;

    public AutomatSimWrapper(AutomatController automatController,AutomatEventHandler aHandler) {
        this.automatController = automatController;
        this.aHandler = aHandler;
        this.a = automatController.getAutomat();
    }

    public void create(int threadName){
        lock.lock();
        try{
            while(a.isFull())
                createCondition.await();
            //create.create(threadName);
        } catch( Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public synchronized void addCake(Map<DataType, Object> cakeData) throws InterruptedException {
        Automat a = automatController.getAutomat();
        //if(a.isFull()) wait();
        if(a.isFull()) lock.lock();
        try {
            AutomatEvent automatEvent = new AutomatEvent(this, cakeData, AutomatOperationType.cKuchen);
            aHandler.handle(automatEvent);
        } catch (Exception e ) {}
        finally {
            lock.unlock();
        }
        //notify();
    }

    public synchronized void deleteCake(Random random, SimulationType simulationType) throws InterruptedException {
        Automat a = automatController.getAutomat();
        List<VerkaufsKuchen> kuchen = a.getKuchen();
        Map<DataType, Object> tempMap = new HashMap<>();
        AutomatEvent automatEvent;
        Collections.sort(kuchen, new KuchenComparators.InspektionsDatumComparator());
       // if (a.isEmpty()) wait();
        if (a.isEmpty()) lock.lock();
        try {
            switch (simulationType) {
                case sim2:
                    tempMap.put(DataType.fachnummer, kuchen.get(0).getFachnummer());
                    automatEvent = new AutomatEvent(this, tempMap, AutomatOperationType.dKuchen);
                    aHandler.handle(automatEvent);
                    //notify();
                    lock.unlock();
                    break;
                case sim3:
                    Integer randomInt = rollIndex(kuchen.size()+1, random);
                    System.out.println("Threads/AutomatSimWrapper.java: lineNumber: 55: kuchenSize" + kuchen.size());
                    System.out.println("Threads/AutomatSimWrapper.java: lineNumber: 55: randomInt:" + randomInt);
                    for (int i = 1; i <= randomInt; i++)  {
                        int fachnummer = i -1;
                        System.out.println("Threads/AutomatSimWrapper.java: lineNumber: 52: kuchen mit Fachnummer:" +  kuchen.get(fachnummer).getFachnummer() + "geloescht");
                        tempMap.put(DataType.fachnummer, kuchen.get(fachnummer).getFachnummer());
                        automatEvent = new AutomatEvent(this, tempMap, AutomatOperationType.dKuchen);
                        aHandler.handle(automatEvent);
                    }
                    if (randomInt>0) {
                        lock.unlock();
                    }
                    break;
            }
        } catch (Exception e ) {}
        finally {
            //lock.unlock();
        }
    }

    public synchronized void inspectRandomCake(Random random) throws Exception {
        Automat a = automatController.getAutomat();
        List<VerkaufsKuchen> kuchen = a.getKuchen();
        Map<DataType, Object> tempMap = new HashMap<>();
        if(a.isEmpty()) return;
            Integer randomInt = rollIndex(kuchen.size(), random);
            tempMap.put(DataType.fachnummer, kuchen.get(randomInt).getFachnummer());
            a.aktualisiereInspektionsdatum(kuchen.get(randomInt).getFachnummer());
            System.out.println("Threads/AutomatSimWrapper.java: lineNumber: 51: " + "Kuchen inspiziert");
    }

    private Integer rollIndex(int maxIndex, Random random) {
        return random.nextInt(maxIndex);
    }
}
