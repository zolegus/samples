package org.zolegus.samples.socket;

import java.io.*;
import java.net.*;

public class SocketClientMultithread implements Runnable{

    public static int port = 11111;
    public static boolean isServerStopped = false;

    @Override
    public void run() {
        final ServerSocket server;
        try {
            server = new ServerSocket(port);
            while (!isServerStopped) {
                final Socket clientSocket = server.accept();
                clientSocket.setTcpNoDelay(true);
                DataOutputStream outputStream;
                InputStream inputStream;
                try {
                    inputStream = clientSocket.getInputStream();
                    outputStream = new DataOutputStream(clientSocket.getOutputStream());
                    int in = inputStream.read();
                    while (in != -1) {
                        if (in=='?')
                            outputStream.write("WTF!".getBytes());
                        if (in=='q') {
                            isServerStopped = true;
                            break;
                        }
                        in = inputStream.read();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws Exception {

        Thread serverThread = new Thread(new SocketClientMultithread(), "ServerThread");
        serverThread.start();

//        SimpleClientSocketAPI client = new SimpleClientSocketAPI(InetAddress.getLocalHost(), port);
        SimpleClientNIOAPI client = new SimpleClientNIOAPI(new InetSocketAddress("localhost", port));

        Thread clientThread = new Thread(client, "ClientThread");
        clientThread.start();


        char msg = ' ';
        while(msg != 'x') {
            msg = (char)System.in.read();
            client.sendToServer(msg);
        }
        client.disconnect();
        clientThread.join();
        serverThread.join();
    }

}
