package controller.command;

import model.ImageMap;

/**
 * A class that represents the Brighten transformation on an image.
 * It is represented by the source image, destination image, and split (if required).
 * It also stores the value to be brightened by.
 */
public class Brighten implements ImageCommand {
  private final String source;
  private final String result;
  private final int val;

  /**
   * Constructor function for the Brighten transformation. Requires an array of Strings, each
   * in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public Brighten(String[] args) {
    if (args.length != 3) {
      this.val = Integer.parseInt(args[1]);
      this.source = args[2];
      this.result = args[3];
    } else {
      throw new IllegalArgumentException("Error: Illegal number of arguments brighten!");
    }
  }

  @Override
  public int apply(ImageMap images) {
    return images.apply(source, result, img -> img.brighten(val), 0);
  }
}
