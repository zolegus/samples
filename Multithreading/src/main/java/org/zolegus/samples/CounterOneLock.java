package org.zolegus.samples;

public class CounterOneLock implements Counter {
    private long customerCount = 0;
    private long shippingCount = 0;

    public synchronized void incrementCustomer() {
        customerCount++;
    }

    public synchronized void incrementShipping() {
        shippingCount++;
    }

    public synchronized long getCustomerCount() {
        return customerCount;
    }

    public synchronized long getShippingCount() {
        return shippingCount;
    }
}