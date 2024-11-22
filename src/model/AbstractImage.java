package model;

import controller.filter.Filter;

import model.pixel.Pixel;
import util.PixelProcessor;

/**
 * This abstract class implements ImageModel and represents an image, which is
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
  public void combineComponents(ImageModel img) {
    for (int i = 0; i < this.pixels.length; i++) {
      for (int j = 0; j < this.pixels[i].length; j++) {
        this.getPixel(i, j).maximizeComponents(img.getPixel(i, j));
      }
    }
  }

  @Override
  public ImageModel[] splitImage(int ratio) {
    if (ratio <= 0 || ratio >= 100) {
      return new ImageModel[]{this};
    }

    int h = this.getHeight();
    int w = this.getWidth();
    int cut = (int) Math.round(w * ratio / 100.0);

    RGBImage[] res = new RGBImage[2];
    res[0] = new RGBImage(h, cut);
    res[1] = new RGBImage(h, w - cut);

    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        if (j < cut) {
          res[0].pixels[i][j] = this.pixels[i][j];
        } else {
          res[1].pixels[i][j - cut] = this.pixels[i][j];
        }
      }
    }

    return res;
  }

  @Override
  public void mergeSplits(ImageModel img) {
    if (this.getHeight() != img.getHeight()) {
      throw new IllegalArgumentException("Images must have same height to be merged!");
    }

    int h = this.getHeight();
    int cut = this.getWidth();
    int w = this.getWidth() + img.getWidth();

    Pixel[][] merged = new Pixel[h][w];
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < cut; j++) {
        merged[i][j] = this.pixels[i][j];
      }

      for (int j = 0; j < w - cut; j++) {
        merged[i][j + cut] = img.getPixel(i, j);
      }
    }

    this.pixels = merged;
  }

  @Override
  public abstract ImageModel deepCopy();

  @Override
  public abstract void applyImageFilter(Filter filter);

  @Override
  public abstract ImageModel[] splitComponents();

  @Override
  public abstract void downscale(int newHeight, int newWidth);

  @Override
  public abstract ImageModel applyMasking(ImageModel maskImage,ImageModel operatedImage);
}
