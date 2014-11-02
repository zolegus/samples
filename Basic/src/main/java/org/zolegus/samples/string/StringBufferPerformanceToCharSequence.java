package org.zolegus.samples.string;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author oleg.zherebkin
 */
public class StringBufferPerformanceToCharSequence {

    private static byte[] encodeUtf8(CharSequence cs) {
        ByteBuffer bb = Charset.forName("ASCII").encode(CharBuffer.wrap(cs));
        byte[] result = new byte[bb.remaining()];
        bb.get(result);
        return result;
    }

    public static void main(String[] args) {
        ByteBuffer builderBuffer = ByteBuffer.allocate(1024 * 64);
        StringBuilder stringBuilder = new StringBuilder(1024 * 64);
        stringBuilder.setLength(0);
        stringBuilder.append("8=FIX.4.2|35=A|9=145|91=112A04B0-5AAF-42F4-994E-FA7CB959C60B|90=36|98=0|108=1|553=username|554=password|384=1|372=d|34=1|52=20141029-22:51:33.708|49=T4Example|56=CTS|10=198|");
        int tests = 100000;
        long[] times = new long[tests];
        long start;
        int count= 0;
        for (int i = 0; i < tests; i++) {
            start = System.nanoTime();
            builderBuffer.put(encodeUtf8(stringBuilder.subSequence(0,stringBuilder.length())));
            times[count++] = System.nanoTime() - start;
            builderBuffer.clear();
        }
        Arrays.sort(times);
        System.out.printf("Average latency %d runs for 50/90/99/99.9/99.99%%/worst was %.2f/%.2f/%.2f/%.2f/%.2f/%.2f us%n",
                tests,
                times[tests / 2] / 1e3,
                times[tests * 9 / 10] / 1e3,
                times[tests - tests / 10] / 1e3,
                times[tests - tests / 100] / 1e3,
                times[tests - tests / 1000] / 1e3,
                times[times.length-1] / 1e3);
    }
}
