package util;

import java.util.Map;
import java.util.function.Consumer;

import model.ImageModel;

/**
 * Functional utility class that is responsible for applying a simple transformation to an Image.
 */
public class ImageTransformer {
  /**
   * Higher order function that takes a source and destination images.
   * Applies a transformation on the source image and saves it as the destination image.
   *
   * @param srcName  variable name of source image
   * @param destName variable name of destination image
   * @param func     the function/transformation to be applied on the image
   * @return 0 if success, 1 if failure
   */
  public static int apply(Map<String, ImageModel> images, String srcName, String destName,
                          Consumer<ImageModel> func) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return 1;
    }

    ImageModel destImg = images.get(srcName).deepCopy();
    func.accept(destImg);
    images.put(destName, destImg);

    if (images.containsKey(destName)) {
      return 0;
    } else {
      return 1;
    }
  }
}
