package controller.command;

import java.util.Map;

import model.ImageMapInterface;
import model.ImageModel;
import util.ImageTransformer;

/**
 * A class that represents the Horizontal Flip transformation on an image.
 * It is represented by the source image, and destination image.
 */
public class FlipHorizontal implements ImageCommand {
  private final String source;
  private final String result;

  /**
   * Constructor function for the Horizontal Flip transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public FlipHorizontal(String[] args) {
    if (args.length == 2) {
      source = args[0];
      result = args[1];
    } else {
      throw new IllegalArgumentException("Unknown number of arguments for horizontalflip!");
    }
  }


  @Override
  public void apply(ImageMapInterface images) {
    images.horizontalFlip(source, result);
  }
}
