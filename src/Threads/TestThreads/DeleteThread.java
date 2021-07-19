package Threads.TestThreads;

class DeleteThread extends Thread {


    private Wrapper wrapper;
    private int sleep;
    private String threadName;

    public DeleteThread(Wrapper wrapper, int sleep, String threadName) {
        this.wrapper = wrapper;
        this.sleep = sleep;
        this.threadName = threadName;
    }

    public void run() {
        while(true){
            try {
                wrapper.delete(threadName);
                sleep(sleep);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}