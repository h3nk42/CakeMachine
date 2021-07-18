package Threads.TestThreads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadMain {
    public static void main(String[] args) {

        Wrapper wrapper = new Wrapper(1);
        int sleep = 0;
        CreateThread createThread = new CreateThread(wrapper, sleep, 1);
        CreateThread createThread2 = new CreateThread(wrapper, sleep, 2);
        CreateThread createThread3 = new CreateThread(wrapper, sleep, 3);
        DeleteThread deleteThread = new DeleteThread(wrapper, sleep, "delete - 1");
        DeleteThread deleteThread2 = new DeleteThread(wrapper, sleep, "delete - 2");
        DeleteThread deleteThread3 = new DeleteThread(wrapper, sleep, "delete - 3");
        createThread.start();
        createThread2.start();
        //createThread3.start();
        deleteThread.start();
        deleteThread2.start();
        //deleteThread3.start();
    }
}
