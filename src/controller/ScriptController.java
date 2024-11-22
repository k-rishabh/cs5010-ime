package controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import controller.command.CombineRGB;
import controller.command.ImageCommand;
import controller.command.SplitRGB;
import model.ImageMap;
import model.ImageMapModel;

import static java.lang.System.exit;

/**
 * The main controller of the program. It is responsible for delegating operations to the model.
 * It uses a command design pattern to call transformations on an object using the command map.
 * It also stores the images in the form of a hashmap.
 * Supports both CLI and script based input.
 */
public class ScriptController extends AbstractController {
  private final Readable in;
  private final Appendable out;

  private final ImageMap imageMap;

  /**
   * Constructs the controller with a Readable and Appendable object.
   * It has been designed to accept a sequence of multiple
   * inputs from the Readable object.
   *
   * @param in  - Readable Object
   * @param out - Appendable Object
   */
  public ScriptController(ImageMap model, Readable in, Appendable out) {
    commands = new HashMap<>();
    this.initializeCommands();

    this.imageMap = model;
    this.in = in;
    this.out = out;
  }

  @Override
  protected void initializeCommands() {
    super.initializeCommands();
    commands.put("rgb-split", args -> new SplitRGB(args));
    commands.put("rgb-combine", args -> new CombineRGB(args));
  }

  @Override
  public void execute() {
    Scanner sc = new Scanner(in);
    int lineNo = 0;

    while (sc.hasNextLine()) {
      try {
        lineNo++;
        String cmd = sc.nextLine().trim();

        if (cmd.isEmpty()) {
          continue;
        } else if (cmd.equals("q") || cmd.equals("quit")) {
          this.out.append("Exiting program...");
          exit(0);
        } else if (cmd.charAt(0) == '#') {
          continue;
        }

        String[] tokens = cmd.split(" ");

        if (tokens[0].equals("run")) {
          File f = new File(tokens[1]);
          ScriptController run = new ScriptController(new ImageMapModel(),
                  new FileReader(f), System.out);

          run.execute();
          continue;
        }

        if (tokens.length < 3) {
          this.out.append(String.format("ERROR: Unknown number of args for \"%s\" on line %d.\n",
                  tokens[0], lineNo));
          continue;
        } else if (Arrays.asList(tokens).contains("split") && !splitCommands.contains(tokens[0])) {
          this.out.append(String.format("ERROR: Command \"%s\" on line %d cannot be previewed!\n",
                  tokens[0], lineNo));
          continue;
        }

        if (!commands.containsKey(tokens[0])) {
          this.out.append(String.format("ERROR: Unknown command \"%s\" on line %d.\n",
                  tokens[0], lineNo));
          continue;
        }

        ImageCommand fn = commands.get(tokens[0]).apply(tokens);

        if (fn != null) {
          if (fn.apply(imageMap) == 0) {
            this.out.append(String.format("%s performed successfully!\n", tokens[0]));
          } else {
            this.out.append(String.format("Error while performing %s on line %d!\n",
                    tokens[0], lineNo));
          }

        } else {
          this.out.append(String.format("ERROR: Unknown command \"%s\" on line %d.\n",
                  tokens[0], lineNo));
        }
      } catch (IOException e) {
        System.out.println("ERROR: IOException while writing to appendable! Exiting program...");
        exit(1);
      }
    }

    System.exit(0);
  }
}
