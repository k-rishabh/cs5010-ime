package util;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.io.IOException;
import java.util.Scanner;

import model.Image;
import model.RGBImage;

import static javax.imageio.ImageIO.read;
import static javax.imageio.ImageIO.write;

public class ImageUtil {
  public static Image loadImageRaster(String filePath) {
    BufferedImage img;

    try {
      img = read(new File(filePath));
    } catch (IOException e) {
      System.out.println("Provided file path is not a valid image!");
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

  public static Image loadImageRaw(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
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
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
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

  public static void saveImageRaster(String filePath, Image img) {
    BufferedImage buffImg =
            new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        buffImg.setRGB(i, j, img.getPackedPixel(i, j));
      }
    }

    try {
      write(buffImg, filePath.substring(filePath.lastIndexOf('.')), new File(filePath));
    } catch (IOException e) {
      System.out.println("Failed to save raster image!");
    }
  }

  public static void saveImageRaw(String filePath, Image img) {
    FileWriter writer;
    try {
      writer = new FileWriter(filePath);
    } catch (IOException e) {
      System.out.println("File " + filePath + " not found!");
      return;
    }

    try {
      writer.write("P3\n");
      writer.write(img.getWidth() + " " + img.getHeight() + "\n");
      writer.write("255\n"); // Max color value
    } catch (IOException e) {
      System.out.println("Failed to save raw image!");
    }

    int width = img.getWidth();
    int height = img.getHeight();


    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int pixel = img.getPackedPixel(i, j);

        int red = pixel >> 16;
        int green = pixel >> 8;
        try {
          writer.write(red + " " + green + " " + pixel + "\n");
        } catch (IOException e) {
          System.out.println("Failed to save raw image!");
        }
      }
    }
  }
}
