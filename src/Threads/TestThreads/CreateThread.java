package Threads.TestThreads;

class CreateThread extends Thread {

    private Wrapper wrapper;
    private int sleep;
    private int threadName;

    public CreateThread(Wrapper wrapper, int sleep, int threadName) {
        this.wrapper = wrapper;
        this.sleep = sleep;
        this.threadName = threadName;
    }


    public void run() {
        while(true){
            try {
                wrapper.create(threadName);
                System.out.println("ThreadName: " + threadName);
                sleep(sleep);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}