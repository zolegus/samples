package org.zolegus.samples;

import java.util.List;

public class Job {
    private final String filename;

    private Job(String filename) {
        this.filename = filename;
    }

    public static Job createAndStart(String filename) {
        Job job = new Job(filename);
        job.start();
        return job;
    }

    private void start() {
       // do something
    }

    public synchronized List getResults() {
        // do something
        return null;
    }
}