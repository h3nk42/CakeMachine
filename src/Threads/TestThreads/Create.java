package Threads.TestThreads;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Create {

    Lock createLock = new ReentrantLock();
    List<Integer> sharedList;

    public Create( List<Integer> sharedList){
        this.sharedList = sharedList;
    }


    public synchronized void create(int threadName) {
        try {
            boolean set = false;
            for(int i = 0; i<sharedList.size();i++) {
                if (sharedList.get(i) == null) {sharedList.set(i, threadName); set = true; break;}
            }
            if (!set) System.out.println("Threads/TestThreads/Wrapper.java: lineNumber: 22: " + "liste voll!");
            else { System.out.println("Threads/TestThreads/Wrapper.java: lineNumber: 23: " + "wert gesetzt");}
        }catch (Exception e){
        }finally {
            System.out.println("Threads/TestThreads/Wrapper.java: lineNumber: 23: " + sharedList);
            if(sharedList.get(sharedList.size()-1) != null ) createLock.lock();
            // lock.unlock();
        }

    }
}
