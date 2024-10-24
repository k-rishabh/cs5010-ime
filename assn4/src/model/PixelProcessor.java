package model;

import java.util.function.Consumer;

import model.pixel.PixelADT;

/**
 * Utility class that provides a higher order function to apply operations on
 * all pixels in a 2D array of pixels.
 * This is used for performing operations on each pixel in an image, such as adjusting brightness,
 * applying filters, or converting to grayscale.
 */
public class PixelProcessor {
  public static void apply(PixelADT[][] pixels, Consumer<PixelADT> func) {
    for (PixelADT[] pixelRow : pixels) {
      for (PixelADT pixel : pixelRow) {
        func.accept(pixel);
      }
    }
  }
}
