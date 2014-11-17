package org.zolegus.samples.chronicle;

import net.openhft.affinity.AffinityStrategies;
import net.openhft.chronicle.Chronicle;
import net.openhft.chronicle.ExcerptAppender;
import net.openhft.chronicle.ExcerptTailer;
import net.openhft.chronicle.IndexedChronicle;
import net.openhft.chronicle.tools.ChronicleTools;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author oleg.zherebkin
 */
public class SimpleObject {

    public static void main(String[] args) throws IOException, InterruptedException {
        String basePath = System.getProperty("java.io.tmpdir") + "/data";
        //ChronicleTools.deleteOnExit(basePath);

        IndexedChronicle chronicle = new IndexedChronicle(basePath);
        ExcerptAppender appender = chronicle.createAppender();


        // write one object
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int count = 10000000;
                    appender.toEnd();
                    System.out.println("The writer started");
                    for (int i = 1; i <= count; i++) {
                        appender.startExcerpt(); // an upper limit to how much space in bytes this message should need.
                        appender.writeLong(System.currentTimeMillis());
                        appender.finish();
                    }
                } catch (Exception e) {
                    throw new AssertionError(e);
                }
                finally {
                    System.out.println("Writer finished");
                }
            }
        });


//        thread.start();

        // read one object
        ExcerptTailer reader = chronicle.createTailer();
//        reader.index(0);
        reader.toStart();
        boolean startT = false;
        boolean stop = false;
        int count = 0;
        long ret;
//        while(!stop) {
        long start = System.nanoTime();
            while (reader.nextIndex()) {
                ret = reader.readLong();
                reader.finish();
                count++;
//                System.out.println(ret);
//                if ( == 1000) {
//                    stop = true;
//                    break;
                }
//            }
//        }

//        reader.free();
        long time = System.nanoTime() - start;
        System.out.printf("Average read time was %.2f us %n", time / count / 1e3);
        System.out.println("The end. There was read " + count);
//        thread.join();
    }
}
