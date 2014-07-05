package org.zolegus.samples;

import java.util.concurrent.atomic.AtomicLong;

public static class CounterAtomic implements Counter {
    private AtomicLong customerCount = new AtomicLong();
    private AtomicLong shippingCount = new AtomicLong();

    public void incrementCustomer() {
        customerCount.incrementAndGet();
    }

    public void incrementShipping() {
        shippingCount.incrementAndGet();
    }

    public long getCustomerCount() {
        return customerCount.get();
    }

    public long getShippingCount() {
        return shippingCount.get();
    }
}
