package controller.command;

import controller.filter.IntensityFilter;
import model.ImageMap;

/**
 * A class that represents the Intensity Component transformation on an image.
 * It is represented by the source image, destination image, and split (if required).
 */
public class ComponentIntensity implements ImageCommand {
  private final String source;
  private final String result;
  private final int split;

  /**
   * Constructor function for the Component Intensity transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public ComponentIntensity(String[] args) {
    if (args.length != 3 && args.length != 5) {
      throw new IllegalArgumentException(
              "Error: Illegal number of arguments in component-intensity!");
    } else if (args.length == 5 && !args[3].equals("split")) {
      throw new IllegalArgumentException("Error: Illegal argument in component-intensity!");
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
    return images.apply(source, result, img -> img.applyColorFilter(new IntensityFilter()), split);
  }
}