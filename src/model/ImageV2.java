package model;

public interface ImageV2 extends ImageV1 {

  ImageV2 compress(int ratio);

  void colorCorrect();

  void levelsAdjust(int black, int mid, int white);

  ImageV2[] splitImage(int ratio);

  void mergeRight(ImageV2 img);
}
