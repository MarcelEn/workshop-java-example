package com.unitedinternet.workshop.examplecommunication.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import static com.unitedinternet.workshop.examplecommunication.server.Server.getSelectedPort;


class Listener extends Thread {
    private ServerSocket serverSocket;
    private InterfaceAddress interfaceAddress;

    Listener(InterfaceAddress address) throws IOException {
        interfaceAddress = address;
        if (isV6()) System.out.println(interfaceAddress);
        serverSocket = new ServerSocket();
        serverSocket.setSoTimeout(100);
        serverSocket.bind(new InetSocketAddress(address.getAddress(), getSelectedPort()));
        start();
    }

    @Override
    public void run() {
        // Dirty way for ipv6 only
        if (isV6())
            while (!interrupted()) {
                try {
                    handleMessage(serverSocket.accept());
                } catch (SocketTimeoutException ignore) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isV6() {
        return interfaceAddress.getAddress() instanceof Inet6Address;
    }

    private void handleMessage(Socket clientSocket) {
        try {
            String message = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())).readLine();
            System.out.println("got " + message + " on " + interfaceAddress);
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
            writer.println(message.toUpperCase());
            writer.flush();
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
}
