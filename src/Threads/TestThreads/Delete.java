package Threads.TestThreads;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Delete {
    Lock deleteLock = new ReentrantLock();
    private List<Integer> sharedList;
    private Condition createCondition;

    public Delete(List<Integer> sharedList, Condition createCondition){

        this.sharedList = sharedList;
        this.createCondition = createCondition;
    }

    public void delete(){
        try {
            deleteLock.lock();
            boolean didDelete = false;
            for(int i = this.sharedList.size()-1; i>=0; i--) {
                if (this.sharedList.get(i) != null) {this.sharedList.set(i, null); didDelete = true; break;}
            }
            if(didDelete) {
                System.out.println("Threads/TestThreads/Delete.java: lineNumber: 21: wert entfernt");
                createCondition.signal();
            }
            else System.out.println("\u001B[31m"+"Threads/TestThreads/Delete.java: lineNumber: 22: liste ist leer!" +"\u001B[0m");
            System.out.println("Threads/TestThreads/Wrapper.java: lineNumber: 23: " + sharedList);
        } catch (Exception e) {

        } finally {
            deleteLock.unlock();
        }
    }
}
