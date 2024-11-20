import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controller.GUIController;
import controller.ImageController;
import controller.ScriptController;
import model.ImageMapModel;
import model.ImageMap;
import view.GUIView;
import view.ImageView;

/**
 * Contains the main method.
 * It is responsible for running the entire program based on the provided script file.
 */
public class ProgramRunner {
  /**
   * Runs the entire program, based on the provided script file. If no script file is provided, or
   * incorrect script file is provided, it will default to CLI input. It requires the file in the
   * format "java ProgramRunner -file fileName".
   *
   * @param args takes in a single String which is the file path of the script file to be run.
   */
  public static void main(String[] args) throws IOException {
    ImageMap model = new ImageMapModel();
    ImageView view = new GUIView();
    ImageController controller;

    if (args.length == 0) {
      // GUI mode
      controller = new GUIController(model, view);

    } else if (args.length == 1) {
      // CLI mode
      if (!args[0].equals("-text")) {
        throw new IllegalArgumentException("Invalid command line argument!");
      }
      controller = new ScriptController(model, new InputStreamReader(System.in), System.out);

    } else if (args.length == 2) {
      // script mode
      if (!args[0].equals("-file")) {
        throw new IllegalArgumentException("Invalid command line argument!");
      }
      File file = new File(args[1]);
      controller = new ScriptController(model, new FileReader(file), System.out);

    } else {
      throw new IllegalArgumentException("Unknown command line arguments!");
    }

    controller.execute();
  }
}