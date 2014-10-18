package com.zolegus.samples.quickfixj;

import quickfix.*;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by zolegus on 19.09.2014.
 */
public class Sample {

    public static void main(String args[]) {
        try {
            // FooApplication is your class that implements the Application interface
            Application application = new FooApplication();

            InputStream inputStream = null;
            if (args.length == 0) {
                inputStream = Sample.class.getResourceAsStream("sample.cfg");
            } else if (args.length == 1) {
                inputStream = new FileInputStream(args[0]);
            }
            if (inputStream == null) {
                System.out.println("usage: " + Sample.class.getName() + " [configFile].");
                return;
            }
            SessionSettings settings = new SessionSettings(inputStream);
            inputStream.close();

//            SessionSettings settings = new SessionSettings(new FileInputStream("sample.cfg"));
            MessageStoreFactory storeFactory = new FileStoreFactory(settings);
            LogFactory logFactory = new FileLogFactory(settings);
            MessageFactory messageFactory = new DefaultMessageFactory();
            SocketInitiator si = new SocketInitiator(application, storeFactory, settings, logFactory, messageFactory);
            si.start();
            while (true) {
            }
            // while( condition == true ) { do something; }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

