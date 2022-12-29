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

    private static long bytesToMegabytes(long bytes) {
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
        retVal = (int) (getBytesFromList(obj) / 1024L);
        return retVal;
    }

    private static long getBytesFromList(Object obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(obj);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray().length;
    }

    public static class Timer {
        private static long start = 0L;
        private static long stop = 0L;

        public static void stopTimer() {
            stop = System.currentTimeMillis();
        }

        public static void startTimer() {
            start = System.currentTimeMillis();
        }

        public static void printSecondsFromStart() {
            long elapsedTime = System.currentTimeMillis() - start;
            System.out.println(String.format("%d seconds.", (elapsedTime / 1000)));
        }

        public static void printElapsedTime() {
            System.out.println(String.format("%d seconds.", (stop - start)));
        }

    }
}
