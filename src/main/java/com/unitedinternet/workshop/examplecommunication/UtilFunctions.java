package com.unitedinternet.workshop.examplecommunication;

import java.util.Scanner;

public class UtilFunctions {
    private static final Scanner sc = new Scanner(System.in);

    private UtilFunctions() {
    }

    public static String readLine() {
        return sc.nextLine();
    }

}
