package controller;

import java.io.File;

public interface Features {
  void load(File f);

  void save(File f);

  void undo();

  void exit();

  void applyGrayscale(String feature, int ratio);

  void applyBlur(int ratio);

  void applySharpen(int ratio);

  void applySepia(int ratio);

  void applyColorCorrect(int ratio);

  void applyLevelsAdjust(int b, int m, int w, int ratio);

  void applyBrighten(int val);

  void applyHorizontalFlip();

  void applyVerticalFlip();

  void applyCompress(int ratio);

  void applyDownscale(String h, String w);
}
