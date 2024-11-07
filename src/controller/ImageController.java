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
 * Functional class that is responsible for reading a script line by line.
 * Passes each line to the CommandInterpreter.
 */
public class ImageController {
  private Scanner sc;
  private Map<String, ImageModel> images;
  private Map<String, Function<String[], ImageCommand>> commands;

  /**
   * Command line mode.
   *
   * @param img
   */
  public ImageController(ImageModel img) {
    sc = new Scanner(System.in);
    commands = new HashMap<>();
    images = new HashMap<>();
  }

  /**
   * Script file mode. If fails, defaults to command line mode.
   *
   * @param img
   * @param scriptFilePath
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
   * The main controller function that is responsible for reading the script file.
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
