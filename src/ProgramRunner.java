import java.io.File;

import controller.ScriptReader;

/**
 * Contains the main method.
 * It is responsible for running the entire program based on the provided script file.
 */
public class ProgramRunner {

  /**
   * Runs the entire program, based on the provided script file.
   *
   * @param args takes in a single String which is the file path of the script file to be run.
   */
  public static void main(String[] args) {
    // Initialize Controller
    ScriptReader reader = new ScriptReader();

    if (args.length == 0) {
      System.out.println("No script file provided! Entering CLI mode...");
      reader.build();
    } else if (new File(args[0]).exists() && new File(args[0]).isFile()) {
      reader.build(args[0]);
    } else {
      System.out.println("Invalid script file path! Entering CLI mode...");
      reader.build();
    }
  }

}
