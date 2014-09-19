package com.zolegus.samples.quickfixj;

import java.util.ArrayList;

import quickfix.*;
import quickfix.field.AvgPx;
import quickfix.field.ClOrdID;
import quickfix.field.CumQty;
import quickfix.field.ExecID;
import quickfix.field.ExecTransType;
import quickfix.field.ExecType;
import quickfix.field.LastPx;
import quickfix.field.LastShares;
import quickfix.field.LeavesQty;
import quickfix.field.NoRelatedSym;
import quickfix.field.OrdStatus;
import quickfix.field.OrdType;
import quickfix.field.OrderID;
import quickfix.field.OrderQty;
import quickfix.field.OrigClOrdID;
import quickfix.field.Price;
import quickfix.field.SenderCompID;
import quickfix.field.Side;
import quickfix.field.SubscriptionRequestType;
import quickfix.field.Symbol;
import quickfix.field.TargetCompID;
import quickfix.field.Text;
import quickfix.field.TimeInForce;
import quickfix.fix42.ExecutionReport;
import quickfix.fix42.MarketDataRequest;
import quickfix.fix42.NewOrderSingle;
import quickfix.fix42.OrderCancelRequest;

public class FooApplication extends MessageCracker implements Application {

    public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound,
            IncorrectDataFormat, IncorrectTagValue, RejectLogon {
    }

    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound,
            IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        crack(message, sessionId);
    }

    public void onMessage(NewOrderSingle message, SessionID sessionID) {
    }

    private void rejectOrder(String senderCompId, String targetCompId, String clOrdId,
            String symbol, char side, String message) {
    }


    public void onMessage(OrderCancelRequest message, SessionID sessionID) throws FieldNotFound,
            UnsupportedMessageType, IncorrectTagValue {
    }

    public void onMessage(MarketDataRequest message, SessionID sessionID) throws FieldNotFound,
            UnsupportedMessageType, IncorrectTagValue {
        MarketDataRequest.NoRelatedSym noRelatedSyms = new MarketDataRequest.NoRelatedSym();

        //String mdReqId = message.getString(MDReqID.FIELD);
        char subscriptionRequestType = message.getChar(SubscriptionRequestType.FIELD);

        if (subscriptionRequestType != SubscriptionRequestType.SNAPSHOT)
            throw new IncorrectTagValue(SubscriptionRequestType.FIELD);
        //int marketDepth = message.getInt(MarketDepth.FIELD);
        int relatedSymbolCount = message.getInt(NoRelatedSym.FIELD);

        for (int i = 1; i <= relatedSymbolCount; ++i) {
            message.getGroup(i, noRelatedSyms);
            String symbol = noRelatedSyms.getString(Symbol.FIELD);
            System.err.println("*** market data: " + symbol);
        }
    }

    public void onCreate(SessionID sessionId) {
    }

    public void onLogon(SessionID sessionId) {
        System.out.println("Logon - " + sessionId);
    }

    public void onLogout(SessionID sessionId) {
        System.out.println("Logout - " + sessionId);
    }

    public void toAdmin(Message message, SessionID sessionId) {
        // empty
    }

    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
        // empty
    }

}
