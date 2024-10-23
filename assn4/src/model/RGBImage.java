package model;

import model.filter.Filter;
import model.pixel.PixelADT;
import model.pixel.PixelProcessor;
import model.pixel.RGBPixel;

/**
 * Abstract class that represents an image in red, blue, and green pixels.
 * Contains implementation for
 */
public class RGBImage extends Image {

  public RGBImage(int height, int width) {
    this.pixels = new PixelADT[height][width];
  }

  public RGBImage(PixelADT[][] pixels) {
    this.pixels = pixels;
  }

  protected void applyFilter(Filter filter) {
    double[][] matrix = filter.getFilter();

    int height = pixels.length;
    int width = pixels[0].length;
    int c = matrix.length;

    PixelADT[][] temp = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        int redVal = 0;
        int greenVal = 0;
        int blueVal = 0;

        for (int x = Math.max(0, c / 2 - i); x < Math.min(c, height + c / 2 - i); x++) {
          for (int y = Math.max(0, c / 2 - j); y < Math.min(c, width + c / 2 - j); y++) {
            int[] filterValues =
                    this.pixels[x + i - c / 2][y + j - c / 2].applyFilter(matrix[x][y]);

            redVal += filterValues[0];
            greenVal += filterValues[1];
            blueVal += filterValues[2];
          }
        }

        temp[i][j] = new RGBPixel(redVal, greenVal, blueVal);
      }
    }

    this.pixels = temp;
  }

  @Override
  public Image[] split() {
    Image redTint = this.deepCopy();
    Image greenTint = this.deepCopy();
    Image blueTint = this.deepCopy();

    PixelProcessor.apply(redTint.pixels, p -> p.applyRedTint());
    PixelProcessor.apply(greenTint.pixels, p -> p.applyGreenTint());
    PixelProcessor.apply(blueTint.pixels, p -> p.applyBlueTint());

    return new Image[] {redTint, greenTint, blueTint};
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
  public Image combine() {
    return null;
  }

}
