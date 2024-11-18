package model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ImageMapModel implements ImageMap {

  private final Map<String, ImageModel> images;

  public ImageMapModel() {
    this.images = new HashMap<>();
  }

  /**
   * Higher order function that takes a source and destination images.
   * Applies a transformation on the source image and saves it as the destination image.
   *
   * @param srcName  variable name of source image
   * @param destName variable name of destination image
   * @param func     the function/transformation to be applied on the image
   * @return 0 if success, 1 if failure
   */
  private int applyNoSplit(String srcName, String destName, Consumer<ImageModel> func) {
    ImageModel destImg = images.get(srcName).deepCopy();
    func.accept(destImg);
    images.put(destName, destImg);

    if (images.containsKey(destName)) {
      return 0;
    } else {
      return 1;
    }
  }

  @Override
  public int apply(String srcName, String destName, Consumer<ImageModel> func, int ratio) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return 1;
    }

    if (ratio <= 0 || ratio >= 100) {
      return applyNoSplit(srcName, destName, func);
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

  @Override
  public ImageModel get(String srcName) {
    return images.get(srcName);
  }

  @Override
  public void put(String name, ImageModel img) {
    images.put(name, img);
  }

  @Override
  public boolean containsKey(String name) {
    return images.containsKey(name);
  }
}
