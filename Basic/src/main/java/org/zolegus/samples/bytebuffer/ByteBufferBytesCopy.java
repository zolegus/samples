package org.zolegus.samples.bytebuffer;

import net.openhft.lang.io.ByteBufferBytes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @author oleg.zherebkin
 */
public class ByteBufferBytesCopy {

    public static void main(String[] args) {
        ByteBufferBytes bbbSource = new ByteBufferBytes(ByteBuffer.allocate(1024 * 64).order(ByteOrder.nativeOrder()));
        ByteBufferBytes bbbDestin = new ByteBufferBytes(ByteBuffer.allocate(1024 * 64).order(ByteOrder.nativeOrder()));

        while(bbbSource.remaining() > 0)
            bbbSource.write((byte) 1);
        bbbSource.flip();

        int tests = 1000;
        long[] times = new long[tests];
        long start;
        int count= 0;
        for (int i = 0; i < tests; i++) {
            start = System.nanoTime();
            while (bbbSource.remaining()>0)
                bbbDestin.write(bbbSource.read());
            times[count++] = System.nanoTime() - start;
            bbbSource.position(0);
            bbbDestin.clear();
        }
        Arrays.sort(times);
        System.out.printf("Average latency %d runs for 50/90/99/99.9/99.99%% was %.2f/%.2f/%.2f/%.2f/%.2f us%n",
                tests,
                times[tests / 2] / 1e3,
                times[tests * 9 / 10] / 1e3,
                times[tests - tests / 10] / 1e3,
                times[tests - tests / 100] / 1e3,
                times[tests - tests / 1000] / 1e3);

        count = 0;
        bbbSource.clear();
        while(bbbSource.remaining() > 0)
            bbbSource.write((byte) 2);
        bbbSource.flip();
        for (int i = 0; i < tests; i++) {
            start = System.nanoTime();
            bbbDestin.write(bbbSource);
            times[count++] = System.nanoTime() - start;
            bbbSource.position(0);
            bbbDestin.clear();
        }
        Arrays.sort(times);
        System.out.printf("Average latency %d runs for 50/90/99/99.9/99.99%% was %.2f/%.2f/%.2f/%.2f/%.2f us%n",
                tests,
                times[tests / 2] / 1e3,
                times[tests * 9 / 10] / 1e3,
                times[tests - tests / 10] / 1e3,
                times[tests - tests / 100] / 1e3,
                times[tests - tests / 1000] / 1e3);
    }

}
