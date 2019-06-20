package com.unitedinternet.workshop.examplecommunication.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

public class InterfaceManager implements Closeable {
    private NetworkInterface networkInterface;
    private List<Listener> listeners;

    InterfaceManager(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
        printInfo();
        listeners = new ArrayList<>();
        networkInterface.getInterfaceAddresses()
                .forEach(address -> {
                    try {
                        listeners.add(new Listener(address));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void printInfo() {
        System.out.println(networkInterface);
    }

    public void close() throws IOException {
        listeners.forEach(Thread::interrupt);
    }
}
