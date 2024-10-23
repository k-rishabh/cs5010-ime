package model;

import model.filter.Filter;
import model.filter.BlurFilter;
import model.filter.SharpenFilter;

import model.pixel.PixelADT;
import model.pixel.PixelProcessor;

public abstract class Image implements ImageADT {
  protected PixelADT[][] pixels;

//  @Override
//  public int getHeight() {
//    return pixels.length;
//  }
//
//  @Override
//  public int getWidth() {
//    return pixels[0].length;
//  }
//
//  @Override
//  public PixelADT getPixel(int i, int j) {
//    return pixels[i][j];
//  }
//
//  @Override
//  protected void setPixel(int i, int j, PixelADT pixel) {
//    pixels[i][j] = pixel;
//  }

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

  private void applyFilter(Filter filter) {
    double[][] matrix = filter.getFilter();

    int height = pixels.length;
    int width = pixels[0].length;
    int c = matrix.length;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        for (int x = Math.max(0, c / 2 - i); x < Math.min(c, height + c / 2 - i); x++) {
          for (int y = Math.max(0, c / 2 - j); y < Math.min(c, width + c / 2 - j); y++) {
            this.pixels[x + i - c / 2][y + j - c / 2].applyFilter(matrix[x][y]);
          }
        }
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
  abstract public Image[] split();

  @Override
  abstract public Image combine();

  public abstract Image deepCopy();
}
