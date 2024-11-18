package controller.command;

import controller.filter.CompGreenFilter;
import model.ImageMap;

/**
 * A class that represents the Green Component transformation on an image.
 * It is represented by the source image, destination image, and split (if required).
 */
public class ComponentGreen implements ImageCommand {
  private final String source;
  private final String result;
  private final int split;

  /**
   * Constructor function for the Component Green transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public ComponentGreen(String[] args) {
    if (args.length != 3 && args.length != 5) {
      throw new IllegalArgumentException("Error: Illegal number of arguments in component-green!");
    } else if (args.length == 5 && !args[3].equals("split")) {
      throw new IllegalArgumentException("Error: Illegal argument in component-green!");
    }

    this.source = args[1];
    this.result = args[2];

    if (args.length == 5) {
      this.split = Integer.parseInt(args[4]);
    } else {
      this.split = 0;
    }
  }

  @Override
  public int apply(ImageMap images) {
    return images.apply(source, result, img -> img.applyColorFilter(new CompGreenFilter()), split);
  }
}

