//package util;
//
//import java.util.function.Consumer;
//
//import controller.ImageHandler;
//import model.ImageV1;
//
///**
// * Functional utility class that is responsible for applying a simple transformation to an Image.
// */
//public class ImageTransformer extends ImageHandler {
//  /**
//   * Higher order function that takes a source and destination images.
//   * Applies a transformation on the source image and saves it as the destination image.
//   *
//   * @param srcName  variable name of source image
//   * @param destName variable name of destination image
//   * @param func     the function/transformation to be applied on the image
//   * @return 0 if success, 1 if failure
//   */
//  public static int apply(String srcName, String destName, Consumer<ImageV1> func) {
//    if (images.get(srcName) == null) {
//      System.out.println("Image " + srcName + " not found!");
//      return 1;
//    }
//
//    ImageV1 destImg = images.get(srcName).deepCopy();
//    func.accept(destImg);
//    images.put(destName, destImg);
//
//    if (images.containsKey(destName)) {
//      return 0;
//    } else {
//      return 1;
//    }
//  }
//}
