package model;

import java.io.File;
import java.io.IOException;

public interface Image {
  /**
   * Loads an image from a given file path.
   *
   * @param f the file path of the image
   * @throws IOException if the image is not found
   */
  void loadImage(File f, String imageName) throws IOException;

  /**
   *
   * @param filename
   * @param destination
   * @throws IOException
   */
  void saveAsPPM(String filename, File destination) throws IOException;

  /**
   *
   * @param filename
   * @param destination
   * @throws IOException
   */
  void saveAsJPG(String filename, File destination) throws IOException;

  /**
   *
   * @param filename
   * @param destination
   * @throws IOException
   */
  void saveAsPNG(String filename, File destination) throws IOException;


  Image getValueComponent();

  Image brightenImage(int constant, String destinationImageName);

  void setRed(int height, int width, int value);

  void setBlue(int height, int width, int value);

  Image flipImage(String destinationImageName);

  Image getImageIntensity();

  int[][] getRed();
  int[][] getGreen();

  void setGreen(int height, int width, int value);

  int[][] getBlue();

  Image getImageLuma();
}
