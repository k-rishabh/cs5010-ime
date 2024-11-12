package controller.filter;

/**
 * A class extending Filter represents a red component filter for pixel processing.
 */
public class CompRedFilter extends AbstractFilter {
  /**
   * Initializes a predefined 3x3 matrix.
   * The matrix represents a red component filter.
   */
  public CompRedFilter() {
    filter = new double[][]{
            {1, 0, 0},
            {1, 0, 0},
            {1, 0, 0}
    };
  }
}
