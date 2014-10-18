package com.zolegus.samples.quickfixj;

import quickfix.*;
import quickfix.fix42.MarketDataRequest;
import quickfix.fix42.NewOrderSingle;
import quickfix.fix42.OrderCancelRequest;

public class FooApplication extends MessageCracker implements Application {

    public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        System.out.println("fromAdmin");
    }

    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound,IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        crack(message, sessionId);
    }

    public void onMessage(NewOrderSingle message, SessionID sessionID) {
        System.out.println("onMessage");
    }

    public void onMessage(OrderCancelRequest message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        System.out.println("onMessage");
    }

    public void onMessage(MarketDataRequest message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        System.out.println("onMessage");
    }

    public void onCreate(SessionID sessionId) {
        System.out.println("onCreate");
    }

    public void onLogon(SessionID sessionId) {
        System.out.println("Logon - " + sessionId);
    }

    public void onLogout(SessionID sessionId) {
        System.out.println("Logout - " + sessionId);
    }

    public void toAdmin(Message message, SessionID sessionId) {
        System.out.println("toAdmin");
        // empty
    }

    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
        System.out.println("toApp");
        // empty
    }

}
