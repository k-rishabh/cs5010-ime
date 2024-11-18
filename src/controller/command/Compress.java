package controller.command;

import model.ImageMap;

/**
 * A class that represents the Compress transformation on an image.
 * It is represented by the source image, destination image, and ratio of compression.
 */
public class Compress implements ImageCommand {
  private final String source;
  private final String result;
  private final int ratio;

  /**
   * Constructor function for the Compress transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public Compress(String[] args) {
    if (args.length == 4) {
      this.ratio = Integer.parseInt(args[1]);
      this.source = args[2];
      this.result = args[3];
    } else {
      throw new IllegalArgumentException(
              "Error: Illegal number of arguments in compress!");
    }
  }

  @Override
  public int apply(ImageMap images) {
    return images.apply(source, result, img -> img.compress(ratio), 0);
  }
}
