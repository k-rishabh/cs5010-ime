package model;

/**
 * Class that represents an RGB pixel in a 2-D image.
 */
public class RGBPixel implements Pixel {
  private int red;
  private int green;
  private int blue;

  /**
   * Constructor function to initialize the red, green, and blue values of the pixel.
   *
   * @param red   the red component
   * @param green the green component
   * @param blue  the blue component
   */
  public RGBPixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public Pixel addAllComponents(int val) {
    return new RGBPixel(red + val, green + val, blue + val);
  }

  @Override
  public Pixel mulAllComponents(float val) {
    return new RGBPixel((int) (red * val), (int) (green * val), (int) (blue * val));
  }

  @Override
  public int getMaxComponent() {
    return Math.max(red, Math.max(green, blue));
  }

  @Override
  public int getMinComponent() {
    return Math.min(red, Math.min(green, blue));
  }

  @Override
  public int getAvgComponent() {
    return (red + green + blue) / 3;
  }
}
