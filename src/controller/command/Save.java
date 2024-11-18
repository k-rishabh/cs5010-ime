package controller.command;

import model.ImageMap;
import model.ImageModel;
import util.ImageUtil;

/**
 * A class that represents the save command for an image.
 * It is represented by the file path and the name of the image.
 */
public class Save implements ImageCommand {
  String filePath;
  String imageName;

  /**
   * Constructor function for the save command. Requires an array of Strings,
   * each in order, representing one word of the command in correct syntax.
   *
   * @param args the parameters for the transformation
   */
  public Save(String[] args) {
    if (args.length == 3) {
      filePath = args[1];
      imageName = args[2];
    } else {
      throw new IllegalArgumentException("Unknown number of arguments for load command!");
    }
  }

  @Override
  public int apply(ImageMap images) {
    ImageModel img = images.get(imageName);

    if (img == null) {
      System.out.println("Image " + imageName + " not found!");
      return 1;
    }

    String extension = filePath.substring(filePath.lastIndexOf('.')).toLowerCase();

    if (extension.equals(".jpg") || extension.equals(".png")) {
      ImageUtil.saveImageRaster(filePath, img);
    } else if (extension.equalsIgnoreCase(".ppm")) {
      ImageUtil.saveImageRaw(filePath, img);
    } else {
      System.out.printf("Error: Did not recognize file extension: %s!\n", extension);
    }

    return 0;
  }
}
