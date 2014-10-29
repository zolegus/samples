package org.zolegus.samples.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by zolegus on 10.10.2014.
 */
public class SocketClientNIO implements Runnable{
    public static final int LOOP_COUNT = 1000;

    @Override
    public void run() {
        final ServerSocket server;
        try {
            server = new ServerSocket(11111);
//            while () {
            final Socket serverSocket = server.accept();
            serverSocket.setTcpNoDelay(true);
            long counter = 0;
            DataOutputStream serverOut;
            try {
                serverOut = new DataOutputStream(serverSocket.getOutputStream());
                for (int i = 0; i < LOOP_COUNT; i++) {
                    serverOut.writeLong(System.nanoTime());
                    serverOut.writeLong(counter);
                    counter++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }

    private static final ByteBuffer internalBufferBB = ByteBuffer.allocate(8);

    public static void process(int size, ByteBuffer buf){
        //if buffer is big enough to contain both values, get them and process them
        //else do ByteBuffer management, see full source code for full implementation

        if(internalBufferBB.position() == 8 && buf.remaining() >= 8){
            internalBufferBB.flip();
            long remoteTS = internalBufferBB.getLong();
            internalBufferBB.clear();
            long remoteCounter = buf.getLong();
        } else {
            long remoteTS = buf.getLong();
            long remoteCounter = buf.getLong();
        }
    }


    public static void startNIOChannelsInBlockingMode() throws Exception {
        SocketChannel channel = SocketChannel.open();
        channel.setOption(StandardSocketOptions.TCP_NODELAY, true);
        channel.connect(new InetSocketAddress("localhost", 11111));

        ByteBuffer data = ByteBuffer.allocate(16);
        int size = 0;
        try{

            while(-1 != (size = channel.read(data))){
                data.flip();
                process(size, data);
                data.clear();
            }
        } finally {
            channel.close();
        }
    }

    public static void startNIOChannelsInNonBlockingModeUsingSpin() throws Exception {

        SocketChannel channel = SocketChannel.open();
        channel.setOption(StandardSocketOptions.TCP_NODELAY, true);
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress(InetAddress.getLocalHost(), 11111));
        channel.finishConnect();

        ByteBuffer data = ByteBuffer.allocate(16);
        int size = 0;
        try {
            while (-1 != (size = channel.read(data))) {
                if (size != 0) {
                    data.flip();
                    process(size, data);
                    data.clear();
                }
            }
        } finally {
            channel.close();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread thread = new Thread(new SocketClientJavaStream(), "Server");
        thread.start();
        System.out.println("Server running...");


        //NIO channels in blocking mode
        try {
            long start = System.nanoTime();
            for (int i = 0; i < LOOP_COUNT; i++)
//                startNIOChannelsInBlockingMode();
                startNIOChannelsInNonBlockingModeUsingSpin();
            long time = System.nanoTime() - start;
            System.out.printf("Average parse time was %.2f us, fields per message %d%n", time / LOOP_COUNT / 1e3, LOOP_COUNT);

        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println("Press enter...");
//        while(System.in.read() == ' ') {}
        thread.join();
    }
}
