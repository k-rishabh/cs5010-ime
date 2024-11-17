package controller.command;

import java.util.Map;

import model.ImageMapInterface;
import model.ImageModel;
import util.ImageTransformer;

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
    if (args.length == 3) {
      this.ratio = Integer.parseInt(args[0]);
      this.source = args[1];
      this.result = args[2];
    } else {
      throw new IllegalArgumentException(
              "Error: Illegal number of arguments in compress!");
    }
  }

  @Override
  public void apply(ImageMapInterface images) {
    images.compress(ratio,source,result);
  }

}
