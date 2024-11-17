package model;

import java.io.IOException;
import java.io.InputStream;

public interface ImageMapInterface {

  void load(String imageName, String filePath);

  void save(String imageName, String filePath) throws IOException;

  void sepia(String[] args);

  void redComponent(String[] args);

  void blueComponent(String[] args);

  void greenComponent(String[] args);

  void lumaGreyscale(String[] args);

  void valueGreyscale(String[] args);

  void intensityGreyscale(String[] args);

  void brighten(String imageName, String destImgName, int increment);

  void blur(String[] args);

  void sharpen(String[] args);

  void horizontalFlip(String imageName, String destImgName);

  void verticalFlip(String imageName, String destImgName);

  void rgbSplit(String imageName, String destRedImgName, String destGreenImageName,
                String destBlueImgName);

  void rgbCombine(String destImageName,String redImageName, String greenImageName, String blueImageName);

  void colorCorrect(String[] args);

  void compress(int percentage, String imageName, String destImageName);

  void histogram(String imageName, String destImageName);

  void levelsAdjust(String[] args);
}
