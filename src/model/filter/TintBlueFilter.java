package model.filter;

public class TintBlueFilter extends AbstractFilter {
  public TintBlueFilter() {
    filter = new double[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 1}
    };
  }
}
