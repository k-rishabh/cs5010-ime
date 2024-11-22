package model.pixel;

import controller.filter.Filter;

/**
 * This interface defines the operations that can be performed on a pixel.
 * The operations allow for various transformations to be applied to the pixel,
 * such as brightening, darkening, applying image filters (blur, sharpen),
 * and applying color filters (Value, Luma, Intensity, Sepia, Red/Green/Blue Component).
 */
public interface Pixel {

  /**
   * Returns the red value of the pixel.
   *
   * @return the red pixel value
   */
  int getRed();

  /**
   * Returns the green value of the pixel.
   *
   * @return the green pixel value
   */
  int getGreen();

  /**
   * Returns the blue value of the pixel.
   *
   * @return the blue value of the pixel
   */
  int getBlue();

  /**
   * Returns the packed RGB value of the pixel. The integer
   * contains all components in a packed form.
   *
   * @return the packed RGB value of the pixel.
   */
  int getPacked();

  /**
   * Applies a color filter on the pixel so that it is a grayscale representation of the
   * max component value.
   */
  void applyValueFilter();

  /**
   * Applies a filter on the color using 2D matrix multiplication.
   * Filter must have same number of columns as number of components.
   *
   * @param filter the color filter to be applied
   */
  void applyColorFilter(Filter filter);

  /**
   * Brightens/darkens the pixel by a given value.
   * If positive, it brightens the pixel.
   * If negative, it darkens the pixel.
   *
   * @param val the amount by which to brighten/darken the pixel.
   */
  void brighten(int val);

  /**
   * Maximizes the components of two pixels.
   * This is done by taking the maximum component from both pixels.
   *
   * @param pixel the pixel whose components are to be added
   */
  void maximizeComponents(Pixel pixel);

  /**
   * Applies a filter to the pixel by modifying its color based on the filter's value.
   * Multiplies the filter to each component.
   *
   * @param val the filter value to apply to the pixel.
   * @return an array representing the filtered color values for each component.
   */
  double[] applyFilter(double val);
}

