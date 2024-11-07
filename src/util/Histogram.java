package util;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

import model.ImageModel;
import model.RGBImage;
import model.pixel.Pixel;
import model.pixel.RGBPixel;

public class Histogram {

  private final BufferedImage histogram;

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

  public ImageModel createHistogram(RGBImage rgbImage) {
    int[][] freq = getFrequencies(rgbImage);
    int redFreq = findMaxFrequency(freq[0]);
    int greenFreq = findMaxFrequency(freq[1]);
    int blueFreq = findMaxFrequency(freq[2]);

    int maxFreq = Math.max(Math.max(redFreq,greenFreq),blueFreq);

    Graphics2D graphics = histogram.createGraphics();
    drawingChannelLines(graphics, freq[0], Color.RED, maxFreq);
    drawingChannelLines(graphics, freq[1], Color.GREEN, maxFreq);
    drawingChannelLines(graphics, freq[2], Color.BLUE, maxFreq);

    graphics.dispose();

    return convertToImage(histogram);
  }

  private int findMaxFrequency(int[] channel) {
    int max = 0;
    for (int channelValue : channel) {
      max = Math.max(max, channelValue);
    }
    return max;
  }

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

  public int[][] getFrequencies(ImageModel rgbImage) {
    int[] reds = new int[256];
    int[] greens = new int[256];
    int[] blues = new int[256];

    for (int i = 0; i < rgbImage.getHeight(); i++) {
      for (int j = 0; j < rgbImage.getWidth(); j++) {
        reds[(rgbImage.getPackedPixel(i, j) >> 16) & 0xFF]++;
        greens[(rgbImage.getPackedPixel(i, j) >> 8) & 0xFF]++;
        blues[(rgbImage.getPackedPixel(i, j)) & 0xFF]++;
      }
    }

    return new int[][]{reds, greens, blues};
  }

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