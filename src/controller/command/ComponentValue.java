package controller.command;

import java.util.Map;

import model.ImageMapInterface;
import model.ImageModel;
import util.ImageTransformer;

/**
 * A class that represents the Value Component transformation on an image.
 * It is represented by the source image, destination image, and split (if required).
 */
public class ComponentValue implements ImageCommand {
  private final String[] args;

  /**
   * Constructor function for the Component Value transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public ComponentValue(String[] args) {
    if (args.length != 2 && args.length != 4) {
      throw new IllegalArgumentException("Error: Illegal number of arguments in component-value!");
    } else if (args.length == 4 && !args[2].equals("split")) {
      throw new IllegalArgumentException("Error: Illegal argument in component-value!");
    }

    this.args = args;
  }

  @Override
  public void apply(ImageMapInterface images) {
    images.valueGreyscale(args);
  }

}
