package controller.command;

import java.util.Map;

import model.ImageMapInterface;
import model.ImageModel;
import util.ImageUtil;

/**
 * A class that represents the load command for an image.
 * It is represented by the file path and the name of the image.
 */
public class Load implements ImageCommand {
  String filePath;
  String imageName;

  /**
   * Constructor function for the load command. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public Load(String[] args) {
    if (args.length == 2) {
      filePath = args[0];
      imageName = args[1];
    } else {
      throw new IllegalArgumentException("Unknown number of arguments for load command!");
    }
  }

  @Override
  public void apply(ImageMapInterface images) {
    images.load(imageName, filePath);
  }
}
