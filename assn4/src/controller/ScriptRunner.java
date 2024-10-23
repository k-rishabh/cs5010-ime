package controller;

import java.io.FileInputStream;
import java.util.Scanner;

import java.io.FileNotFoundException;

public class ScriptRunner {
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
