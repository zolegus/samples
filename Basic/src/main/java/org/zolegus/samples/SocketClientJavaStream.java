package org.zolegus.samples;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SocketClientJavaStream implements Runnable {

    public static final int LOOP_COUNT = 1000;

    @Override
    public void run() {
        final ServerSocket server;
        try {
            server = new ServerSocket(11111);
//            while (true) {
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

    private static final byte[] internalBufferBA = new byte[8];

    public static void processInputStream(InputStream input) throws IOException{
        while(true){
            int size = input.read(internalBufferBA, 0, 8);
            if(size == -1) break;
            long remoteTS = bytesToLong(internalBufferBA);

            size = input.read(internalBufferBA, 0, 8);
            if(size == -1) break;
            long remoteCounter = bytesToLong(internalBufferBA);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread thread = new Thread(new SocketClientJavaStream(), "Server");
        thread.start();
        System.out.println("Server running...");

        //Connection code for InputStream, BufferedInputStream, DataInputStream and Buffered DataInputStream

        Socket client = new Socket("localhost", 11111);
        client.setTcpNoDelay(true);

        //InputStream, BufferedInputStream, DataInputStream and Buffered DataInputStream
        InputStream in = client.getInputStream();
//        InputStream in = new BufferedInputStream(client.getInputStream(), 10);
//        DataInputStream in = new DataInputStream(client.getInputStream());
//        DataInputStream in = new BufferedInputStream(new DataInputStream(client.getInputStream()), 10);

        try{
            long start = System.nanoTime();
            for (int i = 0; i < LOOP_COUNT; i++)
                processInputStream(in);
            long time = System.nanoTime() - start;
            System.out.printf("Average parse time was %.2f us, fields per message %d%n", time / LOOP_COUNT / 1e3, LOOP_COUNT);
        } finally {
            client.close();
        }

//        System.out.println("Press enter...");
//        while(System.in.read() == ' ') {}
        thread.join();
    }


}
