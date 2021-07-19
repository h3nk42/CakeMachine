package Threads.ActualSims;

import Threads.TestThreads.Wrapper;

class CreateThread extends Thread {

    private LockWrapper wrapper;
    private int sleep;
    private int threadName;

    public CreateThread(LockWrapper wrapper) {
        this.wrapper = wrapper;
    }


    public void run() {
        while(true){
            try {
                wrapper.create();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
