package controller.command;

import java.util.Map;

import model.ImageMapInterface;
import model.ImageModel;
import util.ImageTransformer;

/**
 * A class that represents the Vertical Flip transformation on an image.
 * It is represented by the source image, and destination image.
 */
public class FlipVertical implements ImageCommand {
  String src;
  String dest;

  /**
   * Constructor function for the Vertical Flip transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public FlipVertical(String[] args) {
    if (args.length == 2) {
      src = args[0];
      dest = args[1];
    } else {
      throw new IllegalArgumentException("Unknown number of arguments for vertical-flip!");
    }
  }


  @Override
  public void apply(ImageMapInterface images) {
    images.verticalFlip(src, dest);
  }
}
