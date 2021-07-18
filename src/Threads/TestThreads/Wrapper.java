package Threads.TestThreads;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Wrapper {
    Lock lock = new ReentrantLock();
    Delete delete;
    Create create;
    Condition deleteCondition = lock.newCondition();
    Condition createCondition = lock.newCondition();
    volatile List<Integer> sharedList;
    volatile boolean isEmpty = true;
    volatile boolean isFull = false;

    public Wrapper( int listLength){
        this.sharedList = Arrays.asList(new Integer[listLength]);;
        this.create = new Create(sharedList, deleteCondition);
        this.delete = new Delete(sharedList, createCondition);
    }

    public void create(int threadName){
        try{
            lock.lock();
            calcListState();
            if(isFull) {
                System.out.println("Threads/TestThreads/Wrapper.java: lineNumber: 27: createCondition.await" );
                createCondition.await();
                //lock.lock();
            }
            create.create(threadName);
        } catch( Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public void delete(){
        try{
            lock.lock();
            calcListState();
            if(isEmpty) {
                System.out.println("Threads/TestThreads/Wrapper.java: lineNumber: 40: deleteCondtion.await");
                deleteCondition.await();
                //lock.lock();
            }
            delete.delete();
        } catch( Exception e) {

        } finally {
            lock.unlock();
        }
    }

    private void calcIfEmpty(){
        if(sharedList.get(0) == null) {
            isEmpty = true;
        } else {
            isEmpty = false;
        }
    }

    private void calcIfFull(){
        if(sharedList.get(sharedList.size()-1) != null) {
            isFull = true;
        } else isFull = false;
    }

    private synchronized void calcListState(){
        calcIfEmpty();
        calcIfFull();
    }
}
