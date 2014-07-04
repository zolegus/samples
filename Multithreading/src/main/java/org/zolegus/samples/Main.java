package org.zolegus.samples;

/**
 * Created by zolegus on 6/10/14.
 */

public class Main {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        t1.start();
        System.out.println("Thread t1 was started");
        System.out.println("Waiting when t1 will be finished.");
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("all stopped.");
    }
}
