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
  void loadImage(File f) throws IOException;

  /**
   *
   * @param filename
   * @param destination
   * @throws IOException
   */
  void saveImage(String filename, File destination) throws IOException;

  int[][] getImageValue();

  int getImageIntensity();

  int getImageLuma();
}
