package model;

import java.util.function.Function;

import model.pixel.Pixel;
import model.pixel.RGBPixel;
import util.HaarTransform;

public class RGBImageV2 extends RGBImageV1 implements ImageV2 {
  public RGBImageV2(int height, int width) {
    super(height, width);
  }

  private RGBImageV2(int[][] packedPixels) {
    super(packedPixels);
  }

  @Override
  public ImageV2 compress(int ratio) {
    int h = this.getHeight();
    int w = this.getWidth();

    int[][] red = new int[h][w];
    int[][] green = new int[h][w];
    int[][] blue = new int[h][w];

    int nz_init = 0;
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        red[i][j] = this.pixels[i][j].getRed();
        green[i][j] = this.pixels[i][j].getGreen();
        blue[i][j] = this.pixels[i][j].getBlue();

        if (red[i][j] == 0) {
          nz_init++;
        }

        if (green[i][j] == 0) {
          nz_init++;
        }

        if (blue[i][j] == 0) {
          nz_init++;
        }
      }
    }

    red = HaarTransform.haar2D(red);
    green = HaarTransform.haar2D(green);
    blue = HaarTransform.haar2D(blue);

    return null;
  }

  @Override
  public ImageV2 decompress() {
    int h = this.getHeight();
    int w = this.getWidth();

    int[][] red = new int[h][w];
    int[][] green = new int[h][w];
    int[][] blue = new int[h][w];

    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        red[i][j] = this.pixels[i][j].getRed();
        green[i][j] = this.pixels[i][j].getGreen();
        blue[i][j] = this.pixels[i][j].getBlue();
      }
    }

    red = HaarTransform.haar2DInverse(red);
    green = HaarTransform.haar2DInverse(green);
    blue = HaarTransform.haar2D(blue);

    int[][] newPixels = new int[red.length][red[0].length];
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        newPixels[i][j] = (red[i][j] << 16) | (green[i][j] << 8) | (blue[i][j]);
      }
    }

    return new RGBImageV2(newPixels);
  }

  public int[][] getFrequencies(AbstractImage rgbImage) {
    int[] reds = new int[256];
    int[] greens = new int[256];
    int[] blues = new int[256];

    for (int i = 0; i < rgbImage.getHeight(); i++) {
      for (int j = 0; j < rgbImage.getWidth(); j++) {
        reds[rgbImage.pixels[i][j].getRed()]++;
        greens[rgbImage.pixels[i][j].getGreen()]++;
        blues[rgbImage.pixels[i][j].getBlue()]++;
      }
    }

    return new int[][]{reds, greens, blues};
  }

  @Override
  public void colorCorrect() {
    int[][] frequencies = getFrequencies(this);
    int[] red = frequencies[0];
    int[] green = frequencies[1];
    int[] blue = frequencies[2];

    int redPeak = findChannelPeak(red);
    int greenPeak = findChannelPeak(green);
    int bluePeak = findChannelPeak(blue);

    int avgPeak = (redPeak + greenPeak + bluePeak) / 3;
    Function<Pixel, Integer> colorCorrect = p -> {

      int updatedR = Math.max(0, Math.min(255, p.getRed() + avgPeak - redPeak));
      int updatedG = Math.max(0, Math.min(255, p.getGreen() + avgPeak - greenPeak));
      int updatedB = Math.max(0, Math.min(255, p.getBlue() + avgPeak - bluePeak));

      return (updatedR << 16) | (updatedG << 8) | (updatedB);
    };

    this.applyTransform(colorCorrect);
  }

  private int findChannelPeak(int[] histogram) {
    int peakValue = 0;
    int peakPosition = 0;
    for (int i = 10; i <= 245; i++) {
      if (histogram[i] > peakValue) {
        peakValue = histogram[i];
        peakPosition = i;
      }
    }

    return peakPosition;
  }

  private void applyTransform(Function<Pixel, Integer> transformFunction) {
    int height = this.pixels.length;
    int width = this.pixels[0].length;
    int[][] result = new int[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        result[i][j] = transformFunction.apply(this.pixels[i][j]);
        RGBPixel pixel = new RGBPixel(result[i][j]);
        this.pixels[i][j] = pixel;
      }
    }
  }

  @Override
  public void levelsAdjust(int black, int mid, int white) {
    Function<Pixel, Integer> levelAdjust = p -> {
      int updatedR = this.fittingProcess(black, mid, white, p.getRed());
      int updatedG = this.fittingProcess(black, mid, white, p.getGreen());
      int updatedB = this.fittingProcess(black, mid, white, p.getBlue());

      return (updatedR << 16) | (updatedG << 8) | (updatedB);
    };

    this.applyTransform(levelAdjust);
  }

  private int fittingProcess(int black, int mid, int white, int signal) {
    double valueA = Math.pow(black, 2) * (mid - white) - black * (Math.pow(mid, 2)
            - Math.pow(white, 2)) + white * Math.pow(mid, 2) - mid * Math.pow(white, 2);

    double valueAa = -black * (128 - 255) + 128 * white - 255 * mid;

    double valueAb = Math.pow(black, 2) * (128 - 255) + 255 * Math.pow(mid, 2)
            - 128 * Math.pow(white, 2);

    double valueAc = Math.pow(black, 2) * (255 * mid - 128 * white)
            - black * (255 * Math.pow(mid, 2) - 128 * Math.pow(white, 2));

    double a = valueAa / valueA;

    double b = valueAb / valueA;

    double c = valueAc / valueA;
    return Math.max(0, Math.min(255, (int) (a * Math.pow(signal, 2) + b * signal + c)));
  }

  @Override
  public ImageV2[] splitImage(int ratio) {
    if (ratio < 0 || ratio > 100) {
      throw new IllegalArgumentException("Split ratio must be between 0 and 100!");
    } else if (ratio == 0 || ratio == 100) {
      return new ImageV2[]{this};
    }

    int h = this.getHeight();
    int w = this.getWidth();
    int cut = (int) (w * ratio / 100.0);

    ImageV2[] res = new RGBImageV2[2];
    res[0] = new RGBImageV2(h, cut);
    res[1] = new RGBImageV2(h, w - cut);

    // code

    return res;
  }

  @Override
  public void mergeRight(ImageV2 img) {
    if (this.getHeight() != img.getHeight()) {
      throw new IllegalArgumentException("Cannot merge images of different heights!");
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
        merged[i][j + w] = this.pixels[i][j];
      }
    }

    this.pixels = merged;
  }
}
