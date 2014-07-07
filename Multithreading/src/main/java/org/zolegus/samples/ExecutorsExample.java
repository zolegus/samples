package org.zolegus.samples;

import java.util.Random;
import java.util.concurrent.*;

public class ExecutorsExample implements Callable<Integer> {
	private static Random random = new Random(System.currentTimeMillis());

	public Integer call() throws Exception {
        long k = 0;
        for (long i = 0; i < 3500000000l; i++)
            k++;
        //Thread.sleep(1000);
		return random.nextInt(100);
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
        long startMillis = System.currentTimeMillis();
		ExecutorService executorService = Executors.newFixedThreadPool(12);
		Future<Integer>[] futures = new Future[12];
		for (int i = 0; i < futures.length; i++) {
			futures[i] = executorService.submit(new ExecutorsExample());
		}
		for (int i = 0; i < futures.length; i++) {
			Integer retVal = futures[i].get();
			System.out.println(retVal);
		}
		executorService.shutdown();
        long totalMillis = System.currentTimeMillis() - startMillis;
        System.out.println(totalMillis + " ms.");

	}
}
