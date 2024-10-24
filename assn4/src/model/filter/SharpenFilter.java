package model.filter;

/**
 * A class extending Filter represents a sharpen filter for image processing.
 * This filter uses a 5x5 kernel to sharpen the pixels of the image.
 */
public class SharpenFilter extends Filter {
  /**
   * Initializes a predefined 5x5 matrix.
   * The matrix represents a sharpen filter.
   */
  public SharpenFilter() {
    filter = new double[][]{
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}
    };
  }
}