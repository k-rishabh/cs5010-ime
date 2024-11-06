package model.filter;

public class LumaFilter extends AbstractFilter {
  public LumaFilter() {
    filter = new double[][]{
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    };
  }
}
