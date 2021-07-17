package Threads.TestThreads;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Delete {
    Lock deleteLock = new ReentrantLock();
    private List<Integer> sharedList;

    public Delete(List<Integer> sharedList){

        this.sharedList = sharedList;
    }

    public void delete(){
        boolean didDelete = false;
        for(int i = this.sharedList.size()-1; i>=0; i--) {
            if (this.sharedList.get(i) != null) {this.sharedList.set(i, null); didDelete = true;}
        }
        if(didDelete) System.out.println("Threads/TestThreads/Delete.java: lineNumber: 21: wert entfernt");
        else System.out.println("Threads/TestThreads/Delete.java: lineNumber: 22: liste ist leer!" );
    }
}
