package controller.command;

import java.util.Map;

import model.ImageMapInterface;
import model.ImageModel;

/**
 * A class that represents the RGB Split transformation on an image.
 * It is represented by the source image, and the red, green, and blue destination images.
 */
public class SplitRGB implements ImageCommand {
  private final String source;
  private final String resultRed;
  private final String resultGreen;
  private final String resultBlue;

  /**
   * Constructor function for the RGB Split transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public SplitRGB(String[] args) {
    if (args.length == 4) {
      this.source = args[0];
      this.resultRed = args[1];
      this.resultGreen = args[2];
      this.resultBlue = args[3];
    } else {
      throw new IllegalArgumentException(
              "Error: Illegal number of arguments in rgb-split!");
    }
  }

  @Override
  public void apply(ImageMapInterface images) {
    images.rgbSplit(source, resultRed, resultGreen, resultBlue);
  }
}
