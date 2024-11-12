package controller.filter;

/**
 * A class extending Filter represents a red tint filter for pixel processing.
 */
public class TintRedFilter extends AbstractFilter {
  /**
   * Initializes a predefined 3x3 matrix.
   * The matrix represents a red tint filter.
   */
  public TintRedFilter() {
    filter = new double[][]{
            {1, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };
  }
}
