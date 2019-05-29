package basejava.main;

import java.util.Arrays;

public class MainDeadlock {
    private static Thread threadFirst;
    private static Thread threadSecond;

    public static void main(String[] args) {

        String[] people = {"Tom", "Alice", "Sam"};
        Integer[] numbers = {23, 4, 5, 2};

        threadFirst = new Thread(() -> {
            try {
                joinThread(threadSecond, threadFirst, people);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread_First");

        threadSecond = new Thread(() -> {
            try {
                joinThread(threadFirst, threadSecond, numbers);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread_Second");

        // DeadLock
        threadFirst.start();

        // Without deadlock
        //threadFirst.join();

        threadSecond.start();
    }

    private static <T> void joinThread(Thread threadJoin, Thread threadCurrent, T[] items) throws InterruptedException {
        System.out.println(threadCurrent.getName() + " start");
        threadJoin.join();
        Arrays.asList(items).forEach(System.out::println);
        System.out.println(threadCurrent.getName() + " finished");
    }
}
