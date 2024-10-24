package util;

import java.util.function.Consumer;

import model.pixel.PixelADT;

/**
 * Functional utility class that is responsible for applying operations on a 2D array of pixels.
 * This is used for performing operations on each pixel in an image, such as adjusting brightness,
 * applying filters, or converting to grayscale.
 */
public class PixelProcessor {
  /**
   * Higher order function that takes as input the pixel array and applies an operation on all.
   *
   * @param pixels the pixel array to be transformed
   * @param func   the function/transformation to be applied
   */
  public static void apply(PixelADT[][] pixels, Consumer<PixelADT> func) {
    for (PixelADT[] pixelRow : pixels) {
      for (PixelADT pixel : pixelRow) {
        func.accept(pixel);
      }
    }
  }

}
