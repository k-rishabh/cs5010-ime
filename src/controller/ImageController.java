package controller;

import java.io.FileInputStream;
import java.io.IOException;
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
import controller.command.ImageCommand;
import controller.command.LevelsAdjust;
import controller.command.Load;
import controller.command.Save;
import controller.command.Sepia;
import controller.command.Sharpen;
import controller.command.SplitRGB;
import model.ImageMap;
import model.ImageMapInterface;
import model.ImageModel;
import controller.command.Histogram;

/**
 * The main controller of the program. It is responsible for delegating operations to the model.
 * It uses a command design pattern to call transformations on an object using the command map.
 * It also stores the images in the form of a hashmap.
 * Supports both CLI and script based input.
 */
public class ImageController implements ImageControllerInterface {
  private Map<String, Function<String[], ImageCommand>> commands;
  private final Readable in;
  private final Appendable out;


  /**
   * Constructs the controller with a Readable and
   * Appendable object. It has been designed to accept a sequence of multiple
   * inputs from the Readable object.
   *
   * @param in  - Readable Object
   * @param out - Appendable Object
   */
  public ImageController(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
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
   * The command package automatically adds the resulting image(s) to the map, since it is
   * provided as input, along with the parameters of the command.
   */
  public void execute(ImageMapInterface image) throws IOException {
    commands = new HashMap<>();
    this.initializeCommands();
    Scanner sc = new Scanner(in);
    int lineNo = 0;
    while (sc.hasNextLine()) {
      try {
        lineNo++;
        String cmd = sc.nextLine().trim();

        if (cmd.equals("q") || cmd.equals("quit")) {
          System.out.println("Exiting program...");
          System.exit(0);
        } else if (!cmd.isEmpty() && cmd.charAt(0) != '#') {
          String[] tokens = cmd.split(" ");

//        if (tokens[0].equals("run")) {
//          ImageController run = new ImageController(null, tokens[1]);
//          run.execute();
//          continue;
//        }

          if (tokens.length < 3) {
//            this.out.append("Unknown command on line number:").append(String.valueOf(lineNo)).append("\n");
//            System.out.printf("Unknown command on line number: %d\n", lineNo);
            throw new IllegalArgumentException("Unknown command on line number: " + String.valueOf(lineNo));
          }


          String[] args = new String[tokens.length - 1];
          System.arraycopy(tokens, 1, args, 0, tokens.length - 1);
          ImageCommand fn = commands.get(tokens[0]).apply(args);
          if (fn != null) {
            fn.apply(image);
            this.out.append(tokens[0]).append(" operation performed!").append("\n");
          } else {
//            this.out.append("Unknown command on line number:").append(String.valueOf(lineNo)).append("\n");
            throw new IllegalArgumentException("Unknown command on line number: " + String.valueOf(lineNo));
          }
        }
      }
      catch (Exception e) {
        this.out.append("Error").append("\n");
      }
    }

  }
}
