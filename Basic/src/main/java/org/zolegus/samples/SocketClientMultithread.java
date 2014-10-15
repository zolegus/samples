package org.zolegus.samples;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

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
                        in = inputStream.read();
                    }
//                    outputStream.writeLong(System.nanoTime());
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

    public static void main(String[] args) throws IOException, InterruptedException {

        Thread serverThread = new Thread(new SocketClientMultithread(), "ServerThread");
        serverThread.start();

        SimpleClientAPI client = new SimpleClientAPI(InetAddress.getLocalHost(), port);
        Thread clientThread = new Thread(client, "ClientThread");
        clientThread.start();

        char msg = ' ';
        while(msg != 'x') {
            msg = (char)System.in.read();
            client.sendToServer(msg);
        }

        client.disconnect();
        clientThread.interrupt();
        clientThread.join();
        isServerStopped = true;
        serverThread.join();

    }

}
