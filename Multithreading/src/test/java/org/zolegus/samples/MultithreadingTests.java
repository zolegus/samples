package org.zolegus.samples;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertThat;

/**
 * Created by zolegus on 7/5/14.
 */
public class MultithreadingTests {

    @Test
    public void testPutOnEmptyQueueBlocks() throws InterruptedException {
        final SimpleBlockingQueue queue = new SimpleBlockingQueue();
        BlockingThread blockingThread = new BlockingThread(queue);
        blockingThread.start();
        Thread.sleep(5000);
        assertThat(blockingThread.isReachedAfterGet(), is(false));
        assertThat(blockingThread.isWasInterrupted(), is(false));
        assertThat(blockingThread.isThrowableThrown(), is(false));
        queue.put(new Object());
        Thread.sleep(1000);
        assertThat(blockingThread.isReachedAfterGet(), is(true));
        assertThat(blockingThread.isWasInterrupted(), is(false));
        assertThat(blockingThread.isThrowableThrown(), is(false));
        blockingThread.join();
    }

    @Test
    public void testParallelInsertionAndConsumption() throws InterruptedException, ExecutionException {
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        ExecutorService threadPool = Executors.newFixedThreadPool(NUM_THREADS);
        final CountDownLatch latch = new CountDownLatch(NUM_THREADS);
        List<Future<Integer>> futuresPut = new ArrayList<Future<Integer>>();
        for (int i = 0; i < 3; i++) {
            Future<Integer> submit = threadPool.submit(new Callable<Integer>() {
                public Integer call() {
                    int sum = 0;
                    for (int i = 0; i < 1000; i++) {
                        int nextInt = ThreadLocalRandom.current().nextInt(100);
                        queue.put(nextInt);
                        sum += nextInt;
                    }
                    latch.countDown();
                    return sum;
                }
            });
            futuresPut.add(submit);
        }
        List<Future<Integer>> futuresGet = new ArrayList<Future<Integer>>();
        for (int i = 0; i < 3; i++) {
            Future<Integer> submit = threadPool.submit(new Callable<Integer>() {
                public Integer call() {
                    int count = 0;
                    try {
                        for (int i = 0; i < 1000; i++) {
                            Integer got = queue.get();
                            count += got;
                        }
                    } catch (InterruptedException e) {

                    }
                    latch.countDown();
                    return count;
                }
            });
            futuresGet.add(submit);
        }
        latch.await();
        int sumPut = 0;
        for (Future<Integer> future : futuresPut) {
            sumPut += future.get();
        }
        int sumGet = 0;
        for (Future<Integer> future : futuresGet) {
            sumGet += future.get();
        }
        assertThat(sumPut, is(sumGet));
    }
    //, is(sumGet));


    @Test
    public void testPerformance() throws InterruptedException {
        for (int numThreads = 1; numThreads < THREADS_MAX; numThreads++) {
            long startMillis = System.currentTimeMillis();
            final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
            ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
            for (int i = 0; i < numThreads; i++) {
                threadPool.submit(new Runnable() {
                    public void run() {
                        for (long i = 0; i < ITERATIONS; i++) {
                            int nextInt = ThreadLocalRandom.current().nextInt(100);
                            try {
                                queue.put(nextInt);
                                nextInt = queue.get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
            threadPool.shutdown();
            threadPool.awaitTermination(5, TimeUnit.MINUTES);
            long totalMillis = System.currentTimeMillis() - startMillis;
            double throughput = (double)(numThreads * ITERATIONS * 2) / (double) totalMillis;
            System.out.println(String.format("%s with %d threads: %dms (throughput: %.1f ops/s)", LinkedBlockingQueue.class.getSimpleName(), numThreads, totalMillis, throughput));
        }
    }

    @Test
    public void stressTest() throws InterruptedException {
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        blitzer.blitz(new Runnable() {
            public void run() {
                try {
                    queue.put(42);
                    queue.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        assertThat(queue.getSize(), is(0));
    }
}
