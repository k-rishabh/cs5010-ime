package controller.command;

import java.util.Map;

import controller.ImageCommand;
import model.ImageModel;
import util.ImageUtil;

public class Save implements ImageCommand {
  String filePath;
  String imageName;

  public Save(String[] args) {
    if (args.length == 3) {
      filePath = args[1];
      imageName = args[2];
    } else {
      throw new IllegalArgumentException("Unknown number of arguments for load command!");
    }
  }

  @Override
  public int apply(Map<String, ImageModel> images) {
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
