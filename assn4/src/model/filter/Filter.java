package model.filter;

/**
 * An abstract class that implements the FilterADT interface. This class
 * represents a image filter and provides functionality for setting
 * and retrieving the filter matrix.
 */
public abstract class Filter implements FilterADT {
  protected double[][] filter;

  @Override
  public void setFilter(double[][] newFilter) {
    this.filter = newFilter;
  }

  @Override
  public double[][] getFilter() {
    return this.filter;
  }
}
