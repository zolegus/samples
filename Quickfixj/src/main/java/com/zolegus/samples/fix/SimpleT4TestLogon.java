package com.zolegus.samples.fix;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SimpleT4TestLogon {

    public static int calculateChecksum(String message) {
        char[] aMessage = message.toCharArray();
        int summ = 0;
        for(int i=0; i < aMessage.length; i++)
            summ+=aMessage[i];
        return (summ%256);
    }

    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss.SSS");
        /*
        Simulator               fix-sim.t4login.com 10443  IP = 74.201.6.199 || 69.44.110.200
        Simulator Chart Data    fix-chart-sim.t4login.com 10443  IP = 74.201.6.229
        Live                    fix.t4login.com 10443   IP = 74.201.6.106 || 208.116.215.102 || 69.44.110.106
        Live Chart Data         fix-chart.t4login.com 10443 IP =  69.44.110.227 || 208.116.215.116

        [FIXLOGIN]
        [MsgSeqNum] 34 = 1
        [SenderCompID] 49 = T4Example
        [TargetCompID] 56 = T4
        [SendingTime] 52 = 20130607-14:47:22.872
        [SecureData] 91 = 112A04B0-5AAF-42F4-994E-FA7CB959C60B
        [SecureDataLen] 90 = 36
        [EncryptMethod] 98 = 0
        [HeartBtInt] 108 = 30
        [UserName] 553 = _username_
        [Password] 554 = _password_
        [NoMsgTypes] 384 = 1
        [RefMsgType] 372 = d (DISABLE_PORTFOLIO_LISTING)
         */
        char soh = 001;
        String messageBody = "91=112A04B0-5AAF-42F4-994E-FA7CB959C60B" + soh +
                                "90=36" + soh +
                                "98=0" + soh +
                                "108=1" + soh +
                                "553=username" + soh +
                                "554=password" + soh +
                                "384=1" + soh +
                                "372=d" + soh;
        String standardHeader = "8=FIX.4.2" + soh +
                                "9=" + messageBody.length() + soh +
                                "35=A" + soh +
                                "34=1" + soh +
                                "52=" + LocalDateTime.now(Clock.systemUTC()).format(dtf) + soh +
                                "49=T4Example" + soh +
                                "56=CTS" + soh;
        String standardTrailer = "10=" + calculateChecksum(standardHeader + messageBody) + soh;

        String fullMessage = standardHeader + messageBody + standardTrailer;
        System.out.println(fullMessage);
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = null;
        try {
            socket = (SSLSocket)factory.createSocket("74.201.6.199", 10443);
            socket.setSoTimeout(15000);
            OutputStream out = socket.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "ASCII");
            writer.write(fullMessage);
            writer.flush();
            InputStream in = socket.getInputStream();
            StringBuilder string = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(in, "ASCII");

            for (int c = reader.read(); c != -1; c = reader.read()) {
                string.append((char) c);
                System.out.println(string);
            }

            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    // ignore
                }
            }
        }
    }
}
