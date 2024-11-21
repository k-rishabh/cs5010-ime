package util;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.io.IOException;
import java.util.Scanner;

import model.ImageModel;
import model.RGBImage;

import static javax.imageio.ImageIO.read;
import static javax.imageio.ImageIO.write;

/**
 * Class that is responsible for loading and saving based on what type of image it is.
 * Provides functionality for raster and raw type of images.
 */
public class ImageUtil {
  /**
   * Converts our implementation of an image (ImageModel) to the ImageIO implementation,
   * in the form of a BufferedImage.
   *
   * @param img the ImageModel to be converted
   * @return the resulting BufferedImage
   */
  public static BufferedImage toBufferedImage(ImageModel img) {
    if (img == null || img.getHeight() == 0 || img.getWidth() == 0) {
      return null;
    }

    BufferedImage buffImg =
            new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        buffImg.setRGB(j, i, img.getPixel(i, j).getPacked());
      }
    }

    return buffImg;
  }

  /**
   * Loads a raster type image from the given file path.
   *
   * @param filePath the file path of the raster image
   * @return the image in the form of our internal representation
   */
  public static ImageModel loadImageRaster(String filePath) {
    BufferedImage img;

    try {
      img = read(new File(filePath));
    } catch (IOException e) {
      System.out.println("Exception: Provided file path is not a valid image!");
      return null;
    }

    int height = img.getHeight();
    int width = img.getWidth();
    int[][] pixels = new int[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        pixels[i][j] = img.getRGB(j, i);
      }
    }

    return new RGBImage(pixels);
  }

  /**
   * Loads a raw type image from the given file path.
   *
   * @param filename the file path of the raw image
   * @return the image in the form of our internal representation
   */
  public static ImageModel loadImageRaw(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("Exception: File " + filename + " not found!");
      return null;
    }

    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s);
        builder.append(System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());
    String token;
    token = sc.next();

    if (!token.equals("P3")) {
      System.out.println("Error: Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    int[][] packedPixels = new int[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        packedPixels[i][j] = (r << 16) | (g << 8) | b;
      }
    }

    return new RGBImage(packedPixels);
  }

  /**
   * Saves an image in the provided file path, in the form of a Raster image.
   *
   * @param filePath the file path where the image must be saved
   * @param img      the image to be saved, in the form of our internal representation
   */
  public static void saveImageRaster(String filePath, ImageModel img) {
    BufferedImage buffImg = toBufferedImage(img);

    try {
      write(buffImg, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));
    } catch (IOException e) {
      System.out.println("Exception: Failed to save raster image!");
    }
  }

  /**
   * Saves an image in the provided file path, in the form of a Raw image.
   *
   * @param filePath the file path where the image must be saved
   * @param img      the image to be saved, in the form of our internal representation
   */
  public static void saveImageRaw(String filePath, ImageModel img) {
    FileWriter writer;
    try {
      writer = new FileWriter(filePath);
    } catch (IOException e) {
      System.out.println("Exception: File " + filePath + " not found!");
      return;
    }

    try {
      writer.write("P3\n");
      writer.write(img.getWidth() + " " + img.getHeight() + "\n");
      writer.write("255\n"); // Max color value
    } catch (IOException e) {
      System.out.println("Exception: Failed to save raw image!");
    }

    int width = img.getWidth();
    int height = img.getHeight();


    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = img.getPixel(i, j).getRed();
        int green = img.getPixel(i, j).getGreen();
        int blue = img.getPixel(i, j).getBlue();

        try {
          writer.write(red + " " + green + " " + blue + "\n");
        } catch (IOException e) {
          System.out.println("Exception: Failed to save raw image!");
        }
      }
    }

    try {
      writer.close();
    } catch (IOException e) {
      System.out.println("Exception: Failed to save close file writer!");
    }
  }
}
