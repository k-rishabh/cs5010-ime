package controller.command;

import model.ImageMap;
import model.ImageModel;

/**
 * A class that represents the CombineRGB transformation.
 * It is represented by the red, green, and blue source images, and the destination image.
 */
public class CombineRGB implements ImageCommand {
  private final String dest;
  private final String red;
  private final String green;
  private final String blue;

  /**
   * Constructor function for the CombineRGB transformation. Requires an array of Strings, each
   * in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public CombineRGB(String[] args) {
    if (args.length == 5) {
      dest = args[1];
      red = args[2];
      green = args[3];
      blue = args[4];
    } else {
      throw new IllegalArgumentException("Illegal number of arguments in rgb-combine!");
    }
  }

  @Override
  public int apply(ImageMap images) {
    if (images.get(red) == null) {
      System.out.println("Image " + red + " not found!");
      return 1;
    } else if (images.get(green) == null) {
      System.out.println("Image " + green + " not found!");
      return 1;
    } else if (images.get(blue) == null) {
      System.out.println("Image " + blue + " not found!");
      return 1;
    }

    ImageModel destImg = images.get(red).deepCopy();
    destImg.combineComponents(images.get(green));
    destImg.combineComponents(images.get(blue));

    images.put(dest, destImg);
    if (images.get(dest) != null) {
      return 0;
    } else {
      return 1;
    }
  }
}
