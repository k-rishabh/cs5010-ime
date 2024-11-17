package controller.command;

import java.io.IOException;
import java.util.Map;

import model.ImageMapInterface;
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
    if (args.length == 2) {
      filePath = args[0];
      imageName = args[1];
    } else {
      throw new IllegalArgumentException("Unknown number of arguments for load command!");
    }
  }

  @Override
  public void apply(ImageMapInterface images) {
    try {
      images.save(imageName,filePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
