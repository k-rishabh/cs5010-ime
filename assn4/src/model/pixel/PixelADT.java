package model.pixel;

/**
 * This interface defines the operations that can be performed on a pixel.
 * The operations allow for various transformations to be applied
 * to the pixel, such as brightening, darkening, applying filters (Blur and Sharpen),
 * and adjusting color components (Value, Luma, Intensity, Sepia, Red/Green/Blue Component).
 */
public interface PixelADT {

  /**
   * Returns the packed RGB value of the pixel. The integer
   * contains all components in a packed form.
   *
   * @return the packed RGB value of the pixel.
   */
  int getPacked();

  /**
   * Creates a deep copy of the pixel, which is a new instance with the same pixel data.
   *
   * @return a new PixelADT object that is a copy of this pixel.
   */
  PixelADT deepCopy();

  void showRed();

  void showGreen();

  void showBlue();

  /**
   * Sets the pixel's value to its maximum component value. Provides a grayscale
   * effect on the pixel where each pixel is the brightest.
   */
  void showValue();

  /**
   * Sets the pixel's value to the average of its components.
   * Provides a grayscale effect on the pixel.
   */
  void showIntensity();

  /**
   * Sets the pixel's value based on the luma component, which is a weighted average of
   * the components. Provides a grayscale effect on the pixel.
   */
  void showLuma();

  /**
   * Brightens/darkens the pixel by a given value.
   * If Positive it brightens the pixel value or darkens the pixel value.
   *
   * @param val the amount by which to brighten the pixel.
   */
  void brighten(int val);

  void applyRedTint();

  void applyGreenTint();

  void applyBlueTint();

  void addComponent(PixelADT pixel);

  /**
   * Applies a filter to the pixel by modifying its color based on the filter's value.
   *
   * @param val the filter value to apply to the pixel.
   * @return an array of integers representing the filtered color values.
   */
  double[] applyFilter(double val);

  /**
   * Applies a sepia tone effect to the pixel.
   */
  void applySepia();

}

