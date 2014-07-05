package org.zolegus.samples;

/**
 * Created by zolegus on 7/5/14.
 */
private static class BlockingThread extends Thread {
    private SimpleBlockingQueue queue;
    private boolean wasInterrupted = false;
    private boolean reachedAfterGet = false;
    private boolean throwableThrown;

    public BlockingThread(SimpleBlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            try {
                queue.get();
            } catch (InterruptedException e) {
                wasInterrupted = true;
            }
            reachedAfterGet = true;
        } catch (Throwable t) {
            throwableThrown = true;
        }
    }

    public boolean isWasInterrupted() {
        return wasInterrupted;
    }

    public boolean isReachedAfterGet() {
        return reachedAfterGet;
    }

    public boolean isThrowableThrown() {
        return throwableThrown;
    }
}