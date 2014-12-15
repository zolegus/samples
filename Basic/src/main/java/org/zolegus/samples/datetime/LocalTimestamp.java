package org.zolegus.samples.datetime;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * @author oleg.zherebkin
 */
public class LocalTimestamp {
    public static void main(String[] args) throws InterruptedException {
        TimeZone GMT = TimeZone.getTimeZone("GMT");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        format.setTimeZone(GMT);
        for (int i = 0; i < 100; i++) {
            System.out.println(format.format(System.currentTimeMillis()) + " " + LocalDateTime.now().toString());
            Thread.sleep(1000);
        }
    }
}
