package model;

import java.lang.reflect.InvocationTargetException;

public abstract class Image implements ImageADT {
  protected PixelADT[][] pixels;

  @Override
  public int getHeight() {
    return pixels.length;
  }

  @Override
  public int getWidth() {
    return pixels[0].length;
  }

  @Override
  public PixelADT getPixel(int i, int j) {
    return pixels[i][j];
  }

  @Override
  public void setPixel(int i, int j, PixelADT pixel) {
    pixels[i][j] = pixel;
  }

  @Override
  public void valueComponent() {
    PixelProcessor.apply(pixels, p -> p.showValue());
  }

  @Override
  public void intensityComponent() {
    PixelProcessor.apply(pixels, p -> p.showIntensity());
  }

  @Override
  public void lumaComponent() {
    PixelProcessor.apply(pixels, p -> p.showLuma());
  }

  @Override
  public void horizontalFlip() {

    int width = pixels[0].length;
    int height = pixels.length;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width / 2; j++) {
        PixelADT temp = pixels[i][j];
        pixels[i][j] = pixels[i][width - 1 - j];
        pixels[i][width - 1 - j] = temp;
      }
    }
  }

  @Override
  public void verticalFlip() {

    int width = pixels[0].length;
    int height = pixels.length;

    for (int i = 0; i < height / 2; i++) {
      PixelADT[] temp = pixels[i];
      pixels[i] = pixels[height - 1 - i];
      pixels[height - 1 - i] = temp;
    }
  }

  @Override
  public void brighten(int val) {
    PixelProcessor.apply(pixels, p -> p.brighten(val));
  }

  @Override
  public void blur(double[][] filter) {

  }

  @Override
  public void sharpen(double[][] filter) {

  }

  @Override
  public void sepia() {
    PixelProcessor.apply(pixels, p -> p.applySepia());
  }

  @Override
  abstract public Image[] split();

  @Override
  abstract public Image combine();

  public abstract Image copy();

  public void setPixels(PixelADT[][] newPixels) {
    this.pixels = newPixels;
  }
}
