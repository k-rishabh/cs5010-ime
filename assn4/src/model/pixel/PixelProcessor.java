package model.pixel;

import java.util.function.Consumer;

public class PixelProcessor {
  public static void apply(PixelADT[][] pixels, Consumer<PixelADT> func) {
    for (PixelADT[] pixelRow : pixels) {
      for (PixelADT pixel : pixelRow) {
        func.accept(pixel);
      }
    }
  }
}
