package controller.filter;

/**
 * A class extending Filter represents a sepia filter for pixel processing.
 */
public class SepiaFilter extends AbstractFilter {
  /**
   * Initializes a predefined 3x3 matrix.
   * The matrix represents a sepia filter.
   */
  public SepiaFilter() {
    filter = new double[][]{
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };
  }
}
