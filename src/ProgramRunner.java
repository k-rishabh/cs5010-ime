import java.util.Arrays;

import controller.ImageController;
import model.ImageModel;
import model.RGBImage;

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
    // Initialize
    ImageModel model = new RGBImage(0, 0);
    ImageController controller;
    if (args.length == 0) {
      // CLI mode
      controller = new ImageController(model);

    } else if (args.length == 2) {
      // script mode
      if (args[0].equals("-file")){
        controller = new ImageController(model, args[1]);
      } else {
        throw new IllegalArgumentException("Invalid command line argument!");
      }
    } else {
      throw new IllegalArgumentException("Unknown command line arguments!");
    }

    controller.execute();
  }
}