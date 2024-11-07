package controller.filter;

/**
 * A class extending Filter represents a blue component filter for pixel processing.
 */
public class CompBlueFilter extends AbstractFilter {
  /**
   * Initializes a predefined 3x3 matrix.
   * The matrix represents a blue component filter.
   */
  public CompBlueFilter() {
    filter = new double[][]{
            {0, 0, 1},
            {0, 0, 1},
            {0, 0, 1}
    };
  }
}
