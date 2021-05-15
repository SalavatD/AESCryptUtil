package com.anytrash;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class Main {

    private static AesCrypt.Mode mode = AesCrypt.Mode.DEFAULT;

    private static String inputFilePath = "";
    private static String outputFilePath = "";
    private static String password = "";

    public static void main(String[] args) {

        try {
            parseArgs(args);
        } catch (IllegalArgumentException exception) {
            System.err.println("Argument error: " + exception.getMessage());
            System.err.println("Use -h for help.");
            System.exit(-1);
        }

        AesCrypt aesCrypt = new AesCrypt();

        try {
            aesCrypt.openFiles(inputFilePath, outputFilePath, password, mode);
            System.out.println("Done.");
        } catch (GeneralSecurityException exception) {
            exception.printStackTrace();
            System.err.println("Internal error: " + exception.getMessage());
            System.exit(-1);
        } catch (IOException exception) {
            System.err.println("Input/output error: " + exception.getMessage());
            System.exit(-1);
        } catch (IllegalArgumentException exception) {
            System.err.println("Argument error: " + exception.getMessage());
            System.exit(-1);
        }
    }

    private static void parseArgs(String[] args) {

        if (args.length == 1 && args[0].equals("-h")) {
            printHelp();
            System.exit(0);
        }

        if (args.length < 7) {
            throw new IllegalArgumentException("Not enough arguments.");
        }

        if (args.length > 7) {
            throw new IllegalArgumentException("Too many arguments.");
        }

        if (Arrays.asList(args).contains("-e") && Arrays.asList(args).contains("-d")) {
            throw new IllegalArgumentException("Undefined mode.");
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-e")) {
                mode = AesCrypt.Mode.ENCRYPT;
            } else if (args[i].equals("-d")) {
                mode = AesCrypt.Mode.DECRYPT;
            } else if (i + 1 < args.length) {
                switch (args[i]) {
                    case "-i":
                        inputFilePath = args[i + 1];
                        break;
                    case "-o":
                        outputFilePath = args[i + 1];
                        break;
                    case "-p":
                        password = args[i + 1];
                        break;
                }
            }
        }

        if (mode == AesCrypt.Mode.DEFAULT) {
            throw new IllegalArgumentException("Required option is missing (-e or -d).");
        } else if (inputFilePath.isEmpty()) {
            throw new IllegalArgumentException("Input file path is empty.");
        } else if (outputFilePath.isEmpty()) {
            throw new IllegalArgumentException("Output file path is empty.");
        } else if (inputFilePath.equals(outputFilePath)) {
            throw new IllegalArgumentException("Input and output files are the same.");
        } else if (password.isEmpty()) {
            throw new IllegalArgumentException("Password is empty.");
        }
    }

    private static void printHelp() {
        System.out.println();
        System.out.println("About:");
        System.out.println("    Utility for encrypting files with the AES-256-CBC algorithm.");
        System.out.println("    Created 16.05.2021.");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("    aescryptutil.jar [-e|-d] -i INPUT_FILE -o OUTPUT_FILE -p PASSWORD");
        System.out.println();
        System.out.println("Options:");
        System.out.println("    -e    Encrypt mode");
        System.out.println("    -d    Decrypt mode");
        System.out.println("    -i    Input file path");
        System.out.println("    -o    Output file path");
        System.out.println("    -p    Password");
        System.out.println("    -h    Call help");
        System.out.println();
    }
}
