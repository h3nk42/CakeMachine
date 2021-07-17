package Threads.TestThreads;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Wrapper {
    List<Integer> sharedList;
    Lock lock = new ReentrantLock();
    Delete delete;
    Create create;
    Condition condition = lock.newCondition();

    public Wrapper( List<Integer> sharedList ){
        this.sharedList = sharedList;
        this.create = new Create(sharedList);
        this.delete = new Delete(sharedList);
    }

    public synchronized void create(int threadName){
        lock.lock();
        try{
            create.create(threadName);
        } catch( Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public void delete(){
        lock.lock();
        try{
            delete.delete();
        } catch( Exception e) {

        } finally {
            lock.unlock();
        }
    }

}
