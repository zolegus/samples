package org.zolegus.samples.datetime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.TimeZone;

/**
 * @author oleg.zherebkin
 */
public class TimestampFromLDTPerf {
    public static void main(String[] args) throws InterruptedException {
        StringBuilder stringBuilder = new StringBuilder(1024 * 32);
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss.SSS");
        TimeZone Chicago = TimeZone.getTimeZone("America/Chicago");
        ZoneId zoneid = Chicago.toZoneId();
        LocalDateTime ldt;
        long timestamp;
        int run = 5;
        for (int n = 0; n < 5; n++) {
            int tests = 1000000;
            long[] times = new long[tests];
            long start;
            int count = 0;
            for (int i = 0; i < tests; i++) {
                ldt = LocalDateTime.now();
                start = System.nanoTime();
                timestamp = ldt.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
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
                    times[times.length - 1] / 1e3);
        }
    }
}
