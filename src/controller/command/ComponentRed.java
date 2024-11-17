package controller.command;

import java.util.Map;

import controller.filter.CompRedFilter;
import model.ImageMapInterface;
import model.ImageModel;
import util.ImageTransformer;

/**
 * A class that represents the Red Component transformation on an image.
 * It is represented by the source image, destination image, and split (if required).
 */
public class ComponentRed implements ImageCommand {
  private final String[] args;

  /**
   * Constructor function for the Component Red transformation. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public ComponentRed(String[] args) {
    if (args.length == 4 && !args[2].equals("split")) {
      throw new IllegalArgumentException("Error: Illegal argument in component-red!");
    }
    else if (args.length == 2 || args.length == 4) {
      this.args = args;
    }
    else{
      throw new IllegalArgumentException("Error: Illegal number of arguments in component-red!");
    }

  }

  @Override
  public void apply(ImageMapInterface images) {
    images.redComponent(args);
  }

}
