package model;

import java.util.function.Function;

import controller.filter.Filter;
import controller.filter.TintBlueFilter;
import controller.filter.TintGreenFilter;
import controller.filter.TintRedFilter;
import model.pixel.Pixel;
import model.pixel.RGBPixel;
import util.HaarTransform;
import util.Histogram;
import util.PixelProcessor;


/**
 * Class representing an RGB image, extending the abstract class Image.
 * This class provides the implementation for applying filters, splitting the image
 * into color components, and creating deep copy of the image.
 */
public class RGBImage extends AbstractImage {
  /**
   * Constructs an empty (black) RGBImage with the given height and width.
   *
   * @param height the height of the image
   * @param width  the width of the image
   */
  public RGBImage(int height, int width) {
    this.pixels = new Pixel[height][width];
  }

  public RGBImage(Pixel[][] pixels) {
    this.pixels = pixels;
  }

  /**
   * Constructs an RGBImage from a 2D array of packed integer RGB values.
   * The packed values are unpacked into individual red, green, and blue components.
   *
   * @param packedPixels a 2D array of packed RGB values
   */
  public RGBImage(int[][] packedPixels) {
    int height = packedPixels.length;
    int width = packedPixels[0].length;

    this.pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.pixels[i][j] = new RGBPixel(packedPixels[i][j]);
      }
    }
  }

  @Override
  public ImageModel histogram(){
    Histogram histogram = new Histogram();
    return histogram.createHistogram(this);
  }

  @Override
  public RGBImage deepCopy() {
    int height = this.pixels.length;
    int width = this.pixels[0].length;

    RGBImage copyImage = new RGBImage(height, width);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        copyImage.pixels[i][j] = new RGBPixel(pixels[i][j].getPacked());
      }
    }

    return copyImage;
  }

  /**
   * Applies the given Filter to the image. The filter matrix is applied
   * to each pixel and the surrounding pixels, and the result is stored in a temporary
   * array before being assigned back to the image.
   *
   * @param filter the filter to be applied to the image.
   */
  public void applyImageFilter(Filter filter) {
    double[][] matrix = filter.getFilter();

    int height = pixels.length;
    int width = pixels[0].length;
    int c = matrix.length;

    Pixel[][] temp = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        double redVal = 0;
        double greenVal = 0;
        double blueVal = 0;

        for (int x = Math.max(0, c / 2 - i); x < Math.min(c, height + c / 2 - i); x++) {
          for (int y = Math.max(0, c / 2 - j); y < Math.min(c, width + c / 2 - j); y++) {
            double[] filterValues =
                    this.getPixel(x + i - c / 2, y + j - c / 2).applyFilter(matrix[x][y]);

            redVal += filterValues[0];
            greenVal += filterValues[1];
            blueVal += filterValues[2];
          }
        }

        temp[i][j] = new RGBPixel((int) redVal, (int) greenVal, (int) blueVal);
      }
    }
    this.pixels = temp;
  }

  @Override
  public ImageModel[] splitComponents() {
    RGBImage redTint = this.deepCopy();
    RGBImage greenTint = this.deepCopy();
    RGBImage blueTint = this.deepCopy();

    PixelProcessor.apply(redTint.pixels, p -> p.applyColorFilter(new TintRedFilter()));
    PixelProcessor.apply(greenTint.pixels, p -> p.applyColorFilter(new TintGreenFilter()));
    PixelProcessor.apply(blueTint.pixels, p -> p.applyColorFilter(new TintBlueFilter()));

    return new ImageModel[]{redTint, greenTint, blueTint};
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
  public void compress(int ratio) {
    int h = this.getHeight();
    int w = this.getWidth();

    double[][] red = new double[h][w];
    double[][] green = new double[h][w];
    double[][] blue = new double[h][w];

    // initialize reds, blues, greens, and non-zero count
    int nz_init = 0;
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        red[i][j] = this.getRed(i, j);
        green[i][j] = this.getGreen(i, j);
        blue[i][j] = this.getBlue(i, j);

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

    // haar 2d transform
    red = HaarTransform.haar2D(red);
    green = HaarTransform.haar2D(green);
    blue = HaarTransform.haar2D(blue);

    // thresholding


    // inverse haar 2d transform
    red = HaarTransform.haar2DInverse(red);
    green = HaarTransform.haar2DInverse(green);
    blue = HaarTransform.haar2DInverse(blue);

    // un-padding of matrices
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        int r = (int) Math.round(red[i][j]);
        int g = (int) Math.round(green[i][j]);
        int b = (int) Math.round(blue[i][j]);

        this.pixels[i][j] = new RGBPixel(r, g, b);
      }
    }
  }

  public int[][] getFrequencies(ImageModel rgbImage) {
    int[] reds = new int[256];
    int[] greens = new int[256];
    int[] blues = new int[256];

    for (int i = 0; i < rgbImage.getHeight(); i++) {
      for (int j = 0; j < rgbImage.getWidth(); j++) {
        reds[rgbImage.getRed(i, j)]++;
        greens[rgbImage.getGreen(i, j)]++;
        blues[rgbImage.getBlue(i, j)]++;
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
  public ImageModel[] splitImage(int ratio) {
    if (ratio < 0 || ratio > 100) {
      throw new IllegalArgumentException("Split ratio must be between 0 and 100!");
    } else if (ratio == 0 || ratio == 100) {
      return new ImageModel[]{this};
    }

    int h = this.getHeight();
    int w = this.getWidth();
    int cut = (int) (w * ratio / 100.0);

    RGBImage[] res = new RGBImage[2];
    res[0] = new RGBImage(h, cut);
    res[1] = new RGBImage(h, w - cut);

    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        if (j < cut) {
          res[0].pixels[i][j] = this.pixels[i][j];
        } else {
          res[1].pixels[i][cut - j] = this.pixels[i][j];
        }
      }
    }

    return res;
  }

  @Override
  public void mergeRight(ImageModel img) {
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