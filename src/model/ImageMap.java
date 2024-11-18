package model;

import java.util.function.Consumer;

public interface ImageMap {
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
  int apply(String srcName, String destName, Consumer<ImageModel> func, int ratio);

  ImageModel get(String name);

  void put(String name, ImageModel img);

  boolean containsKey(String name);
}
