package Threads.TestThreads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadMain {
    public static void main(String[] args) {

        List<Integer> sharedList = Arrays.asList(new Integer[3]);
        Wrapper wrapper = new Wrapper(sharedList);

        int sleep = 2000;
        CreateThread createThread = new CreateThread(wrapper, sleep, 1);
        CreateThread createThread2 = new CreateThread(wrapper, sleep, 2);
        DeleteThread deleteThread = new DeleteThread(wrapper, sleep, "create - 1");
        createThread.start();
        createThread2.start();
        deleteThread.start();
    }
}
