package org.zolegus.samples.chronicle;

import net.openhft.chronicle.Chronicle;
import net.openhft.chronicle.ChronicleQueueBuilder;
import net.openhft.chronicle.ExcerptAppender;
import net.openhft.chronicle.VanillaChronicle;

import java.io.IOException;

/**
 * @author oleg.zherebkin
 */
public class AppenderGCTest {
    public static void main(String[] args) throws IOException {
        String baseDir = "./logs";
//        VanillaChronicle chronicle = new VanillaChronicle(baseDir);
        Chronicle chronicle = ChronicleQueueBuilder.VanillaChronicleQueueBuilder.vanilla(baseDir).build();
        while(true) {
            ExcerptAppender appender = chronicle.createAppender();
            appender.startExcerpt();
            appender.finish();
        }
    }
}
