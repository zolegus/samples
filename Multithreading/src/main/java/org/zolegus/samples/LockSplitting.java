package org.zolegus.samples;

import java.util.concurrent.ThreadLocalRandom;

public class LockSplitting implements Runnable {
    private static final int NUMBER_OF_THREADS = 5;
    private Counter counter;



//    public static class CounterOneLock implements Counter { ... }

//    public static class CounterSeparateLock implements Counter { ... }

    public LockSplitting(Counter counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < 100000; i++) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                counter.incrementCustomer();
            } else {
                counter.incrementShipping();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[NUMBER_OF_THREADS];
        Counter counter = new CounterOneLock();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i] = new Thread(new LockSplitting(counter));
        }
        long startMillis = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i].start();
        }
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i].join();
        }
        System.out.println((System.currentTimeMillis() - startMillis) + "ms");
    }
}
