package org.zolegus.samples.process;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author oleg.zherebkin
 */
public class NewProcess {
    public static void main(String[] args) throws IOException {
        String path = "/home/zolegus/dev/java/codes/zolegus.com/quote-collector/build/install/quote-collector/bin";
        String program = "./quote-collector";
        Process proc = Runtime.getRuntime().exec(program, null, new File(path));
        try {
            Field f = proc.getClass().getDeclaredField("pid");
            f.setAccessible(true);
            int pid = f.getInt(proc);
            System.out.println("Started pid=" + pid);
        } catch (Throwable e) {
        }
//        Runtime rt = Runtime.getRuntime();
//        String[] commands = {"system.exe","-get t"};
//        Process proc = rt.exec(commands);

/*        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }*/

    }

}
