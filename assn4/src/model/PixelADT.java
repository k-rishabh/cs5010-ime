package model;

public interface PixelADT {
  public void brighten(int val);

  public void applyFilter(double val);

  public void applySepia();

  public void applyRedTint();

  public void applyGreenTint();

  public void applyBlueTint();

  public void showValue();

  public void showIntensity();

  public void showLuma();
}
