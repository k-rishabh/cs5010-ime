package controller.command;

import model.ImageMap;

/**
 * A class that represents the Downscale transformation on an image.
 * It is represented by the source image, destination image, and the new height and width.
 */
public class Downscale implements ImageCommand {
  private final String source;
  private final String result;
  private final int newHeight;
  private final int newWidth;

  /**
   * Constructor function for the Downscale transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public Downscale(String[] args) {
    if (args.length != 5) {
      throw new IllegalArgumentException("Illegal number of arguments in downscale command!");
    }

    this.newHeight = Integer.parseInt(args[1]);
    this.newWidth = Integer.parseInt(args[2]);
    this.source = args[3];
    this.result = args[4];
  }

  @Override
  public int apply(ImageMap images) {
    if (images.get(source).getHeight() < newHeight || images.get(source).getWidth() < newWidth
            || newHeight <= 0 || newWidth <= 0) {
      return 1;
    }
    return images.apply(source, result, img -> img.downscale(newHeight, newWidth), 0);
  }
}
