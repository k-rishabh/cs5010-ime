package controller.command;

import java.util.Map;

import controller.ImageCommand;
import model.ImageModel;
import model.RGBImage;
import util.ImageUtil;

public class Load implements ImageCommand {
  String filePath;
  String imageName;

  public Load(String[] args) {
    if (args.length == 3) {
      filePath = args[1];
      imageName = args[2];
    } else {
      throw new IllegalArgumentException("Unknown number of arguments for load command!");
    }
  }

  @Override
  public int apply(Map<String, ImageModel> images) {
    String extension = filePath.substring(filePath.lastIndexOf('.'));

    if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".png")) {
      images.put(imageName, ImageUtil.loadImageRaster(filePath));
    } else if (extension.equalsIgnoreCase(".ppm")) {
      images.put(imageName, ImageUtil.loadImageRaw(filePath));
    } else {
      System.out.printf("Error: Did not recognize file extension: %s!\n", extension);
    }

    return 0;
  }
}
