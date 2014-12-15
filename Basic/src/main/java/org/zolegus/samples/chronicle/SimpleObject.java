package org.zolegus.samples.chronicle;

import net.openhft.chronicle.*;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author oleg.zherebkin
 */
public class SimpleObject {

    public final static int RUN = 5000000;
    public static void main(String[] args) throws IOException, InterruptedException {
        String basePath = "./logs/simpleobject";
//        ChronicleTools.deleteOnExit(basePath);
//        IndexedChronicle chronicle = new IndexedChronicle(basePath);
        VanillaChronicleConfig config = new VanillaChronicleConfig();
        config.cycleFormat("yyyyMMdd_HHmmss");
        config.cycleLength(43200000,false);
        VanillaChronicle chronicle = new VanillaChronicle(basePath, config);


        // write one object
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("The writer started");
                    String message = "Thread1 message ";
                    final long start = System.nanoTime();
                    for (int i = 1; i <= RUN; i++) {
//                        Thread.sleep(1000);
                        ExcerptAppender appender = chronicle.createAppender();
                        appender.startExcerpt(); // an upper limit to how much space in bytes this message should need.
                        appender.writeLong(System.currentTimeMillis());
                        appender.writeUTF(message);
                        appender.writeUTF(message);
                        appender.writeUTF(message);
                        appender.writeInt(i);
                        appender.finish();
                    }
                    final long time = System.nanoTime() - start;
                    System.out.printf("Thread1 (runs=%d, min size=%03d, elapsed=%.3f ms) took an average of %.3f us per entry\n",
                            RUN,
                            1,
                            time / 1e6,
                            time / 1e3 / (RUN)
                    );
                } catch (Exception e) {
                    throw new AssertionError(e);
                }
                finally {
                    System.out.println("Writer finished");
                }
            }
        });

        // write one object
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ExcerptAppender appender = chronicle.createAppender();
                    System.out.println("The writer started");
                    String message = "Thread2 message ";
                    final long start = System.nanoTime();
                    for (int i = 1; i <= RUN; i++) {
//                        Thread.sleep(1000);
                        appender.startExcerpt(); // an upper limit to how much space in bytes this message should need.
                        appender.writeLong(System.currentTimeMillis());
                        appender.writeUTF(message);
                        appender.writeInt(i);
                        appender.finish();
                    }
                    final long time = System.nanoTime() - start;
                    System.out.printf("Thread2 (runs=%d, min size=%03d, elapsed=%.3f ms) took an average of %.3f us per entry\n",
                            RUN,
                            1,
                            time / 1e6,
                            time / 1e3 / (RUN)
                    );
                } catch (Exception e) {
                    throw new AssertionError(e);
                }
                finally {
                    System.out.println("Writer finished");
                }
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        // read one object
        ExcerptTailer reader = chronicle.createTailer();
        reader.toStart();

//        while (reader.nextIndex()) {
//            if (true) {
//                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(reader.readLong()) + " " + reader.readUTF() + reader.readInt());
////                System.out.println(reader.readUTF());
//                reader.finish();
//            }
//        }

//        reader.free();
//        long time = System.nanoTime() - start;
//        System.out.printf("Average read time was %.2f us %n", time / count / 1e3);
//        System.out.println("The end. There was read " + count);
//        thread.join();
    }
}
