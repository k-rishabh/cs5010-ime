package controller.command;

import model.ImageMap;

/**
 * A class that represents the Levels Adjust transformation on an image.
 * It is represented by the source image, destination image, and split (if required).
 * It also stores the black, mid, and white values to be adjusted with.
 */
public class LevelsAdjust implements ImageCommand {

  private final String source;
  private final String result;
  private final int b;
  private final int m;
  private final int w;
  private final int split;

  /**
   * Constructor function for the Levels Adjust transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public LevelsAdjust(String[] args) {
    if (args.length != 6 && args.length != 8) {
      throw new IllegalArgumentException("Illegal number of arguments in levels-adjust command!");
    } else if (args.length == 8 && !args[6].equals("split")) {
      throw new IllegalArgumentException("Illegal argument in levels-adjust command!");
    }

    this.b = Integer.parseInt(args[1]);
    this.m = Integer.parseInt(args[2]);
    this.w = Integer.parseInt(args[3]);
    this.source = args[4];
    this.result = args[5];

    if (args.length == 8) {
      this.split = Integer.parseInt(args[7]);
    } else {
      this.split = 0;
    }
  }

  @Override
  public int apply(ImageMap images) {
    if (b > m || m > w) {
      return 1;
    }

    return images.apply(source, result, img -> img.levelsAdjust(b, m, w), split);
  }
}
