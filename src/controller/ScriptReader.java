package controller;

import java.io.FileInputStream;
import java.util.Scanner;

import java.io.FileNotFoundException;

/**
 * Functional class that is responsible for reading a script line by line.
 * Passes each line to the CommandInterpreter.
 */
public class ScriptReader {
  private Scanner sc;

  public ScriptReader() {
    sc = new Scanner(System.in);
  }

  public void build() {
    while (true) {
      String cmd = sc.nextLine().trim();
      if (cmd.equals("q") || cmd.equals("quit")) {
        return;
      } else if (!cmd.isEmpty()) {
        CommandInterpreter.execute(cmd, 0);
      }
    }
  }

  /**
   * The main controller function that is responsible for reading the script file.
   * Ignores empty lines.
   * Passes each line to the CommandInterpreter.
   *
   * @param scriptFilePath the absolute file path of the script file
   */
  public void build(String scriptFilePath) {
    try {
      sc = new Scanner(new FileInputStream(scriptFilePath));
    } catch (FileNotFoundException e) {
      System.out.println("Exception: Script file not found: " + scriptFilePath);
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
