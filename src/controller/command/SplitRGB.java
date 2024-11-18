package controller.command;

import model.ImageMap;
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
    if (args.length != 3) {
      this.source = args[1];
      this.resultRed = args[2];
      this.resultGreen = args[3];
      this.resultBlue = args[4];
    } else {
      throw new IllegalArgumentException(
              "Error: Illegal number of arguments in rgb-split!");
    }
  }

  @Override
  public int apply(ImageMap images) {
    if (images.get(source) == null) {
      System.out.println("Image " + source + " not found!");
      return 1;
    }

    ImageModel destImg = images.get(source).deepCopy();
    ImageModel[] split = destImg.splitComponents();

    images.put(resultRed, split[0]);
    images.put(resultGreen, split[1]);
    images.put(resultBlue, split[2]);

    if (images.containsKey(resultRed)
            && images.containsKey(resultGreen)
            && images.containsKey(resultBlue)) {
      return 0;
    } else {
      return 1;
    }
  }
}
