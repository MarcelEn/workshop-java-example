package com.unitedinternet.workshop.examplecommunication.server;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static com.unitedinternet.workshop.examplecommunication.UtilFunctions.readLine;

public class Server {
    private List<InterfaceManager> listeners;
    private static int SELECTED_PORT;

    static int getSelectedPort(){
        return SELECTED_PORT;
    }

    public Server() throws SocketException {
        System.out.println("Enter port:");
        SELECTED_PORT = Integer.parseInt(readLine());

        listeners = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while(interfaces.hasMoreElements())
                listeners.add(new InterfaceManager(interfaces.nextElement()));
            System.out.println("press enter key to exit");
            readLine();
        } finally {
            closeAll();
        }
    }

    private void closeAll() {
        listeners.forEach(l -> {
            try {
                l.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
