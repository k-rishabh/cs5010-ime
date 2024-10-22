package model;

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
