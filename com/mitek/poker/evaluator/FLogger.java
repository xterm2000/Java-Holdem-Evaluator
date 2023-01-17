package com.mitek.poker.evaluator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FLogger {

  static String fileName;
  static PrintWriter writer = null;

  public static boolean init(String fn) {

    try {
      writer = new PrintWriter(fn, "UTF-8");
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      e.printStackTrace();
      return false;
    }
    return true;

  }

  private void setFileName(String fn) {
    fileName = fn;
  }

  public static void log(String msg) {
    writer.print(msg);
  }

  public static void logln(String msg) {
    writer.println(msg);
  }

  public static void shutdown() {
    System.out.println("writer closing.");
    writer.flush();
    writer.close();

  }

}
