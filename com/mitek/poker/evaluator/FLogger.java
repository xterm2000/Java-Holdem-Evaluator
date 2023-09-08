package com.mitek.poker.evaluator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * <pre>
 * writes output to file 
 * init - used to init with the file name 
 * log - prints to file 
 * shutdown - flushes and closes the writer
 * </pre>
 */
public class FLogger {

  static String fileName;
  static PrintWriter writer = null;
  static boolean log_enable = false;

  /**
   * @param fn - file name
   * @return - true if succeeded, false otherwise
   */
  public static boolean init(String fn) {

    try {
      writer = new PrintWriter(fn, "UTF-8");
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      e.printStackTrace();
      return false;
    }
    return true;

  }

  /** log without newline */
  public static void log(String msg) {
    if (!log_enable)
      return;
    writer.print(msg);
  }

  /** log with newline */
  public static void logln(String msg) {
    if (!log_enable)
      return;
    writer.println(msg);
  }

  /** flushes and closes the writer */
  public static void shutdown() {
    System.out.println("writer closing.");
    writer.flush();
    writer.close();
  }

}
