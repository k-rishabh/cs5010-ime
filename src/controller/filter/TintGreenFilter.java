package controller.filter;

public class TintGreenFilter extends AbstractFilter {
  public TintGreenFilter() {
    filter = new double[][]{
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
    };
  }
}
