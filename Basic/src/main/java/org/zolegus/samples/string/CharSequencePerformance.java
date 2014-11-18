package org.zolegus.samples.string;

import java.util.Arrays;

/**
 * @author oleg.zherebkin
 */
public class CharSequencePerformance {
    public static void main(String[] args) {
        CharSequence charSequence = "";
        int tests = 1000000;
        long[] times = new long[tests];
        long start;
        int count= 0;
        StringBuilder stringBuilder = new StringBuilder(10);
        for (int i = 0; i < tests; i++) {
            stringBuilder.append(i);
            start = System.nanoTime();
            charSequence = stringBuilder.subSequence(0,stringBuilder.length());
            charSequence = "";
            times[count++] = System.nanoTime() - start;
            stringBuilder.setLength(0);
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
