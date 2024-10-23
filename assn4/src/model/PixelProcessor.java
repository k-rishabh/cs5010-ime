package model;

import java.util.function.Consumer;

import model.pixel.PixelADT;

public class PixelProcessor {
  public static void apply(PixelADT[][] pixels, Consumer<PixelADT> func) {
    for (PixelADT[] pixelRow : pixels) {
      for (PixelADT pixel : pixelRow) {
        func.accept(pixel);
      }
    }
  }
}
