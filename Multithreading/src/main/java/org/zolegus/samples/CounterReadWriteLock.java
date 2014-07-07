package org.zolegus.samples;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CounterReadWriteLock implements Counter {
    private final ReentrantReadWriteLock customerLock = new ReentrantReadWriteLock();
    private final Lock customerWriteLock = customerLock.writeLock();
    private final Lock customerReadLock = customerLock.readLock();
    private final ReentrantReadWriteLock shippingLock = new ReentrantReadWriteLock();
    private final Lock shippingWriteLock = shippingLock.writeLock();
    private final Lock shippingReadLock = shippingLock.readLock();
    private long customerCount = 0;
    private long shippingCount = 0;

    public void incrementCustomer() {
        customerWriteLock.lock();
        customerCount++;
        customerWriteLock.unlock();
    }

    public void incrementShipping() {
        shippingWriteLock.lock();
        shippingCount++;
        shippingWriteLock.unlock();
    }

    public long getCustomerCount() {
        customerReadLock.lock();
        long count = customerCount;
        customerReadLock.unlock();
        return count;
    }

    public long getShippingCount() {
        shippingReadLock.lock();
        long count = shippingCount;
        shippingReadLock.unlock();
        return count;
    }
}

