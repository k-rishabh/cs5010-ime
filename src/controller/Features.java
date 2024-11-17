package controller;
import java.io.IOException;

public interface Features
{
  void load(String filepath) throws IOException;

  void save(String filepath) throws IOException;

  void redComponent() throws IOException;

  void blueComponent() throws IOException;

  void greenComponent() throws IOException;

  void lumaGreyscale() throws IOException;

  void valueGreyscale() throws IOException;

  void intensityGreyscale() throws IOException;

  void blur() throws IOException;

  void sharpen() throws IOException;

  void sepia() throws IOException;

  void brighten(int scale) throws IOException;

  void verticalFlip() throws IOException;


  void horizontalFlip() throws IOException;

  void colorCorrect() throws IOException;

  void levelAdjust(int b, int m, int w) throws IOException;

  void compress(double percentage) throws IOException;

  void rgbSplit(String redFilePath, String greenFilePath, String blueFilePath) throws IOException;

  void rgbCombine(String redImageFile, String greenImageFile, String blueImageFile)
          throws IOException;

  void split(String operationType, String[] args) throws IOException;

  void refreshImage() throws IOException;

  void exitProgram();
}
