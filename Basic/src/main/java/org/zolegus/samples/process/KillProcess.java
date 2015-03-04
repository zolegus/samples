package org.zolegus.samples.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author oleg.zherebkin
 */
public class KillProcess {
    public static void main(String[] args) {
        try {
            String line;
            Process p = Runtime.getRuntime().exec("ps -fe");
            System.out.println("Result = " + p.isAlive());
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (line.contains("quote-collector")) {
                    System.out.println(line);
                    String pid = line.split("  ")[1];
                    Runtime.getRuntime().exec("kill " + pid);
                    Thread.sleep(100);
                    System.out.printf("Process %s killed successful%n", pid);
                }
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
