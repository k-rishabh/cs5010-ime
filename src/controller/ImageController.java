package controller;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.util.function.Function;

import controller.command.Blur;
import controller.command.Brighten;
import controller.command.ColorCorrect;
import controller.command.CombineRGB;
import controller.command.ComponentBlue;
import controller.command.ComponentGreen;
import controller.command.ComponentIntensity;
import controller.command.ComponentLuma;
import controller.command.ComponentRed;
import controller.command.ComponentValue;
import controller.command.Compress;
import controller.command.FlipHorizontal;
import controller.command.FlipVertical;
import controller.command.LevelsAdjust;
import controller.command.Load;
import controller.command.Save;
import controller.command.Sepia;
import controller.command.Sharpen;
import controller.command.SplitRGB;
import model.ImageModel;
import controller.command.Histogram;

/**
 * The main controller of the program. It is responsible for delegating operations to the model.
 * It uses a command design pattern to call transformations on an object using the command map.
 * It also stores the images in the form of a hashmap.
 * Supports both CLI and script based input.
 */
public class ImageController {
  private ImageModel mock;
  private Scanner sc;
  private Map<String, ImageModel> images;
  private Map<String, Function<String[], ImageCommand>> commands;

  /**
   * Initializes the controller in CLI mode. Takes the model as input.
   *
   * @param img the model on which the controller will operate
   */
  public ImageController(ImageModel img) {
    sc = new Scanner(System.in);
    commands = new HashMap<>();
    images = new HashMap<>();
    this.mock = img;
  }

  /**
   * Initializes the controller in script file mode. If fails, defaults to command line mode.
   * It takes the model and script file path as input.
   *
   * @param img the model on which the controller will operate
   * @param scriptFilePath the file path of the script file
   */
  public ImageController(ImageModel img, String scriptFilePath) {
    commands = new HashMap<>();
    images = new HashMap<>();

    try {
      sc = new Scanner(new FileInputStream(scriptFilePath));
    } catch (FileNotFoundException e) {
      System.out.println("Exception: Script file not found: " + scriptFilePath);
      System.out.println("Using CLI input...");
      sc = new Scanner(System.in);
    }
  }

  /**
   * Initializes all the known commands that can be used to operate on images.
   */
  private void initializeCommands() {
    commands.put("load", args -> new Load(args));
    commands.put("save", args -> new Save(args));

    commands.put("red-component", args -> new ComponentRed(args));
    commands.put("green-component", args -> new ComponentGreen(args));
    commands.put("blue-component", args -> new ComponentBlue(args));
    commands.put("value-component", args -> new ComponentValue(args));
    commands.put("luma-component", args -> new ComponentLuma(args));
    commands.put("intensity-component", args -> new ComponentIntensity(args));

    commands.put("horizontal-flip", args -> new FlipHorizontal(args));
    commands.put("vertical-flip", args -> new FlipVertical(args));
    commands.put("brighten", args -> new Brighten(args));

    commands.put("rgb-split", args -> new SplitRGB(args));
    commands.put("rgb-combine", args -> new CombineRGB(args));

    commands.put("blur", args -> new Blur(args));
    commands.put("sharpen", args -> new Sharpen(args));
    commands.put("sepia", args -> new Sepia(args));

    commands.put("compress", args -> new Compress(args));
    commands.put("histogram", args -> new Histogram(args));
    commands.put("color-correct", args -> new ColorCorrect(args));
    commands.put("levels-adjust", args -> new LevelsAdjust(args));
  }

  /**
   * The main (go) function of the controller that is responsible for parsing the input command.
   * Based on the command entered, it gets the function from the command map and delegates the
   * work to the command package. The command package will in turn use model functions to operate
   * on the model.
   * <p> The command package automatically adds the resulting image(s) to the map, since it is
   * provided as input, along with the parameters of the command.
   */
  public void execute() {
    this.initializeCommands();

    int lineNo = 0;
    while (sc.hasNextLine()) {
      lineNo++;
      String cmd = sc.nextLine().trim();

      if (cmd.equals("q") || cmd.equals("quit")) {
        System.out.println("Exiting program...");
        System.exit(0);
      } else if (!cmd.isEmpty() && cmd.charAt(0) != '#') {
        String[] tokens = cmd.split(" ");

        if (tokens[0].equals("run")) {
          ImageController run = new ImageController(null, tokens[1]);
          run.execute();
          continue;
        }

        if (tokens.length < 3) {
          System.out.printf("Unknown command on line number: %d\n", lineNo);
        }

        ImageCommand fn = commands.get(tokens[0]).apply(tokens);
        if (fn != null) {
          fn.apply(this.images);
        } else {
          System.out.printf("Unknown command on line number: %d\n", lineNo);
        }
      }
    }

    System.out.println("Successfully reached end of script!");
  }
}
