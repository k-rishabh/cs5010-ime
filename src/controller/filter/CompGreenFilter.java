package controller.filter;

/**
 * A class extending Filter represents a green component filter for pixel processing.
 */
public class CompGreenFilter extends AbstractFilter {
  /**
   * Initializes a predefined 3x3 matrix.
   * The matrix represents a green component filter.
   */
  public CompGreenFilter() {
    filter = new double[][]{
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0}
    };
  }
}
