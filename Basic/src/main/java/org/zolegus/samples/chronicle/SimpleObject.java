package org.zolegus.samples.chronicle;

import net.openhft.affinity.AffinityStrategies;
import net.openhft.chronicle.Chronicle;
import net.openhft.chronicle.ExcerptAppender;
import net.openhft.chronicle.ExcerptTailer;
import net.openhft.chronicle.IndexedChronicle;
import net.openhft.chronicle.tools.ChronicleTools;

import java.io.IOException;
import java.util.Random;

/**
 * @author oleg.zherebkin
 */
public class SimpleObject {

    public static void main(String[] args) throws IOException {
        String basePath = System.getProperty("java.io.tmpdir") + "/SimpleChronicle";
        ChronicleTools.deleteOnExit(basePath);

        IndexedChronicle chronicle = new IndexedChronicle(basePath);
        ExcerptAppender appender = chronicle.createAppender();
        // write one object
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    for (int i = 1; i <= 100000000; i++) {
                        appender.startExcerpt(); // an upper limit to how much space in bytes this message should need.
                        appender.writeObject(i);
                        appender.writeObject(i);
                        appender.writeObject(i);
                        appender.writeObject(i);
                        appender.writeObject(i);
                        appender.finish();
                    }
                } catch (Exception e) {
                    throw new AssertionError(e);
                }
                finally {
                    System.out.println("Writer finished!");
                }
            }
        });

        // read one object
        ExcerptTailer reader = chronicle.createTailer();
//        reader.index(0);
        reader.toStart();
        boolean startT = false;
        boolean stop = false;
        int i = 0;
        Object ret;
        while(!stop) {
            while (reader.nextIndex()) {
                ret = reader.readObject();
                ret = reader.readObject();
                ret = reader.readObject();
                ret = reader.readObject();
                ret = reader.readObject();
                reader.finish();
//                System.out.println(ret);
                if (++i == 100000000) {
                    stop = true;
                    break;
                }
            }

            if (!startT) {
                System.out.println("Start writer!");
                thread.start();
                startT = true;
            }



        }
//        reader.free();
        System.out.println("The end!");
    }
}
