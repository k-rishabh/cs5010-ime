package controller.filter;

/**
 * A class extending Filter represents an intensity filter for pixel processing.
 */
public class IntensityFilter extends AbstractFilter {
  /**
   * Initializes a predefined 3x3 matrix.
   * The matrix represents an intensity filter.
   */
  public IntensityFilter() {
    filter = new double[][]{
            {0.3333, 0.3333, 0.3333},
            {0.3333, 0.3333, 0.3333},
            {0.3333, 0.3333, 0.3333}
    };
  }
}
