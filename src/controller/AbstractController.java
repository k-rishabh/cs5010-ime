package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import controller.command.Blur;
import controller.command.Brighten;
import controller.command.ColorCorrect;
import controller.command.ComponentBlue;
import controller.command.ComponentGreen;
import controller.command.ComponentIntensity;
import controller.command.ComponentLuma;
import controller.command.ComponentRed;
import controller.command.ComponentValue;
import controller.command.Compress;
import controller.command.Downscale;
import controller.command.FlipHorizontal;
import controller.command.FlipVertical;
import controller.command.Histogram;
import controller.command.ImageCommand;
import controller.command.LevelsAdjust;
import controller.command.Load;
import controller.command.Save;
import controller.command.Sepia;
import controller.command.Sharpen;

/**
 * An abstract class that is used by the controllers to initialize the list of known commands,
 * in the form of a command mapping. It also initializes a list of commands that support
 * image previewing.
 */
public abstract class AbstractController implements ImageController {
  protected Map<String, Function<String[], ImageCommand>> commands;
  protected List<String> splitCommands;

  /**
   * Initializes the map of known commands and a list of commands that support image previewing.
   */
  protected void initializeCommands() {
    // all known commands
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

    commands.put("blur", args -> new Blur(args));
    commands.put("sharpen", args -> new Sharpen(args));
    commands.put("sepia", args -> new Sepia(args));

    commands.put("compress", args -> new Compress(args));
    commands.put("histogram", args -> new Histogram(args));
    commands.put("color-correct", args -> new ColorCorrect(args));
    commands.put("levels-adjust", args -> new LevelsAdjust(args));
    commands.put("downscale", args -> new Downscale(args));

    // all commands that can be previewed/split
    splitCommands = new ArrayList<>();
    splitCommands.add("blur");
    splitCommands.add("sharpen");
    splitCommands.add("sepia");

    splitCommands.add("red-component");
    splitCommands.add("green-component");
    splitCommands.add("blue-component");
    splitCommands.add("value-component");
    splitCommands.add("luma-component");
    splitCommands.add("intensity-component");

    splitCommands.add("color-correct");
    splitCommands.add("levels-adjust");
  }
}
