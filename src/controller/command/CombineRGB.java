package controller.command;

import java.util.Map;

import model.ImageMapInterface;
import model.ImageModel;

/**
 * A class that represents the CombineRGB transformation.
 * It is represented by the red, green, and blue source images, and the destination image.
 */
public class CombineRGB implements ImageCommand {
  private final String dest;
  private final String red;
  private final String green;
  private final String blue;

  /**
   * Constructor function for the CombineRGB transformation. Requires an array of Strings, each
   * in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public CombineRGB(String[] args) {
    if (args.length == 4) {
      dest = args[0];
      red = args[1];
      green = args[2];
      blue = args[3];
    } else {
      throw new IllegalArgumentException("Illegal number of arguments in rgb-combine!");
    }
  }

  @Override
  public void apply(ImageMapInterface images) {
    images.rgbCombine(dest, red, green, blue);
  }
}
