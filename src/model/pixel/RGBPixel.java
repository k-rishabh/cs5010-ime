package model.pixel;

import model.filter.Filter;

/**
 * Class that represents an RGB pixel.
 * This class stores the red, green, and blue components of the pixel
 * and provides methods for operations such as brightening, applying filters,
 * and generating grayscale representations (value, intensity, luma).
 */
public class RGBPixel implements Pixel {
  private int red;
  private int green;
  private int blue;

  /**
   * Constructor function to initialize the red, green, and blue values of the pixel.
   * The values are clamped between 0 and 255 to ensure valid RGB ranges.
   *
   * @param red   the red component
   * @param green the green component
   * @param blue  the blue component
   */
  public RGBPixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;

    this.clampValues();
  }

  /**
   * Constructor to initialize an RGBPixel using a packed RGB integer value.
   * The integer contains red, green, and blue values packed into different bits.
   *
   * @param packedRGB an integer where the first 8 bits represent blue, the next 8 bits
   *                  represent green, and the next 8 bits represent red.
   */
  public RGBPixel(int packedRGB) {
    this((packedRGB >> 16) & 0xFF, (packedRGB >> 8) & 0xFF, packedRGB & 0xFF);
  }

  /**
   * Ensures that the RGB values remain within the valid range (0-255).
   */
  private void clampValues() {
    this.red = Math.max(0, Math.min(255, this.red));
    this.green = Math.max(0, Math.min(255, this.green));
    this.blue = Math.max(0, Math.min(255, this.blue));
  }

  /**
   * Simply updates the current pixel values with the given arguments.
   *
   * @param red the new red value
   * @param green the new green value
   * @param blue the new blue value
   */
  private void setPixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.clampValues();
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public int getPacked() {
    return (this.red << 16) | (this.green << 8) | (this.blue);
  }

  @Override
  public void applyGreyscale(Greyscale type) {
    int grey;

    switch (type) {
      case RED:
        grey = this.red;
        break;
      case GREEN:
        grey = this.green;
        break;
      case BLUE:
        grey = this.blue;
        break;
      case VALUE:
        grey = Math.max(this.red, Math.max(this.green, this.blue));
        break;
      case INTENSITY:
        grey = (this.red + this.green + this.blue) / 3;
        break;
      case LUMA:
        grey = (int) ((0.2126 * this.red) + (0.7152 * this.green) + (0.0722 * this.blue));
        break;
      default:
        throw new IllegalArgumentException("Unsupported greyscale enum: " + type);
    }

    this.setPixel(grey, grey, grey);
  }

  @Override
  public void applyColorFilter(Filter filter) {
    double[][] matrix = filter.getFilter();

    if (matrix[0].length != 3) {
      throw new IllegalArgumentException("The filter must have 3 columns!");
    }

    double r = 0;
    double g = 0;
    double b = 0;

    for (double[] doubles : matrix) {
      r += this.red * doubles[0];
      g += this.green * doubles[1];
      b += this.blue * doubles[2];
    }

    this.setPixel((int) Math.round(r), (int) Math.round(g), (int) Math.round(b));
  }

  @Override
  public void brighten(int val) {
    this.setPixel(this.red + val, this.green + val, this.blue + val);
  }

  @Override
  public void maximizeComponents(Pixel pixel) {
    int r = Math.max(this.red, pixel.getRed());
    int g = Math.max(this.green, pixel.getGreen());
    int b = Math.max(this.blue, pixel.getBlue());

    this.setPixel(r, g, b);
  }

  @Override
  public double[] applyFilter(double val) {
    return new double[]{this.red * val, this.green * val, this.blue * val};
  }

}
