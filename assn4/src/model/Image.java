package model;

import model.filter.Filter;
import model.filter.BlurFilter;
import model.filter.SharpenFilter;

import model.pixel.PixelADT;

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
  public int getPackedPixel(int i, int j) {
    return pixels[i][j].getPacked();
  }

  @Override
  public void redComponent() {
    PixelProcessor.apply(pixels, p -> p.showRed());
  }

  @Override
  public void greenComponent() {
    PixelProcessor.apply(pixels, p -> p.showGreen());
  }

  @Override
  public void blueComponent() {
    PixelProcessor.apply(pixels, p -> p.showBlue());
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
    int height = pixels.length;
    int width = pixels[0].length;

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
  public void combine(Image img) {
    for (int i = 0; i < this.pixels.length; i++) {
      for (int j = 0; j < this.pixels[i].length; j++) {
        this.pixels[i][j].addComponent(img.pixels[i][j]);
      }
    }
  }

  @Override
  public void blur() {
    Filter filter = new BlurFilter();
    this.applyFilter(filter);
  }

  @Override
  public void sharpen() {
    Filter filter = new SharpenFilter();
    this.applyFilter(filter);
  }

  @Override
  public void sepia() {
    PixelProcessor.apply(pixels, p -> p.applySepia());
  }

  @Override
  abstract public Image deepCopy();

  @Override
  abstract public Image[] split();

  abstract protected void applyFilter(Filter filter);
}
