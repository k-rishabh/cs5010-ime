package controller.filter;

/**
 * This interface represents a filter that can be applied to an image.
 * Interface specifies methods for getting and setting the filter.
 * A filter is represented as a matrix of double values.
 */
public interface Filter {
  /**
   * Sets the filter matrix to a new set of values.
   *
   * @param newFilter the newFilter matrix to be applied, represented as a 2D array of doubles.
   */
  void setFilter(double[][] newFilter);

  /**
   * Retrieves the current filter matrix.
   *
   * @return a 2D array of doubles representing the filter matrix.
   */
  double[][] getFilter();
}
