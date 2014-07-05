package org.zolegus.samples;

import java.util.Random;
import java.util.concurrent.*;

public class FindMinTask implements Callable<Integer> {
    private int[] numbers;
    private int startIndex;
    private int endIndex;
    private ExecutorService executorService;

    public FindMinTask(ExecutorService executorService, int[] numbers, int startIndex, int endIndex) {
        this.executorService = executorService;
        this.numbers = numbers;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public Integer call() throws Exception {
        int sliceLength = (endIndex - startIndex) + 1;
        if (sliceLength > 2) {
            FindMinTask lowerFindMin = new FindMinTask(executorService, numbers, startIndex, startIndex + (sliceLength / 2) - 1);
            Future<Integer> futureLowerFindMin = executorService.submit(lowerFindMin);
            FindMinTask upperFindMin = new FindMinTask(executorService, numbers, startIndex + (sliceLength / 2), endIndex);
            Future<Integer> futureUpperFindMin = executorService.submit(upperFindMin);
            return Math.min(futureLowerFindMin.get(), futureUpperFindMin.get());
        } else {
            return Math.min(numbers[startIndex], numbers[endIndex]);
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] numbers = new int[100];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(64);
        Future<Integer> futureResult = executorService.submit(new FindMinTask(executorService, numbers, 0, numbers.length-1));
        System.out.println(futureResult.get());
        executorService.shutdown();
    }
}

