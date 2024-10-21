package model;

import java.util.function.Consumer;

public class PixelProcessor {
  public static <T> Pixel[][] apply(Pixel[][] pixels, Consumer<Pixel> func) {
    for (Pixel[] pixelRow : pixels) {
      for (Pixel pixel : pixelRow) {
        func.accept(pixel);
      }
    }

    return pixels;
  }
}
