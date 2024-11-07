package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import java.io.FileNotFoundException;

/**
 * Functional class that is responsible for reading a script line by line.
 * Passes each line to the CommandInterpreter.
 */
public class ScriptReader {
  private Scanner sc;

  ImageHandler imgHandler;

  public ScriptReader() {
    sc = new Scanner(System.in);
    imgHandler = new ImageHandler();
  }

  public void build() {
    while (true) {
      String cmd = sc.nextLine().trim();
      if (cmd.equals("q") || cmd.equals("quit")) {
        return;
      } else if (!cmd.isEmpty()) {
        Readable in = new StringReader(cmd);
        CommandInterpreter ci = new CommandInterpreter(in, System.out);
        try {
          ci.apply(imgHandler);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
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
    StringBuilder fileContent = new StringBuilder();
    try {
      sc = new Scanner(new FileInputStream(scriptFilePath));
      while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.isEmpty()) {
          continue;
        }
        if (s.charAt(0) != '#') {
          fileContent.append(s).append(System.lineSeparator());
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("Exception: Script file not found: " + scriptFilePath);
      return;
    }

    Readable in = new StringReader(fileContent.toString());

    CommandInterpreter ci = new CommandInterpreter(in, System.out);
    try {
      ci.apply(imgHandler);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
