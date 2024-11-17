import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

import controller.ImageController;
import model.ImageMap;
import model.ImageMapInterface;
import model.ImageModel;
import model.RGBImage;

import static java.lang.System.in;

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
    ImageMapInterface imageMap = new ImageMap();
    ImageController controller;

    if (args.length == 0) {
      // CLI mode
      controller = new ImageController(new InputStreamReader(System.in), System.out);
    } else if (args.length == 2) {
      if (args[0].equals("-file")) {
        File file = new File(args[1]);
        FileInputStream fis = new FileInputStream(file);
        Scanner sc = new Scanner(fis);
        StringBuilder builder = new StringBuilder();
        while (sc.hasNextLine()) {
          String s = sc.nextLine();
          if (s.isEmpty()) {
            continue;
          }
          if (s.charAt(0) != '#') {
            builder.append(s).append(System.lineSeparator());
          }
        }
        Readable in = new StringReader(builder.toString());
        controller = new ImageController(in, System.out);
      } else {
        throw new IllegalArgumentException("Invalid command line argument!");
      }
    } else {
      throw new IllegalArgumentException("Unknown command line arguments!");
    }
    controller.execute(imageMap);
  }
}