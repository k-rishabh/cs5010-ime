package model;

import controller.filter.Filter;

import model.pixel.Pixel;
import util.PixelProcessor;

/**
 * This abstract class implements ImageADT and represents an image, which is
 * composed of a 2D array of pixels.
 * It provides methods for various image processing operations such as color transformations,
 * flipping, and applying filters. This class serves as a base for more specific image types
 * and requires the implementation of certain methods such as split, combine,
 * and deepCopy.
 */
public abstract class AbstractImage implements ImageModel {
  protected Pixel[][] pixels;

  @Override
  public int getHeight() {
    return pixels.length;
  }

  @Override
  public int getWidth() {
    return pixels[0].length;
  }

  @Override
  public int getRed(int i, int j) {
    return pixels[i][j].getRed();
  }

  @Override
  public int getGreen(int i, int j) {
    return pixels[i][j].getGreen();
  }

  @Override
  public int getBlue(int i, int j) {
    return pixels[i][j].getBlue();
  }

  @Override
  public int getPackedPixel(int i, int j) {
    return pixels[i][j].getPacked();
  }

  @Override
  public Pixel getPixel(int i, int j) {
    return pixels[i][j];
  }

  @Override
  public void applyColorFilter(Filter filter) {
    PixelProcessor.apply(pixels, p -> p.applyColorFilter(filter));
  }

  @Override
  public void valueComponent() {
    PixelProcessor.apply(pixels, p -> p.applyValueFilter());
  }

  @Override
  public void brighten(int val) {
    PixelProcessor.apply(pixels, p -> p.brighten(val));
  }

  @Override
  public void horizontalFlip() {
    int height = pixels.length;
    int width = pixels[0].length;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width / 2; j++) {
        Pixel temp = pixels[i][j];
        pixels[i][j] = pixels[i][width - 1 - j];
        pixels[i][width - 1 - j] = temp;
      }
    }
  }

  @Override
  public void verticalFlip() {
    int height = pixels.length;

    for (int i = 0; i < height / 2; i++) {
      Pixel[] temp = pixels[i];
      pixels[i] = pixels[height - 1 - i];
      pixels[height - 1 - i] = temp;
    }
  }

  @Override
  abstract public ImageModel deepCopy();

  @Override
  abstract public void applyImageFilter(Filter filter);

  @Override
  abstract public ImageModel[] splitComponents();

  @Override
  abstract public void combineComponents(ImageModel img);

}
