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
        lock.lock();
        try{
            while(sharedList.get(sharedList.size()-1) != null)
                createCondition.await();
            create.create(threadName);
        } catch( Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public void delete(String threadName){
        lock.lock();
        try{
            while(sharedList.get(0) == null)
                deleteCondition.await();
            delete.delete();
        } catch( Exception e) {

        } finally {
            lock.unlock();
        }
    }

}