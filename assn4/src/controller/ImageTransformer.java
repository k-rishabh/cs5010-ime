package controller;

import java.util.function.Consumer;

import model.Image;

public class ImageTransformer extends ImageHandler {
  public static int apply(String srcName, String destName, Consumer<Image> func) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return 1;
    }

    Image destImg = images.get(srcName).deepCopy();
    func.accept(destImg);
    images.put(destName, destImg);

    if (images.containsKey(destName)) {
      return 0;
    } else {
      return 1;
    }
  }
}
