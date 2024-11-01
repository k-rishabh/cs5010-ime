package model.pixel;

/**
 * This interface defines the operations that can be performed on a pixel.
 * The operations allow for various transformations to be applied to the pixel,
 * such as brightening, darkening, applying filters (blur and sharpen),
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

  /**
   * Sets the pixel to a greyscale which represents the red component of the pixel.
   * All components set to the red value.
   */
  void showRed();

  /**
   * Sets the pixel to a greyscale which represents the green component of the pixel.
   * All components set to the green value.
   */
  void showGreen();

  /**
   * Sets the pixel to a greyscale which represents the blue component of the pixel.
   * All components set to the blue value.
   */
  void showBlue();

  /**
   * Sets the pixel's value to its maximum component value.
   * Provides a greyscale effect on the pixel where each pixel is the brightest.
   */
  void showValue();

  /**
   * Sets the pixel's value to the average of its components.
   * Provides a greyscale effect on the pixel.
   */
  void showIntensity();

  /**
   * Sets the pixel's value based on the luma component (weighted sum).
   * Provides a greyscale effect on the pixel.
   */
  void showLuma();

  /**
   * Brightens/darkens the pixel by a given value.
   * If positive, it brightens the pixel.
   * If negative, it darkens the pixel.
   *
   * @param val the amount by which to brighten/darken the pixel.
   */
  void brighten(int val);

  /**
   * Visualizes only the red component of the pixel.
   * Other components are set to 0. This adds a red tint to the image.
   */
  void applyRedTint();

  /**
   * Visualizes only the green component of the pixel.
   * Other components are set to 0. This adds a green tint to the image.
   */
  void applyGreenTint();

  /**
   * Visualizes only the blue component of the pixel.
   * Other components are set to 0. This adds a blue tint to the image.
   */
  void applyBlueTint();

  /**
   * Combines the components of two pixels.
   * This is done by taking the maximum component from both pixels.
   *
   * @param pixel the pixel whose components are to be added
   */
  void addComponent(PixelADT pixel);

  /**
   * Applies a filter to the pixel by modifying its color based on the filter's value.
   * Multiplies the filter to each component.
   *
   * @param val the filter value to apply to the pixel.
   * @return an array representing the filtered color values for each component.
   */
  double[] applyFilter(double val);

  /**
   * Applies a sepia tone effect to the pixel.
   */
  void applySepia();

}

