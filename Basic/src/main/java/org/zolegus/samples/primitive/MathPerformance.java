package org.zolegus.samples.primitive;

import java.util.Arrays;

/**
 * @author oleg.zherebkin
 */
public class MathPerformance {
    public static void main(String[] args) {
        int tests = 1000000;
        long[] times = new long[tests];
        long start;
        int result = 0;
        System.out.println("TimeZone convert:");
        for(int run = 0;run<5;run++) {
            for (int i = 0; i < tests; i++) {
                start = System.nanoTime();
//        while (true)
                result = Math.abs(1234);
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
                    times[times.length - 1] / 1e3);
        }
    }
}

