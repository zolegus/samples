package org.zolegus.samples;

public class SimpleBlockingQueueGroboUtilTest {

    private static class MyTestRunnable extends TestRunnable {
        private SimpleBlockingQueue<Integer> queue;

        public MyTestRunnable(SimpleBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void runTest() throws Throwable {
            for (int i = 0; i < 1000000; i++) {
                this.queue.put(42);
                this.queue.get();
            }
        }
    }

    @Test
    public void stressTest() throws Throwable {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        TestRunnable[] testRunnables = new TestRunnable[6];
        for (int i = 0; i < testRunnables.length; i++) {
            testRunnables[i] = new MyTestRunnable(queue);
        }
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(testRunnables);
        mttr.runTestRunnables(2 * 60 * 1000);
        assertThat(queue.getSize(), is(0));
    }
}