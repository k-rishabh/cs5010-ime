package model;

import controller.filter.Filter;
import model.pixel.Pixel;

/**
 * This interface represents an Image. It specifies functionality for simple transformations
 * that can take place on an Image. Some of the functionalities are: applying color filters,
 * applying image filters, flipping, histogram, brighten, levels adjustment, color correction,
 * compression, etc.
 * It also has some basic getter functions for height, width, and the pixels at a particular
 * coordinate in the image.
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

  /**
   * Retrieves the pixel at the specified coordinates.
   *
   * @param i the row index of the pixel
   * @param j the col index of the pixel
   * @return the pixel object of type Pixel (defined the in the pixel package)
   */
  Pixel getPixel(int i, int j);

  /**
   * Creates a deep copy of the image.
   * The deep copy should be independent of the original image.
   * It should not be a reference of the original image.
   *
   * @return a deep copy of the original image
   */
  ImageModel deepCopy();

  /**
   * Applies a color filter on each pixel of the image. The filter must be n x n in dimensions,
   * where n is the number of components in a pixel. For RGB, it must be a 3x3 matrix. It performs
   * matrix multiplication with components of the pixel.
   *
   * @param filter the color filter to be applied to each pixel
   */
  void applyColorFilter(Filter filter);

  /**
   * Applies the given Filter to the image. The filter matrix is applied
   * to each pixel and the surrounding pixels, and the result is stored in a temporary
   * array before being assigned back to the image.
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

  /**
   * Applies all necessary Haar transformations in order, to compress an image. It compresses
   * each component of the pixel individually and merges all 3, to get the final image.
   * It uses the ratio for lossy compression. Ratio represents the fraction of non-zero
   * values before and after thresholding.
   *
   * @param ratio the compression ratio, between 0 and 100
   */
  void compress(int ratio);

  /**
   * Applies color correction to the image by adjusting each color channel to align with average
   * peak based on channel histograms.
   */
  void colorCorrect();

  /**
   * Method which creates the histogram from the RGB Image.
   *
   * @return returns an image which represents the histogram.
   */
  ImageModel histogram();

  /**
   * Adjusts levels by mapping pixel values from the specified black, mid, and white values
   * to fit within the desired range. This operation improves the contrast.
   *
   * @param black the black value.
   * @param mid   the mid value.
   * @param white the white value.
   */
  void levelsAdjust(int black, int mid, int white);

  /**
   * Used for split view operations on an image. This splits the calling class image, into two
   * separate images, based on the ratio. The ratio defines what proportions are used for splitting
   * the image.
   *
   * @param ratio a number between 0 and 100 that represents where the image is split from
   * @return an array of 2 images, the left and right halves
   */
  ImageModel[] splitImage(int ratio);

  /**
   * Merges the argument image to the right of the calling image object. This can only be done
   * if the heights of both objects are equal. This is used for split view operations on an image.
   *
   * @param img the image to be merged to the right of the calling image
   */
  void mergeSplits(ImageModel img);

  /**
   * Downscales the image to the specified dimensions. The method adjusts the pixel data of
   * the current image to match the new height and width.
   *
   * @param newHeight the height of the downscaled image
   * @param newWidth  the width of the downscaled image
   * @throws IllegalArgumentException if the new dimensions are larger than the original dimensions
   *                                   or if they are non-positive
   */
  void downscale(int newHeight, int newWidth);

  /**
   * Applies a masking operation to the current image using a mask image and an operated image.
   * The resulting image is generated by blending pixels from the original image.
   * If the mask pixel value is 0, the pixel from the operated image is used. If the mask pixel
   * value is 1-255, the pixel from the original image is used.
   *
   * @param maskImage     the image used as a mask.
   * @param operatedImage the image on which the operation was performed.
   * @return an ImageModel with the masking operation applied.
   * @throws IllegalArgumentException if the dimensions of the mask image and the
   *                                  original image do not match.
   */
  ImageModel applyMasking(ImageModel maskImage, ImageModel operatedImage);
}
