package org.zolegus.samples.datetime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @author oleg.zherebkin
 */
public class ConvertTimeZone {

    public static void main(String[] args) throws InterruptedException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss.SSS");
        String dtString = "20141125-12:05:00.409";
        LocalDateTime dateTime = LocalDateTime.parse(dtString, dtf);
        LocalDateTime tmp;
        ZonedDateTime zonedDateTime;

        int tests = 1000000;
        long[] times = new long[tests];
        long start;
        System.out.println("TimeZone convert:");
        for (int run = 0; run < 5; run ++) {
            for (int i = 0; i < tests; i++) {
                start = System.nanoTime();
//        while(true)
            zonedDateTime = dateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("America/Chicago"));
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

        System.out.println("DateTime api utilities:");
        for (int run = 0; run < 5; run ++) {
            for (int i = 0; i < tests; i++) {
                start = System.nanoTime();
//        while(true)
                tmp = dateTime.minusHours(5);
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
