package org.zolegus.samples.bytebuffer;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author oleg.zherebkin
 */
public class SimpleByteBufferCopy {
    public static void main(String[] args) {
        ByteBuffer bbSource = ByteBuffer.allocate(1024 * 64);
        ByteBuffer bbDestin = ByteBuffer.allocate(1024 * 64);
        while(bbSource.remaining() > 0)
            bbSource.put((byte) 1);
        bbSource.flip();

        int tests = 1000;
        long[] times = new long[tests];
        long start;
        int count= 0;


        for (int i = 0; i < tests; i++) {
            start = System.nanoTime();
            while (bbSource.remaining()>0)
                bbDestin.put(bbSource.get());
            times[count++] = System.nanoTime() - start;
            bbSource.rewind();
            bbDestin.clear();
        }
        Arrays.sort(times);
        System.out.printf("One byte Average latency %d runs for 50/90/99/99.9/99.99%% was %.2f/%.2f/%.2f/%.2f/%.2f us%n",
                tests,
                times[tests / 2] / 1e3,
                times[tests * 9 / 10] / 1e3,
                times[tests - tests / 10] / 1e3,
                times[tests - tests / 100] / 1e3,
                times[tests - tests / 1000] / 1e3);

        count = 0;
        bbSource.clear();
        while(bbSource.remaining() > 0)
            bbSource.put((byte) 2);
        bbSource.flip();
        for (int i = 0; i < tests; i++) {
            start = System.nanoTime();
            bbDestin.put(bbSource);
            times[count++] = System.nanoTime() - start;
            bbSource.rewind();
            bbDestin.clear();
        }
        Arrays.sort(times);
        System.out.printf("Bytebuffer Average latency %d runs for 50/90/99/99.9/99.99%% was %.2f/%.2f/%.2f/%.2f/%.2f us%n",
                tests,
                times[tests / 2] / 1e3,
                times[tests * 9 / 10] / 1e3,
                times[tests - tests / 10] / 1e3,
                times[tests - tests / 100] / 1e3,
                times[tests - tests / 1000] / 1e3);
    }
}
