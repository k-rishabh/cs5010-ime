package model;

import controller.filter.Filter;
import model.pixel.Pixel;

/**
 * This interface represents the operations that can be performed on an Image.
 * An image is composed of pixels, and various manipulations such as:
 * <ol>
 * <li>Red Component</li>
 * <li>Green Component</li>
 * <li>Blue Component</li>
 * <li>Value Component</li>
 * <li>Luma Component</li>
 * <li>Intensity Component</li>
 * <li>Horizontal Flipping</li>
 * <li>Vertical Flipping</li>
 * <li>Brightening</li>
 * <li>RGB Split</li>
 * <li>RGB Combine</li>
 * <li>Blur</li>
 * <li>Sharpen</li>
 * <li>Sepia</li>
 * </ol>
 */
public interface ImageModel {
  /**
   * Gets the height of the image, which is the number of rows of pixels.
   *
   * @return the height of the image
   */
  int getHeight();

  /**
   * Gets the width of the image, which is the number of columns of pixels.
   *
   * @return the width of the image
   */
  int getWidth();

  int getRed(int i, int j);

  int getGreen(int i, int j);

  int getBlue(int i, int j);

  /**
   * Retrieves the packed pixel at the specified coordinates.
   * A packed pixel is typically an integer representing the red, green, and blue values.
   *
   * @param i the row index of the pixel
   * @param j the column index of the pixel
   * @return the packed pixel at the specified position
   */
  int getPackedPixel(int i, int j);

  Pixel getPixel(int i, int j);

  /**
   * Creates a deep copy of the image.
   * The deep copy should be independent of the original image.
   * It should not be a reference of the original image.
   *
   * @return a deep copy of the original image
   */
  ImageModel deepCopy();

  void applyColorFilter(Filter filter);

  /**
   * Applies a given filter to the image.
   *
   * @param filter the filter to be applied to the image.
   */
  void applyImageFilter(Filter filter);

  /**
   * Converts the image to its value component in the form of greyscale.
   * The value component is the maximum of the red, green, and blue values for each pixel.
   */
  void valueComponent();

  /**
   * Brightens/Darkens the image by a given constant value.
   * Increases or decreases the brightness of each pixel by adding the value to
   * the red, green, and blue components of each pixel.
   * If the constant is positive, then the image is brightened. If the constant
   * is negative, image is darkened.
   *
   * @param val the value to be brightened/darkened by
   */
  void brighten(int val);

  /**
   * Flips the image horizontally (flipping left to right pixel order).
   */
  void horizontalFlip();

  /**
   * Flips the image vertically (flipping top to bottom pixel order).
   */
  void verticalFlip();

  /**
   * Splits the image into its red, green, and blue components.
   * Each component is stored as a separate image.
   *
   * @return an array of three images, representing the red, green, and blue channels.
   */
  ImageModel[] splitComponents();

  /**
   * Combines the color components of the calling class and parameter into one image.
   * This is done by taking the max of each component from both images.
   */
  void combineComponents(ImageModel img);

  void compress(int ratio);

  void colorCorrect();

  ImageModel histogram();

  void levelsAdjust(int black, int mid, int white);

  ImageModel[] splitImage(int ratio);

  void mergeSplits(ImageModel img);
}
