package model.filter;

/**
 * This interface represents the get and set operations for a filter that can be applied
 * to an image.
 * A filter is represented as a matrix of double values.
 */
public interface FilterADT {
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
