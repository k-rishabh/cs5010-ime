package model;

import java.util.function.Function;

import model.filter.Filter;
import model.pixel.PixelADT;
import model.pixel.RGBPixel;
import util.PixelProcessor;


/**
 * Class representing an RGB image, extending the abstract class Image.
 * This class provides the implementation for applying filters, splitting the image
 * into color components, and creating deep copy of the image.
 */
public class RGBImage extends Image {

  /**
   * Constructs an empty (black) RGBImage with the given height and width.
   *
   * @param height the height of the image
   * @param width  the width of the image
   */
  public RGBImage(int height, int width) {
    this.pixels = new PixelADT[height][width];
  }

  public RGBImage(PixelADT[][] pixels){
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

    this.pixels = new PixelADT[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.pixels[i][j] = new RGBPixel(packedPixels[i][j]);
      }
    }
  }

  @Override
  public Image deepCopy() {
    int height = this.pixels.length;
    int width = this.pixels[0].length;

    Image copyImage = new RGBImage(height, width);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        copyImage.pixels[i][j] = pixels[i][j].deepCopy();
      }
    }

    return copyImage;
  }

  @Override
  public Image[] split() {
    Image redTint = this.deepCopy();
    Image greenTint = this.deepCopy();
    Image blueTint = this.deepCopy();

    PixelProcessor.apply(redTint.pixels, p -> p.applyRedTint());
    PixelProcessor.apply(greenTint.pixels, p -> p.applyGreenTint());
    PixelProcessor.apply(blueTint.pixels, p -> p.applyBlueTint());

    return new Image[]{redTint, greenTint, blueTint};
  }

  /**
   * Applies the given Filter to the image. The filter matrix is applied
   * to each pixel and the surrounding pixels, and the result is stored in a temporary
   * array before being assigned back to the image.
   *
   * @param filter the filter to be applied to the image.
   */
  protected void applyFilter(Filter filter) {
    double[][] matrix = filter.getFilter();

    int height = pixels.length;
    int width = pixels[0].length;
    int c = matrix.length;

    PixelADT[][] temp = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        double redVal = 0;
        double greenVal = 0;
        double blueVal = 0;

        for (int x = Math.max(0, c / 2 - i); x < Math.min(c, height + c / 2 - i); x++) {
          for (int y = Math.max(0, c / 2 - j); y < Math.min(c, width + c / 2 - j); y++) {
            double[] filterValues =
                    this.pixels[x + i - c / 2][y + j - c / 2].applyFilter(matrix[x][y]);

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


  public int[][] getFrequencies(Image rgbImage) {
    int[] reds = new int[256];
    int[] greens = new int[256];
    int[] blues = new int[256];

    for (int i = 0; i < rgbImage.getHeight(); i++) {
      for (int j = 0; j < rgbImage.getWidth(); j++) {
        reds[(rgbImage.getPackedPixel(i, j) >> 16) & 0xFF]++;
        greens[(rgbImage.getPackedPixel(i, j) >> 8) & 0xFF]++;
        blues[(rgbImage.getPackedPixel(i, j)) & 0xFF]++;
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
    Function<PixelADT, Integer> colorCorrect = p -> {

      int updatedR = Math.max(0, Math.min(255,((p.getPacked() >> 16) & 0xFF) + avgPeak - redPeak));
      int updatedG = Math.max(0, Math.min(255,((p.getPacked() >> 8) & 0xFF) + avgPeak - greenPeak));
      int updatedB = Math.max(0, Math.min(255,(p.getPacked() & 0xFF) + avgPeak - bluePeak));

      return (updatedR<< 16) | (updatedG << 8) | (updatedB);
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

  private void applyTransform(Function<PixelADT, Integer> transformFunction) {
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
    Function<PixelADT, Integer> levelAdjust = p -> {
      int updatedR = this.fittingProcess(black, mid, white, ((p.getPacked() >> 16) & 0xFF));
      int updatedG = this.fittingProcess(black, mid, white, ((p.getPacked() >> 8) & 0xFF));
      int updatedB = this.fittingProcess(black, mid, white, ((p.getPacked()) & 0xFF));

      return (updatedR<< 16) | (updatedG << 8) | (updatedB);
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
    return Math.max(0, Math.min(255,(int) (a * Math.pow(signal, 2) + b * signal + c)));
  }
}
