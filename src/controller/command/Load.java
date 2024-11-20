package controller.command;

import model.ImageMap;
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
    if (args.length == 3) {
      filePath = args[1];
      imageName = args[2];
    } else {
      throw new IllegalArgumentException("Unknown number of arguments for load command!");
    }
  }

  @Override
  public int apply(ImageMap images) {
    String extension = filePath.substring(filePath.lastIndexOf('.'));

    if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".png")) {
      images.put(imageName, ImageUtil.loadImageRaster(filePath));
    } else if (extension.equalsIgnoreCase(".ppm")) {
      images.put(imageName, ImageUtil.loadImageRaw(filePath));
    } else {
      System.out.printf("Error: Did not recognize file extension: %s!\n", extension);
      return 1;
    }

    return 0;
  }
}
