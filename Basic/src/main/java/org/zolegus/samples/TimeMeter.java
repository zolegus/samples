package org.zolegus.samples;

public class TimeMeter {

    public static void main(String[] args) throws InterruptedException {
        int counter = 0;
        int runs = 10000;
        long start = System.nanoTime();
        for (int i = 0; i < runs; i++) {
            System.out.println("output message text");
            counter++;
        }
        long time = System.nanoTime() - start;
        System.out.printf("Average parse time was %.2f us, fields per message %.2f%n", time / runs / 1e3, (double) counter / runs);

    }
}
