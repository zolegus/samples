package com.zolegus.samples.quickfixj;

import quickfix.*;
import java.io.FileInputStream;

/**
 * Created by zolegus on 19.09.2014.
 */
public class Sample {

    public static void main(String args[]) throws Exception {
        if (args.length != 1) return;
        String fileName = args[0];

        // FooApplication is your class that implements the Application interface
        Application application = new FooApplication();

        SessionSettings settings = new SessionSettings(new FileInputStream(fileName));
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory();
        Acceptor acceptor = new SocketAcceptor
                (application, storeFactory, settings, logFactory, messageFactory);
        acceptor.start();
        // while( condition == true ) { do something; }
        acceptor.stop();
    }
}

