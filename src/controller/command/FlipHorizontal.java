package controller.command;

import model.ImageMap;

/**
 * A class that represents the Horizontal Flip transformation on an image.
 * It is represented by the source image, and destination image.
 */
public class FlipHorizontal implements ImageCommand {
  String src;
  String dest;

  /**
   * Constructor function for the Horizontal Flip transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public FlipHorizontal(String[] args) {
    if (args.length == 3) {
      src = args[1];
      dest = args[2];
    } else {
      throw new IllegalArgumentException("Unknown number of arguments for horizontal-flip!");
    }
  }

  @Override
  public int apply(ImageMap images) {
    return images.apply(src, dest, img -> img.horizontalFlip(), 0);
  }
}
