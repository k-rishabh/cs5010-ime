package model;

import model.filter.Filter;
import model.filter.TintBlue;
import model.filter.TintGreen;
import model.filter.TintRed;
import model.pixel.Pixel;
import model.pixel.RGBPixel;
import util.PixelProcessor;


/**
 * Class representing an RGB image, extending the abstract class Image.
 * This class provides the implementation for applying filters, splitting the image
 * into color components, and creating deep copy of the image.
 */
public class RGBImageV1 extends AbstractImage {
  /**
   * Constructs an empty (black) RGBImage with the given height and width.
   *
   * @param height the height of the image
   * @param width  the width of the image
   */
  public RGBImageV1(int height, int width) {
    this.pixels = new Pixel[height][width];
  }

  public RGBImageV1(Pixel[][] pixels){
    this.pixels = pixels;
  }

  /**
   * Constructs an RGBImage from a 2D array of packed integer RGB values.
   * The packed values are unpacked into individual red, green, and blue components.
   *
   * @param packedPixels a 2D array of packed RGB values
   */
  public RGBImageV1(int[][] packedPixels) {
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
  public RGBImageV1 deepCopy() {
    int height = this.pixels.length;
    int width = this.pixels[0].length;

    RGBImageV1 copyImage = new RGBImageV1(height, width);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        copyImage.pixels[i][j] = new RGBPixel(pixels[i][j].getPacked());
      }
    }

    return copyImage;
  }

  @Override
  public ImageV1[] splitComponents() {
    RGBImageV1 redTint = this.deepCopy();
    RGBImageV1 greenTint = this.deepCopy();
    RGBImageV1 blueTint = this.deepCopy();

    PixelProcessor.apply(redTint.pixels, p -> p.applyColorFilter(new TintRed()));
    PixelProcessor.apply(greenTint.pixels, p -> p.applyColorFilter(new TintGreen()));
    PixelProcessor.apply(blueTint.pixels, p -> p.applyColorFilter(new TintBlue()));

    return new ImageV1[]{redTint, greenTint, blueTint};
  }

  @Override
  public void combineComponents(ImageV1 img) {
    if (!(img instanceof RGBImageV1)) {
      throw new IllegalArgumentException("Cannot combine images of two different color channels!");
    }

    RGBImageV1 rgb = (RGBImageV1) img;
    for (int i = 0; i < this.pixels.length; i++) {
      for (int j = 0; j < this.pixels[i].length; j++) {
        this.pixels[i][j].maximizeComponents(rgb.pixels[i][j]);
      }
    }
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

    Pixel[][] temp = new RGBPixel[height][width];

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
