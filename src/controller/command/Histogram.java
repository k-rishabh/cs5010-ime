package controller.command;

import model.ImageMap;
import model.ImageModel;

/**
 * A class that represents the Histogram transformation on an image.
 * It is represented by the source image, and destination image.
 */
public class Histogram implements ImageCommand {
  private final String source;
  private final String result;

  /**
   * Constructor function for the Histogram transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public Histogram(String[] args) {
    if (args.length == 3) {
      this.source = args[1];
      this.result = args[2];
    } else {
      throw new IllegalArgumentException(
              "Error: Illegal number of arguments in histogram command!");
    }
  }

  @Override
  public int apply(ImageMap images) {
    ImageModel srcImage = images.get(source).deepCopy();
    ImageModel destImage = srcImage.histogram();
    images.put(result, destImage);
    return 0;
  }
}
