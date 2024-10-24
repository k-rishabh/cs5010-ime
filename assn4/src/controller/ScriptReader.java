package controller;

import java.io.FileInputStream;
import java.util.Scanner;

import java.io.FileNotFoundException;

/**
 * Functional class that is responsible for reading a script line by line.
 * Passes each line to the CommandInterpreter.
 */
public class ScriptReader {
  /**
   * The main controller function that is responsible for reading the script file.
   * Ignores empty lines.
   * Passes each line to the CommandInterpreter.
   *
   * @param scriptFilePath the absolute file path of the script file
   */
  public static void go(String scriptFilePath) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(scriptFilePath));
    } catch (FileNotFoundException e) {
      System.out.println("Script file not found: " + scriptFilePath);
      return;
    }

    int lineNo = 0;
    while (sc.hasNextLine()) {
      String line = sc.nextLine().trim();
      lineNo++;

      if (!line.isEmpty()) {
        CommandInterpreter.execute(line, lineNo);
      }
    }
  }
}
