package org.zolegus.samples.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SimpleClientNIOAPI implements Runnable{

    private SocketChannel socketChannel = null;
    private ByteBuffer in = ByteBuffer.allocate(64);
    private ByteBuffer out = ByteBuffer.allocate(64);
    private boolean isConnected = false;

    public SimpleClientNIOAPI(SocketAddress address) {

        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(address);
            while( !socketChannel.finishConnect() ){}
            isConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void disconnect() {
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (!isConnected)
            return;
        try {
            int bytesRead = socketChannel.read(in);
            while (bytesRead != -1) {
                in.flip();
                while (in.remaining() > 0)
                    System.out.print((char) in.get());

                in.clear();
                bytesRead = socketChannel.read(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client connection closed.");
        isConnected = false;
    }


    public void sendToServer(char message) throws Exception {
        if (!isConnected)
            throw new Exception("Socket channel is not connected");
        out.clear();
        out.put((byte)message);
        out.flip();
        while(out.hasRemaining()) {
            socketChannel.write(out);
        }
    }

}
