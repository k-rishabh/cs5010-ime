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

  /**
   * Constructs an RGBImage from a 2D array of pixels of type Pixel.
   *
   * @param pixels a 2D array of pixels
   */
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
  public ImageModel histogram() {
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
        copyImage.pixels[i][j] = new RGBPixel(
                pixels[i][j].getRed(), pixels[i][j].getGreen(), pixels[i][j].getBlue());
      }
    }

    return copyImage;
  }

  @Override
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
  public void compress(int ratio) {
    int h = this.getHeight();
    int w = this.getWidth();

    int[][] reds = new int[h][w];
    int[][] greens = new int[h][w];
    int[][] blues = new int[h][w];

    // initialize reds, greens, and blues
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        reds[i][j] = this.getPixel(i, j).getRed();
        greens[i][j] = this.getPixel(i, j).getGreen();
        blues[i][j] = this.getPixel(i, j).getBlue();
      }
    }

    // compress matrices
    reds = HaarTransform.compressMatrix(reds, ratio);
    greens = HaarTransform.compressMatrix(greens, ratio);
    blues = HaarTransform.compressMatrix(blues, ratio);

    // combine matrices
    Pixel[][] compressed = new Pixel[h][w];
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        compressed[i][j] = new RGBPixel(reds[i][j], greens[i][j], blues[i][j]);
      }
    }

    this.pixels = compressed;
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

  @Override
  public void colorCorrect() {
    int[][] frequencies = Histogram.getFrequencies(this);
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
  public void levelsAdjust(int black, int mid, int white) {
    Function<Pixel, Integer> levelAdjust = p -> {
      int updatedR = this.fittingProcess(black, mid, white, p.getRed());
      int updatedG = this.fittingProcess(black, mid, white, p.getGreen());
      int updatedB = this.fittingProcess(black, mid, white, p.getBlue());

      return (updatedR << 16) | (updatedG << 8) | (updatedB);
    };

    this.applyTransform(levelAdjust);
  }

  @Override
  public void downscale(int newHeight, int newWidth) {
    if (newHeight > this.getHeight() || newWidth > this.getWidth()
            || newHeight <= 0 || newWidth <= 0) {
      throw new IllegalArgumentException("Height and width must be greater than the original.");
    }
    int existingHeight = this.getHeight();
    int existingWidth = this.getWidth();
    Pixel[][] downsizedPixels = new RGBPixel[newHeight][newWidth];
    for (int y = 0; y < newHeight; y++) {
      for (int x = 0; x < newWidth; x++) {
        float originalX = (float) x * existingWidth / newWidth;
        float originalY = (float) y * existingHeight / newHeight;

        int x1 = (int) Math.floor(originalX);
        int y1 = (int) Math.floor(originalY);
        int x2 = (int) Math.ceil(originalX);
        int y2 = (int) Math.ceil(originalY);

        x1 = Math.min(x1, existingWidth - 1);
        x2 = Math.min(x2, existingWidth - 1);
        y1 = Math.min(y1, existingHeight - 1);
        y2 = Math.min(y2, existingHeight - 1);

        RGBPixel cA = (RGBPixel) this.pixels[y1][x1];
        RGBPixel cB = (RGBPixel) this.pixels[y1][x2];
        RGBPixel cC = (RGBPixel) this.pixels[y2][x1];
        RGBPixel cD = (RGBPixel) this.pixels[y2][x2];

        float xWeight = originalX - x1;
        float yWeight = originalY - y1;

        int red = Math.round(
                (1 - xWeight) * (1 - yWeight) * cA.getRed()
                        + xWeight * (1 - yWeight) * cB.getRed()
                        + (1 - xWeight) * yWeight * cC.getRed()
                        + xWeight * yWeight * cD.getRed()
        );

        int green = Math.round(
                (1 - xWeight) * (1 - yWeight) * cA.getGreen()
                        + xWeight * (1 - yWeight) * cB.getGreen()
                        + (1 - xWeight) * yWeight * cC.getGreen()
                        + xWeight * yWeight * cD.getGreen()
        );

        int blue = Math.round(
                (1 - xWeight) * (1 - yWeight) * cA.getBlue()
                        + xWeight * (1 - yWeight) * cB.getBlue()
                        + (1 - xWeight) * yWeight * cC.getBlue()
                        + xWeight * yWeight * cD.getBlue()
        );

        downsizedPixels[y][x] = new RGBPixel(red, green, blue);
      }
    }
    this.pixels = downsizedPixels;
  }

  private Pixel blendPixel(Pixel originalPixel, Pixel processedPixel, int maskValue) {
    if (maskValue == 0) {
      return processedPixel;
    } else {
      return originalPixel;
    }
  }

  @Override
  public ImageModel applyMasking(ImageModel maskImage, ImageModel operatedImage) {
    if (!(this.getWidth() == maskImage.getWidth() && this.getHeight() == maskImage.getHeight())) {
      throw new IllegalArgumentException(
              "Original image and mask image must have the same dimensions.");
    }

    int width = this.getWidth();
    int height = this.getHeight();

    RGBImage resultMask = new RGBImage(height, width);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Pixel p = blendPixel(this.getPixel(y, x),
                operatedImage.getPixel(y, x),
                maskImage.getPixel(y, x).getRed());
        resultMask.pixels[y][x] = p;
      }
    }
    return resultMask;
  }
}
