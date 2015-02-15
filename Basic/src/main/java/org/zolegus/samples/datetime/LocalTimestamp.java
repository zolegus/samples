package org.zolegus.samples.datetime;

import javax.crypto.Cipher;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author oleg.zherebkin
 */
public class LocalTimestamp {
    public static void main(String[] args) throws InterruptedException {
        TimeZone SysLocal = TimeZone.getDefault();
        TimeZone GMT = TimeZone.getTimeZone("GMT");
        TimeZone Chicago = TimeZone.getTimeZone("America/Chicago");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        for (int i = 0; i < 100; i++) {
            long timestamp = System.currentTimeMillis();
            System.out.println("System:" + timestamp + ", UTC:" + Clock.systemUTC().millis());
            format.setTimeZone(SysLocal);
            System.out.println("Timezone " + format.getTimeZone().getDisplayName() + ": " + format.format(timestamp).toString());
            format.setTimeZone(GMT);
            System.out.println("Timezone " + format.getTimeZone().getDisplayName() + ": " + format.format(timestamp).toString());
            format.setTimeZone(Chicago);
            System.out.println("Timezone " + format.getTimeZone().getDisplayName() + ": " + format.format(timestamp).toString());
            LocalDateTime chicagoDT = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), Chicago.toZoneId());
            System.out.println("LDT Chicago: " + chicagoDT.toString());
            //Reverse
            LocalDateTime localDT = LocalDateTime.now();
            System.out.println(timestamp + " = " + chicagoDT.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli());
            System.out.println(timestamp + " = " + localDT.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            Thread.sleep(1000);


        }
    }
}
