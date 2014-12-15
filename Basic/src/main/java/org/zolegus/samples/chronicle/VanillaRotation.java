package org.zolegus.samples.chronicle;

import net.openhft.chronicle.ExcerptAppender;
import net.openhft.chronicle.ExcerptTailer;
import net.openhft.chronicle.VanillaChronicle;
import net.openhft.chronicle.VanillaChronicleConfig;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author oleg.zherebkin
 */
public class VanillaRotation {

    static public int emptyFolder(File folder, boolean ignoreCannotDel) throws IOException {
        int counter = 0;
        if (folder.exists() && folder.isDirectory()) {
            File[] child = folder.listFiles();
            for (int i = 0; i < child.length; i++) {
                File file = child[i];
                if (file.isDirectory()) counter += emptyFolder(file, ignoreCannotDel);
                boolean result = file.delete();
                if (!result && !ignoreCannotDel) {
                    if(!ignoreCannotDel) {
                        throw new IOException("Cannot delete " + file.getAbsolutePath());
                    } else {
                        file.deleteOnExit() ;
                    }
                } else {
                    counter++;
                }
            }
        }
        return counter;
    }

    static public int remove(File file, boolean ignoreCannotDelete) throws Exception {
        int counter = 0;
        if (file.exists()) {
            if (file.isDirectory()) {
                counter += emptyFolder(file, ignoreCannotDelete);
            }
            boolean result = file.delete();
            if (!result && !ignoreCannotDelete) {
                throw new Exception("Cannot delete " + file.getAbsolutePath());
            } else {
                counter++;
            }
        }
        return counter;
    }

    public static void main(String[] args) throws Exception {
        final String baseDir = "build/vanilla";
        File file = new File(baseDir);
        if (file.exists())
            remove(file, false);

// Create with small data and index sizes so that the test frequently
// generates new files
        VanillaChronicleConfig config = new VanillaChronicleConfig();
//        config.cycleFormat("yyyy-MM-dd-HH:mm:ss");
//        config.cycleLength(60 * 1000, false);
//        config.entriesPerCycle(512 * 1024);
//        config.dataBlockSize(4 * 1024 * 1024);
//        config.indexBlockSize(1 * 1024 * 1024);

            config.cycleFormat("yyyyMMdd_HHmmss");
//        config.cycleLength(1000, false);
//        config.entriesPerCycle(1L << 16);
//        config.indexBlockSize(16L << 10);

        final VanillaChronicle chronicle = new VanillaChronicle(baseDir, config);
        chronicle.clear();
        try {
            ExcerptAppender appender = chronicle.createAppender();
            long start = System.currentTimeMillis() ;
            for(int j = 0; j < 4600000; j++) {
                appender.startExcerpt();
                appender.writeInt(j + 1);
                appender.finish();
            }
            long exec = System.currentTimeMillis() - start ;
            System.out.println("Append in " + exec + "ms");
            for(int i = 0; i < 5; i++) {
                long sum = 0 ;
                AtomicInteger count = new AtomicInteger() ;
                ExcerptTailer tailer = chronicle.createTailer();
                int lastValue = 0 ;
                while(tailer.nextIndex()) {
                    count.incrementAndGet() ;
                    lastValue = tailer.readInt();
                    sum += lastValue;
                    tailer.finish();
                }
//              tailer.flush() ;
                tailer.close();
                System.out.println("files = " +tailer.file()) ;
                System.out.println(
                        "count = " + count.get() + ", sum = " + sum + ", last value = " + lastValue + ", last index = " + chronicle.lastIndex() +
                                ", was padding = " + tailer.wasPadding() + ", nextIndex = " + tailer.nextIndex() +
                                ", finished = " + tailer.isFinished());
            }
            appender.close();
            chronicle.checkCounts(1, 1);
        } finally {
            chronicle.close();
        }
    }
}
