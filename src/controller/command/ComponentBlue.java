package controller.command;

import java.util.Map;

import controller.ImageCommand;
import controller.filter.CompBlueFilter;
import model.ImageModel;
import util.ImageTransformer;

/**
 * A class that represents the Blue Component transformation on an image.
 * It is represented by the source image, destination image, and split (if required).
 */
public class ComponentBlue implements ImageCommand {
  private final String source;
  private final String result;
  private final int split;

  /**
   * Constructor function for the ComponentBlue transformation. Requires an array of Strings, each
   * in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public ComponentBlue(String[] args) {
    if (args.length != 3 && args.length != 5) {
      throw new IllegalArgumentException("Error: Illegal number of arguments in component-blue!");
    } else if (args.length == 5 && !args[3].equals("split")) {
      throw new IllegalArgumentException("Error: Illegal argument in component-blue!");
    }

    this.source = args[1];
    this.result = args[2];

    if (args.length == 5) {
      this.split = Integer.parseInt(args[4]);
    } else {
      this.split = 0;
    }
  }

  @Override
  public int apply(Map<String, ImageModel> images) {
    if (split == 0 || split == 100) {
      return ImageTransformer.apply(images, source,
              result, img -> img.applyColorFilter(new CompBlueFilter()));
    } else {
      return ImageTransformer.applySplit(images, source,
              result, img -> img.applyColorFilter(new CompBlueFilter()), split);
    }
  }
}