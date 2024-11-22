package util;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

import model.ImageModel;
import model.RGBImage;
import model.pixel.Pixel;
import model.pixel.RGBPixel;

/**
 * Histogram class provides functionality to generate a histogram image for
 * an RGB image, showing the frequency distribution of red, green, and blue
 * color intensities.
 */
public class Histogram {

  private final BufferedImage histogram;

  /**
   * Constructor initializing an empty histogram image with
   * grid lines to visualize frequency distributions of the RGB Image.
   */
  public Histogram() {
    this.histogram = new BufferedImage(255, 255, BufferedImage.TYPE_INT_ARGB);
    Graphics2D plot = histogram.createGraphics();
    plot.setColor(Color.WHITE);
    plot.fillRect(0, 0, histogram.getWidth(), histogram.getHeight());

    plot.setColor(Color.GRAY);
    for (int i = 0; i < histogram.getWidth(); i += 10) {
      plot.drawLine(i, 0, i, histogram.getHeight());
      plot.drawLine(0, i, histogram.getWidth(), i);
    }
    plot.dispose();
  }

  /**
   * Creates a histogram image for the given RGB image.
   *
   * @param rgbImage the RGB image to create histogram.
   * @return returns an ImageV1 which represents the histogram.
   */
  public ImageModel createHistogram(RGBImage rgbImage) {
    int[][] freq = getFrequencies(rgbImage);
    int redFreq = findMaxFrequency(freq[0]);
    int greenFreq = findMaxFrequency(freq[1]);
    int blueFreq = findMaxFrequency(freq[2]);

    int maxFreq = Math.max(Math.max(redFreq, greenFreq), blueFreq);

    Graphics2D graphics = histogram.createGraphics();
    drawingChannelLines(graphics, freq[0], Color.RED, maxFreq);
    drawingChannelLines(graphics, freq[1], Color.GREEN, maxFreq);
    drawingChannelLines(graphics, freq[2], Color.BLUE, maxFreq);

    graphics.dispose();

    return convertToImage(histogram);
  }

  /**
   * Method to find maximum frequency in a channel's frequency array.
   *
   * @param channel the frequency array of a color channel
   * @return the maximum frequency in the array
   */
  private int findMaxFrequency(int[] channel) {
    int max = 0;
    for (int channelValue : channel) {
      max = Math.max(max, channelValue);
    }
    return max;
  }

  /**
   * Method to draws lines representing color intensity frequencies for a channel on the histogram.
   *
   * @param graphics Graphics2D object for drawing on the histogram.
   * @param freq     frequency array for a specific channel.
   * @param color    color used to represent the channel.
   * @param maxReq   maximum frequency across all channels.
   */
  private void drawingChannelLines(Graphics2D graphics, int[] freq, Color color, int maxReq) {
    graphics.setColor(color);

    int endingY = histogram.getHeight();
    for (int i = 0; i < freq.length; i++) {
      int y = histogram.getHeight()
              - (freq[i] * histogram.getHeight() / maxReq);
      graphics.drawLine(i, endingY, i, y);
      endingY = y;
    }
  }

  /**
   * Methods which calculates the frequency of each color intensity level for each
   * channel in the image.
   *
   * @param rgbImage RGB image to get intensity levels from.
   * @return returns 2D array where each row represents the frequency array of a channel.
   */
  public static int[][] getFrequencies(ImageModel rgbImage) {
    int[] reds = new int[256];
    int[] greens = new int[256];
    int[] blues = new int[256];

    for (int i = 0; i < rgbImage.getHeight(); i++) {
      for (int j = 0; j < rgbImage.getWidth(); j++) {
        reds[rgbImage.getPixel(i, j).getRed()]++;
        greens[rgbImage.getPixel(i, j).getGreen()]++;
        blues[rgbImage.getPixel(i, j).getBlue()]++;
      }
    }

    return new int[][]{reds, greens, blues};
  }

  /**
   * Method which converts a BufferedImage to an Image Object so that it can work with the other
   * functionalities and be compatible with the model.
   *
   * @param image BufferedImage to convert.
   * @return returns an Image which is converted.
   */
  private ImageModel convertToImage(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    Pixel[][] pixels = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int color = image.getRGB(j, i);
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;
        pixels[i][j] = new RGBPixel(red, green, blue);
      }
    }

    return new RGBImage(pixels);
  }
}
