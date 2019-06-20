package com.unitedinternet.workshop.examplecommunication.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import static com.unitedinternet.workshop.examplecommunication.UtilFunctions.readLine;

public class Client {
    public Client() throws IOException {
        List<InetAddress> addresses = getAddresses();
        printAddresses(addresses);
        InetAddress target = selectAddress(addresses);
        System.out.println(target + " selected");
        System.out.println("Enter port:");
        int port = Integer.parseInt(readLine());
        System.out.println("Enter message:");
        String message = readLine();
        sendMessage(target, port, message);
    }

    private void printAddresses(List<InetAddress> addresses) {
        for(int i = 0; i < addresses.size(); i ++){
            System.out.println(i + " " + addresses.get(i));
        }
    }

    private void sendMessage(InetAddress target, int port, String message) throws IOException {
        try (Socket socket = new Socket(target, port)) {
            socket.setSoTimeout(10000);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println(message);
            writer.flush();
            System.out.println(new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine());
        }
    }

    private InetAddress selectAddress(List<InetAddress> addresses) {

        InetAddress target = null;

        do {
            System.out.println("Select Target interface by number:");
            try {
                target = addresses.get(Integer.parseInt(readLine()));
                // don't do this... that's really dirt ;)
            } catch (Exception ignore) {
            }
        } while (target == null);

        return target;
    }

    private List<InetAddress> getAddresses() throws SocketException {
        List<InetAddress> addresses = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        while (interfaces.hasMoreElements())
            addresses.addAll(
                    interfaces.nextElement().getInterfaceAddresses().stream()
                            .filter(interfaceAddress -> interfaceAddress.getAddress() instanceof Inet6Address)
                            .map(InterfaceAddress::getAddress)
                            .collect(Collectors.toList())
            );
        return addresses;
    }
}
