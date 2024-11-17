package controller.command;

import java.util.Map;

import model.ImageMapInterface;
import model.ImageModel;
import util.ImageTransformer;

/**
 * A class that represents the Levels Adjust transformation on an image.
 * It is represented by the source image, destination image, and split (if required).
 * It also stores the black, mid, and white values to be adjusted with.
 */
public class LevelsAdjust implements ImageCommand {

  private final String[] args;

  /**
   * Constructor function for the Levels Adjust transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public LevelsAdjust(String[] args) {
    if (args.length == 7 && !args[5].equals("split")) {
      throw new IllegalArgumentException("Error: Illegal argument in levels adjust.!");
    } else if (args.length == 5 || args.length == 7) {
      this.args = args;
    } else {
      throw new IllegalArgumentException("Error: Illegal number of arguments in levels adjust.!");
    }
  }


  @Override
  public void apply(ImageMapInterface images) {
    images.levelsAdjust(args);
  }
}
