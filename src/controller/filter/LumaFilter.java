package controller.filter;

/**
 * A class extending Filter represents a luma filter for pixel processing.
 */
public class LumaFilter extends AbstractFilter {
  /**
   * Initializes a predefined 3x3 matrix.
   * The matrix represents a luma filter.
   */
  public LumaFilter() {
    filter = new double[][]{
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    };
  }
}
