package com.unitedinternet.workshop.examplecommunication;

import com.unitedinternet.workshop.examplecommunication.client.Client;
import com.unitedinternet.workshop.examplecommunication.server.Server;

import java.io.IOException;

import static com.unitedinternet.workshop.examplecommunication.UtilFunctions.readLine;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Select application:");
        System.out.println("Server: 1");
        System.out.println("Client: anything else");

        if (readLine().equalsIgnoreCase("1")) {
            new Server();
        } else {
            new Client();
        }
    }
}
