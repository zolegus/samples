package org.zolegus.samples.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.channels.ClosedByInterruptException;

public class SimpleClientSocketAPI implements Runnable {

    private Socket socket;
    private boolean isConnected = false;
    public boolean isStopped = false;
    private InputStream inputStream;
    private OutputStream outputStream;

    public SimpleClientSocketAPI(InetAddress address, int port) {
        try {
            socket = new Socket(address, port);
            socket.setTcpNoDelay(true);
            socket.setSoTimeout(5000);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            isConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        if (!isConnected)
            return;
        try {
            int in = 0;
            StringBuilder string = new StringBuilder();
            while (!isStopped) {
                try {
                    in = inputStream.read();
                    if (in == -1) {
                        System.out.println("client input stream there is nothing to read");
                        break;
                    }
                    string.append((char) in);
                    if (in == '!') {
                        System.out.println(string);
                        string = new StringBuilder();
                    }
                } catch (SocketTimeoutException e) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToServer(int message) {
        if (!isConnected)
            return;
        try {
            this.outputStream.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        isStopped = true;
    }
}
