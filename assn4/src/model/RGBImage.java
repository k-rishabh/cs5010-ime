package model;

import model.filter.Filter;
import model.pixel.PixelADT;
import model.pixel.RGBPixel;

/**
 * Class representing an RGB image, extending the abstract Image class.
 * This class provides the implementation for applying filters, splitting the image
 * into color components, and creating deep copy of the image.
 */
public class RGBImage extends Image {

  /**
   * Constructs an empty RGBImage with the given height and width.
   *
   * @param height the height of the image
   * @param width  the width of the image
   */
  public RGBImage(int height, int width) {
    this.pixels = new PixelADT[height][width];
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

}
