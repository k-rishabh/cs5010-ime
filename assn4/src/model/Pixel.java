package model;

public interface Pixel {
  public Pixel addAllComponents(int val);

  public Pixel mulAllComponents(float val);

  public int getMaxComponent();

  public int getMinComponent();

  public int getAvgComponent();
}
