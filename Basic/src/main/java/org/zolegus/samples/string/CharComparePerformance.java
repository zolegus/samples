package org.zolegus.samples.string;

import java.util.Arrays;

/**
 * @author oleg.zherebkin
 */
public class CharComparePerformance {
    public static void main(String[] args) throws InterruptedException {
        CharSequence charSequence = "AB";
        int tests = 1000000;
        long[] times = new long[tests];
        long start;
        for (int i = 0; i < tests; i++) {
            start = System.nanoTime();
//        while(true)
            for(int j=0; j < charSequence.length(); j++)
                if (charSequence.charAt(j)!="AC".charAt(j))
                    break;
            times[i] = System.nanoTime() - start;
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
