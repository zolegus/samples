package org.zolegus.samples.datetime;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author oleg.zherebkin
 */
public class ConvertTimestamp {
    public static void main(String[] args) {
        String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);

        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        String timestampString = new SimpleDateFormat(pattern).format(timestamp).toString();
        System.out.println(timestampString);
        LocalDateTime dateTime = LocalDateTime.parse(timestampString, dtf);
        System.out.println(dateTime.toString());
        System.out.println(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
