package model.pixel;

/**
 * Class that represents an RGB pixel in a 2-D image.
 */
public class RGBPixel implements PixelADT {
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

    this.clampValues();
  }

  public RGBPixel(int packedRGB) {
    this((packedRGB >> 16) & 0xFF,(packedRGB >> 8) & 0xFF,packedRGB & 0xFF);
  }

  private void clampValues() {
    this.red = Math.max(0, Math.min(255, this.red));
    this.green = Math.max(0, Math.min(255, this.green));
    this.blue = Math.max(0, Math.min(255, this.blue));
  }

  @Override
  public int getPacked() {
    return (this.red << 16) | (this.green << 8) | (this.blue);
  }

  @Override
  public PixelADT deepCopy() {
    return new RGBPixel(this.red, this.green, this.blue);
  }

  @Override
  public void showRed() {
    this.green = this.red;
    this.blue = this.red;
  }

  @Override
  public void showGreen() {
    this.red = this.green;
    this.blue = this.green;
  }

  @Override
  public void showBlue() {
    this.red = this.blue;
    this.green = this.blue;
  }

  @Override
  public void showValue() {
    int max = Math.max(this.red, Math.max(this.green, this.blue));
    this.red = max;
    this.green = max;
    this.blue = max;
  }

  @Override
  public void showIntensity() {
    int avg = (this.red + this.green + this.blue) / 3;
    this.red = avg;
    this.green = avg;
    this.blue = avg;
  }

  @Override
  public void showLuma() {
    int luma = (int) ((0.2126 * this.red) + (0.7152 * this.green) + (0.0722 * this.blue));
    this.red = luma;
    this.green = luma;
    this.blue = luma;
  }

  @Override
  public void brighten(int val) {
    this.red += val;
    this.green += val;
    this.blue += val;

    this.clampValues();
  }

  @Override
  public void applyRedTint() {
    this.green = 0;
    this.blue = 0;
  }

  @Override
  public void applyGreenTint() {
    this.red = 0;
    this.blue = 0;
  }

  @Override
  public void applyBlueTint() {
    this.red = 0;
    this.green = 0;
  }

  @Override
  public void addComponent(PixelADT pixel) {
    this.red = Math.max(this.red, (pixel.getPacked() >> 16) & 0xFF);
    this.green = Math.max(this.green, (pixel.getPacked() >> 8) & 0xFF);
    this.blue = Math.max(this.blue, pixel.getPacked() & 0xFF);
  }

  @Override
  public double[] applyFilter(double val) {
    return new double[]{this.red * val, this.green * val, this.blue * val};
  }

  @Override
  public void applySepia() {
    int tempRed = (int) ((this.red * 0.393) + (this.green * 0.769) + (this.blue * 0.189));
    int tempGreen = (int) ((this.red * 0.349) + (this.green * 0.686) + (this.blue * 0.168));
    int tempBlue = (int) ((this.red * 0.272) + (this.green * 0.534) + (this.blue * 0.131));

    this.red = tempRed;
    this.green = tempGreen;
    this.blue = tempBlue;

    this.clampValues();
  }

}
