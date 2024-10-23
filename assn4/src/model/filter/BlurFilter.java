package model.filter;

public class BlurFilter extends Filter {
  public BlurFilter() {
    filter = new double[][]{
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}
    };
  }
}
