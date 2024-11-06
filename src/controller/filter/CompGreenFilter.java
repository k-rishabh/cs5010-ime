package controller.filter;

public class CompGreenFilter extends AbstractFilter {
  public CompGreenFilter() {
    filter = new double[][]{
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0}
    };
  }
}
