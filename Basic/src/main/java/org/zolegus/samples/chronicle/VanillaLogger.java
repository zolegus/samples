package org.zolegus.samples.chronicle;

import net.openhft.chronicle.Chronicle;
import net.openhft.chronicle.ExcerptTailer;
import net.openhft.chronicle.VanillaChronicle;
import net.openhft.chronicle.logger.ChronicleLogEvent;
import net.openhft.chronicle.logger.ChronicleLogHelper;
import net.openhft.chronicle.logger.ChronicleLogLevel;
import net.openhft.chronicle.logger.slf4j.ChronicleLogger;
import net.openhft.chronicle.logger.slf4j.ChronicleLoggerFactory;
import net.openhft.chronicle.logger.slf4j.ChronicleLoggingConfig;
import net.openhft.lang.io.IOTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author oleg.zherebkin
 */
public class VanillaLogger {

    private static Logger logger;

    public static void main(String[] args) throws IOException {
        System.setProperty("slf4j.chronicle.properties", VanillaLogger.class.getClassLoader().getResource("slf4j.chronicle.vanilla.properties").getPath());
        ChronicleLoggerFactory clf = (ChronicleLoggerFactory) StaticLoggerBinder.getSingleton().getLoggerFactory();


        clf.reload();
        logger = LoggerFactory.getLogger(VanillaLogger.class);
        if (!logger.getClass().equals(ChronicleLogger.class)) {
            System.out.println("[not equals]");
            return;
        }

        writeSimple();
//        readSimple();
//        readTracer();


        ((ChronicleLoggerFactory) StaticLoggerBinder.getSingleton().getLoggerFactory()).shutdown();
//        IOTools.deleteDir(basePath(ChronicleLoggingConfig.TYPE_VANILLA));
    }

    protected static String basePath(String type) {
        return System.getProperty("java.io.tmpdir")
                + File.separator
                + "chronology-slf4j"
                + File.separator
                + type
                + File.separator
                + new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    protected static String basePath(String type, String loggerName) {
        return basePath(type)
                + File.separator
                + loggerName;
    }

    protected static String indexedBasePath(String loggerName) {
        return basePath(ChronicleLoggingConfig.TYPE_INDEXED)
                + File.separator
                + loggerName;
    }

    protected static String vanillaBasePath(String loggerName) {
        return basePath(ChronicleLoggingConfig.TYPE_VANILLA)
                + File.separator
                + loggerName;
    }

    protected static final ChronicleLogLevel[] LOG_LEVELS = ChronicleLogLevel.values();

    protected static void log(Logger logger, ChronicleLogLevel level, String fmt, Object... args) {
        switch (level) {
            case TRACE:
                logger.trace(fmt, args);
                break;
            case DEBUG:
                logger.debug(fmt, args);
                break;
            case INFO:
                logger.info(fmt, args);
                break;
            case WARN:
                logger.warn(fmt, args);
                break;
            case ERROR:
                logger.error(fmt, args);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    protected static VanillaChronicle getVanillaChronicle(String type, String id) throws IOException {
        return new VanillaChronicle(basePath(type, id));
    }

    public static void writeBin() throws IOException {
        final String testId = "readwrite";
        final String threadId = testId + "-th";
        final long timestamp = System.currentTimeMillis();
        final Logger logger = LoggerFactory.getLogger(testId);

        IOTools.deleteDir(basePath(ChronicleLoggingConfig.TYPE_VANILLA, testId));
        Thread.currentThread().setName(threadId);

        for (ChronicleLogLevel level : LOG_LEVELS) {
            log(logger, level, "level is {}", level);
        }

        Chronicle chronicle = getVanillaChronicle(ChronicleLoggingConfig.TYPE_VANILLA, testId);
        ExcerptTailer tailer = chronicle.createTailer().toStart();
        ChronicleLogEvent evt = null;

        for (ChronicleLogLevel level : LOG_LEVELS) {
            if (level != ChronicleLogLevel.TRACE) {
                System.out.println(tailer.nextIndex());
                evt = ChronicleLogHelper.decodeBinary(tailer);
                System.out.println(evt);
                System.out.println(evt.getVersion());
                System.out.println(evt.getType());
                System.out.println(evt.getTimeStamp());
                System.out.println(evt.getLevel());
                System.out.println(evt.getThreadName());
                System.out.println(evt.getLoggerName());
                System.out.println(evt.getMessage());
                System.out.println(evt.getArgumentArray());
                System.out.println(evt.getArgumentArray().length);

                tailer.finish();
            }
        }

        logger.debug("Throwable test", new UnsupportedOperationException());
        logger.debug("Throwable test", new UnsupportedOperationException("Exception message"));

        System.out.println(tailer.nextIndex());
        evt = ChronicleLogHelper.decodeBinary(tailer);
        System.out.println(evt.getMessage());
        System.out.println(evt.getThrowable());
        System.out.println(evt.getThrowable() instanceof UnsupportedOperationException);
        System.out.println(evt.getThrowable().getMessage());

        System.out.println(tailer.nextIndex());
        evt = ChronicleLogHelper.decodeBinary(tailer);
        System.out.println(evt.getMessage());
        System.out.println(evt.getThrowable());
        System.out.println(evt.getThrowable() instanceof UnsupportedOperationException);
        System.out.println(evt.getThrowable().getMessage());

        tailer.close();
        chronicle.close();

//        IOTools.deleteDir(basePath(ChronicleLoggingConfig.TYPE_VANILLA,testId));
    }

    public static void writeSimple() {
        Logger logger = LoggerFactory.getLogger(VanillaLogger.class);
        for (int i = 0; i < 10000; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.warn("Let's logs something " + i);
        }

//        Logger text_logger = LoggerFactory.getLogger("text_logger");
//        for (int i = 0; i < 100000; i++)
//            text_logger.debug("Something major wrote here >> " + System.currentTimeMillis());

    }

    public static void readSimple() {
        Chronicle chronicle = new VanillaChronicle("./logs/main");
        ExcerptTailer tailer = null;
        try {
            tailer = chronicle.createTailer().toStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (tailer.nextIndex()) {

            ChronicleLogEvent evt = ChronicleLogHelper.decodeBinary(tailer);
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(evt.getTimeStamp()) + " [" + evt.getLevel().toString() + "] " + evt.getMessage());
        }
        System.out.println("Reading ended");
    }

    public static void readTracer() {
        Chronicle chronicle = new VanillaChronicle("./logs/text_logger");
        ExcerptTailer tailer = null;
        try {
            tailer = chronicle.createTailer().toStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (tailer.nextIndex()) {
//            ChronicleLogEvent evt = ChronicleLogHelper.decodeBinary(tailer);
            ChronicleLogEvent evt = ChronicleLogHelper.decodeText(tailer);
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(evt.getTimeStamp()) + " [" + evt.getLevel().toString() + "] " + evt.getMessage());
        }
        System.out.println("Reading ended");
    }
}
