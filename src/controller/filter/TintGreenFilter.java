package controller.filter;

/**
 * A class extending Filter represents a green tint filter for pixel processing.
 */
public class TintGreenFilter extends AbstractFilter {
  /**
   * Initializes a predefined 3x3 matrix.
   * The matrix represents a green tint filter.
   */
  public TintGreenFilter() {
    filter = new double[][]{
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
    };
  }
}
