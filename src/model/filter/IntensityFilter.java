package model.filter;

public class IntensityFilter extends AbstractFilter {
  public IntensityFilter() {
    filter = new double[][]{
            {0.3333, 0.3333, 0.3333},
            {0.3333, 0.3333, 0.3333},
            {0.3333, 0.3333, 0.3333}
    };
  }
}
