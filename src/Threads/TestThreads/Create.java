package Threads.TestThreads;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Create {

    Lock createLock = new ReentrantLock();
    List<Integer> sharedList;
    private Condition deleteCondition;

    public Create(List<Integer> sharedList, Condition deleteCondition){
        this.sharedList = sharedList;
        this.deleteCondition = deleteCondition;
    }


    public synchronized void create(int threadName) {
        try {
            createLock.lock();
            boolean set = false;
            for(int i = 0; i<sharedList.size();i++) {
                if (sharedList.get(i) == null) {sharedList.set(i, threadName); set = true; break;}
            }
            if (!set) System.out.println("\u001B[31m"+"Threads/TestThreads/Wrapper.java: lineNumber: 22: " + "liste voll!"+"\u001B[0m");
            else {
                System.out.println("Threads/TestThreads/Wrapper.java: lineNumber: 23: " + "wert gesetzt");
                deleteCondition.signal();
            }
        }catch (Exception e){
        }finally {
            System.out.println("Threads/TestThreads/Wrapper.java: lineNumber: 23: " + sharedList);
            createLock.unlock();
            //if(sharedList.get(sharedList.size()-1) != null ) createLock.lock();
            // lock.unlock();
        }

    }
}
