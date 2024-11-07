package controller.filter;

/**
 * A class extending Filter represents a blue tint filter for pixel processing.
 */
public class TintBlueFilter extends AbstractFilter {
  /**
   * Initializes a predefined 3x3 matrix.
   * The matrix represents a blue tint filter.
   */
  public TintBlueFilter() {
    filter = new double[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 1}
    };
  }
}
