package model.filter;

/**
 * A class extending Filter represents a blur filter for image processing.
 * This filter uses a 3x3 kernel to blur an image.
 */
public class BlurFilter extends Filter {
  /**
   * Initializes a predefined 3x3 matrix.
   * The matrix represents a Gaussian blur filter.
   */
  public BlurFilter() {
    filter = new double[][]{
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}
    };
  }
}
