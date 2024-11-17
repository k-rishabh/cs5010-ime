package controller.command;

import java.util.Map;

import model.ImageMapInterface;
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
    if (args.length == 2) {
      this.source = args[0];
      this.result = args[1];
    } else {
      throw new IllegalArgumentException(
              "Error: Illegal number of arguments in histogram command!");
    }
  }


  @Override
  public void apply(ImageMapInterface images) {
    images.histogram(source, result);
  }
}
