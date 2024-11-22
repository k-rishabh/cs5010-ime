package model;

import java.util.function.Consumer;

/**
 * Represents a model of our program. It includes a mapping from string names to images. It is
 * used by the controller to store all the current images the user has used.
 */
public interface ImageMap {
  /**
   * Higher order function that takes a source and destination images.
   * Applies a transformation on the source image and saves it as the destination image.
   * It also requires the ratio to implement split view. The transformation will only be applied
   * on the first ratio percentage of pixels from the left.
   *`
   * @param srcName  variable name of source image
   * @param destName variable name of destination image
   * @param func     the function/transformation to be applied on the image
   * @param ratio    the fraction of pixels from the left, to be operated on
   * @return 0 if success, 1 if failure
   */
  int apply(String srcName, String destName, Consumer<ImageModel> func, int ratio);

  /**
   * Higher order function that takes a source and destination images.
   * Applies a transformation on the source image and saves it as the destination image, by
   * using the mask image. It only applies the transformation on those pixels that are black
   * in the mask image. Hence, mask and source image must be the same dimension.
   *`
   * @param srcName  variable name of source image
   * @param destName variable name of destination image
   * @param maskImage variable name of the mask image
   * @param func     the function/transformation to be applied on the image
   * @return 0 if success, 1 if failure
   */
  int applyMask(String srcName, String destName, String maskImage,
                Consumer<ImageModel> func);

  /**
   * Retrieves the ImageModel by its image name.
   *
   * @param srcName the image name to retrieve.
   * @return the image associated with the given image name.
   */
  ImageModel get(String srcName);

  /**
   * Stores an ImageModel in the map with an image name.
   *
   * @param name the image name to associate with the ImageModel.
   * @param img  the ImageModel to store.
   */
  void put(String name, ImageModel img);

  /**
   * Checks if an ImageModel with an image name exists in the HashMap.
   *
   * @param name the name of the image.
   * @return true if an ImageModel with the image name exists, false otherwise.
   */
  boolean containsKey(String name);
}
