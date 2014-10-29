package org.zolegus.samples.bytebuffer;

import java.nio.ByteBuffer;

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
        long start = System.nanoTime();
        for (int i = 0; i < tests; i++) {
            while (bbSource.remaining()>0)
                bbDestin.put(bbSource.get());
            bbSource.rewind();
            bbDestin.clear();
        }
        long time = System.nanoTime() - start;
        System.out.printf("One byte copying average latency %.1f us%n", time / tests / 1e3);

        bbSource.clear();
        while(bbSource.remaining() > 0)
            bbSource.put((byte) 2);
        bbSource.flip();
        start = System.nanoTime();
        for (int i = 0; i < tests; i++) {
            bbDestin.put(bbSource);
            bbSource.rewind();
            bbDestin.clear();
        }
        time = System.nanoTime() - start;
        System.out.printf("All data copying average latency %.1f us%n", time / tests / 1e3);

    }
}
