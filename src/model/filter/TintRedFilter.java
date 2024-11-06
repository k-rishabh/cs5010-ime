package model.filter;

public class TintRedFilter extends AbstractFilter {
  public TintRedFilter() {
    filter = new double[][]{
            {1, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };
  }
}
