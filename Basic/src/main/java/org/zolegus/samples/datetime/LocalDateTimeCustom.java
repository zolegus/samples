package org.zolegus.samples.datetime;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @author oleg.zherebkin
 */
public class LocalDateTimeCustom {
    public static void main(String[] args) throws Exception {
        StringBuilder stringBuilder = new StringBuilder(1024);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss.SSS");
        LocalDateTime ldt = LocalDateTime.now();
        int tests = 100000000;
        long[] times = new long[tests];
        long start;
        int count= 0;
        int tmp = 0;
        for (int i = 0; i < tests; i++) {
            start = System.nanoTime();
            ldt = LocalDateTime.now(Clock.systemUTC());
            stringBuilder.append(ldt.getYear());
            tmp = ldt.getMonthValue();
            if (tmp < 10) stringBuilder.append('0');
            stringBuilder.append(tmp);
            tmp = ldt.getDayOfMonth();
            if (tmp < 10) stringBuilder.append('0');
            stringBuilder.append(tmp);
            stringBuilder.append('-');
            tmp = ldt.getHour();
            if (tmp < 10) stringBuilder.append('0');
            stringBuilder.append(tmp);
            stringBuilder.append(':');
            tmp = ldt.getMinute();
            if (tmp < 10) stringBuilder.append('0');
            stringBuilder.append(tmp);
            stringBuilder.append(':');
            tmp = ldt.getSecond();
            if (tmp < 10) stringBuilder.append('0');
            stringBuilder.append(tmp);
            stringBuilder.append('.');
            tmp = (int) (ldt.getNano() / 1e6);
            if (tmp < 10) stringBuilder.append('0');
            if (tmp < 100) stringBuilder.append('0');
            stringBuilder.append(tmp);
            times[count++] = System.nanoTime() - start;

//            if (!ldt.format(dtf).equals(stringBuilder.toString())) {
//                System.out.println(stringBuilder.toString());
//                throw new Exception(new StringBuilder("Datetime format is not correct. Must be ").append(ldt.format(dtf)).toString());
//            }
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
