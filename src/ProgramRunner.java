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
    if (args.length > 0) {
      ScriptReader.build(args[0]);
    } else {
      System.out.println("No script file provided!");
    }
  }

}
