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

  /**
   * Higher order function that takes a source and destination images.
   * Applies a transformation on the source image and saves it as the destination image.
   * It also requires the ratio to implement split view. The transformation will only be applied
   * on the first ratio percentage of pixels from the left.
   *
   * @param srcName  variable name of source image
   * @param destName variable name of destination image
   * @param func     the function/transformation to be applied on the image
   * @param ratio    the fraction of pixels from the left, to be operated on
   * @return 0 if success, 1 if failure
   */
  public static int applySplit(Map<String, ImageModel> images, String srcName, String destName,
                               Consumer<ImageModel> func, int ratio) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return 1;
    }

    ImageModel temp = images.get(srcName).deepCopy();
    ImageModel[] destImg = temp.splitImage(ratio);

    func.accept(destImg[0]);
    destImg[0].mergeSplits(destImg[1]);

    images.put(destName, destImg[0]);

    if (images.containsKey(destName)) {
      return 0;
    } else {
      return 1;
    }
  }
}
