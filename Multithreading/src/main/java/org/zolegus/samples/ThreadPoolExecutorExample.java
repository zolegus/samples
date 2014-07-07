package org.zolegus.samples;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorExample implements Runnable {
	private static AtomicInteger counter = new AtomicInteger();
	private final int taskId;

	public int getTaskId() {
		return taskId;
	}

	public ThreadPoolExecutorExample(int taskId) {
		this.taskId = taskId;
	}

	public static void main(String[] args) {
		BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(100);
		ThreadFactory threadFactory = new ThreadFactory() {
			public Thread newThread(Runnable r) {
				int currentCount = counter.getAndIncrement();
				System.out.println("Creating new thread: " + currentCount);
				return new Thread(r, "mythread" + currentCount);
			}
		};
		RejectedExecutionHandler rejectedHandler = new RejectedExecutionHandler() {
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				if (r instanceof ThreadPoolExecutorExample) {
					ThreadPoolExecutorExample example = (ThreadPoolExecutorExample) r;
					System.out.println("Rejecting task with id " + example.getTaskId());
				}
			}
		};
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 3, TimeUnit.SECONDS, queue, threadFactory, rejectedHandler);
		for (int i = 0; i < 200; i++) {
			executor.execute(new ThreadPoolExecutorExample(i));
		}
//        while(executor.getActiveCount() >0)
//        {}
//        executor.shutdownNow();
		executor.shutdown();
	}

	public void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
            System.out.println("Thread " + this.taskId + " interupted!");
			//e.printStackTrace();
		}
        System.out.println("Thread " + this.taskId + " finished!");
    }
}
