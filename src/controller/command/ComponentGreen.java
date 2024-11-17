package controller.command;

import java.util.Map;

import controller.filter.CompGreenFilter;
import model.ImageMapInterface;
import model.ImageModel;
import util.ImageTransformer;

/**
 * A class that represents the Green Component transformation on an image.
 * It is represented by the source image, destination image, and split (if required).
 */
public class ComponentGreen implements ImageCommand {
  private final String[] args;

  /**
   * Constructor function for the Component Green transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public ComponentGreen(String[] args) {
    if (args.length == 4 && !args[2].equals("split")) {
      throw new IllegalArgumentException("Error: Illegal argument in component-green!");
    } else if (args.length == 2 || args.length == 4) {
      this.args = args;
    } else {
      throw new IllegalArgumentException("Error: Illegal number of arguments in component-green!");
    }
  }

  @Override
  public void apply(ImageMapInterface images) {
    images.greenComponent(args);
  }

}

