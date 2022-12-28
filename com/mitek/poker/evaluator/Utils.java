package com.mitek.poker.evaluator;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Utils {
    private static final long MEGABYTE = 1024L * 1024L;

    /**
     * 
     * @param inputString - string to pad
     * @param length      - desired length
     * @return String padded with zeroes on the left
     */
    public static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);
        return sb.toString();
    }

    public static void writeToFile(String fileName, ArrayList<String> data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
        writer.flush();

        for (String s : data) {
            writer.write(s);
            writer.newLine();
        }

        writer.close();
    }

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    public static void checkMem() {

        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is B: " + memory);
        System.out.println("Used memory is MB: "
                + bytesToMegabytes(memory));
    }

    public static int getMemKBytes(Object obj) {
        int retVal = 0;
        try {
            retVal = (int) (getBytesFromList(obj) / 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    private static long getBytesFromList(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(obj);
        out.close();
        return baos.toByteArray().length;
    }

}
