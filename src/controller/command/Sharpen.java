package controller.command;

import java.util.Map;

import controller.filter.SharpenFilter;
import model.ImageMapInterface;
import model.ImageModel;
import util.ImageTransformer;

/**
 * A class that represents the Sharpen transformation on an image.
 * It is represented by the source image, destination image, and split (if required).
 */
public class Sharpen implements ImageCommand {
  private final String[] args;

  /**
   * Constructor function for the Sharpen transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public Sharpen(String[] args) {
    if (args.length == 4 && !args[2].equals("split")) {
      throw new IllegalArgumentException("Error: Illegal argument in sharpen!");
    } else if (args.length == 2 || args.length == 4) {
      this.args = args;
    } else {
      throw new IllegalArgumentException("Error: Illegal number of arguments in sharpen!");
    }
  }


  @Override
  public void apply(ImageMapInterface images) {
    images.sharpen(args);
  }
}
