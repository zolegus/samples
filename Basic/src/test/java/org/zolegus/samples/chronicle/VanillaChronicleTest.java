package org.zolegus.samples.chronicle;

import junit.framework.TestCase;
import net.openhft.chronicle.Chronicle;
import net.openhft.chronicle.ChronicleQueueBuilder;
import net.openhft.chronicle.ExcerptAppender;
import net.openhft.chronicle.tools.ChronicleTools;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VanillaChronicleTest extends TestCase {

    Chronicle chronicle;

    @Before
    public void setUp() throws Exception {
//        String baseDir = System.getProperty("java.io.tmpdir") + "/performance-logger";
        String baseDir = "./logs";
        ChronicleTools.deleteDirOnExit(baseDir);
//        chronicle = new VanillaChronicle(baseDir);
        chronicle = ChronicleQueueBuilder.VanillaChronicleQueueBuilder.vanilla(baseDir).build();
//        chronicle = new IndexedChronicle(baseDir);
    }

    @After
    public void tearDown() throws Exception {
        chronicle.close();
    }

    @Test
    public void testMultiThreadLogging() throws IOException, InterruptedException {
        final int RUNS = 10000000;
//        final int THREADS = Runtime.getRuntime().availableProcessors();
        final int THREADS = 1;
        double average = 0.0;
        double averageTotal = 0.0;
        int count = 0;
        for (int size : new int[]{64, 128, 256, 512, 1024, 2048, 4096}) {
            final long start = System.nanoTime();
            ExecutorService es = Executors.newFixedThreadPool(THREADS);
            for (int t = 0; t < THREADS; t++) {
                es.submit(new RunnableLogger(RUNS, size));
            }
            es.shutdown();
            es.awaitTermination(2, TimeUnit.MINUTES);
            final long time = System.nanoTime() - start;
            average = time / 1e3 / (RUNS * THREADS);
            averageTotal += average;
            System.out.printf("Perf test (runs=%d, min size=%03d, elapsed=%.3f ms) took an average of %.3f us per entry\n",
                    RUNS,
                    size,
                    time / 1e6,
                    average
            );
            Thread.sleep(500);
            count++;
        }
        System.out.printf("Total average time is %.3f us per entry\n", averageTotal / count);
    }

    protected final class RunnableLogger implements Runnable {
        final ThreadLocal<String> THREAD_NAME = new ThreadLocal<String>();
        private final int runs;

        public RunnableLogger(int runs, int pad) throws IOException {
            this.runs = runs;
        }

        @Override
        public void run() {
            try {
                String message = "Test";// message 1234567890 LongLongLongLongLongLongLongLongLongLongLongLong";
                String message10l = "TestTestTestTestTestTestTestTestTestTest";
                String message20l = "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest";
                byte[] barray = message.getBytes();
                ExcerptAppender appender = chronicle.createAppender();
                for (int i = 0; i < this.runs; i++) {
                    appender.startExcerpt();
                    appender.writeLong(666l);
                    appender.writeInt(20);
                    appender.writeChar('M');
//                    appender.append("worker");
//                    appender.append(message);
//                    appender.append(777l);
//                    appender.writeLong(888l);
//                    appender.write(message.getBytes());
//                    appender.write(message.getBytes());
//                    appender.writeUTF(message);
//                    appender.writeUTFΔ(message);
//                    appender.write(message.getBytes());
                    appender.finish();
                }
                System.out.println("Thread RunnableLogger finished!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String getThreadName() {
            String name = THREAD_NAME.get();
            if (name == null)
                THREAD_NAME.set(name = Thread.currentThread().getName());
            return name;
        }
    }
}