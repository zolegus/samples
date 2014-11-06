package org.zolegus.samples.datetime;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @author oleg.zherebkin
 */
public class LocalDateTimeFormat {
    public static void main(String[] args) throws Exception {
        StringBuilder stringBuilder = new StringBuilder(1024 * 32);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss.SSS");
        LocalDateTime ldt;
        int tests = 1000000;
        long[] times = new long[tests];
        long start;
        int count= 0;
        for (int i = 0; i < tests; i++) {
            start = System.nanoTime();
            ldt = LocalDateTime.now(Clock.systemUTC());
            stringBuilder.append(ldt.format(dtf));
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
