package org.zolegus.samples;

import com.sun.jndi.toolkit.ctx.AtomicContext;

import java.util.concurrent.atomic.AtomicInteger;

public class MyThread extends Thread {


    public void run(){
        System.out.println("MyThread is running...");
        try {
            Thread.currentThread().sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("MyThread was interrupted.");
        }
        System.out.println("MyThread was finished");
    }

}
