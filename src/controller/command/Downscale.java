package controller.command;

import model.ImageMap;

public class Downscale implements ImageCommand{
  private final String source;
  private final String result;
  private final int newHeight;
  private final int newWidth;

  public Downscale(String[] args) {
    if (args.length != 5) {
      throw new IllegalArgumentException("Illegal number of arguments in downscale command!");
    }

    this.newHeight = Integer.parseInt(args[1]);
    this.newWidth = Integer.parseInt(args[2]);
    this.source = args[3];
    this.result = args[4];
  }

  @Override
  public int apply(ImageMap images) {
    if (images.get(source).getHeight() < newHeight || images.get(source).getWidth() < newWidth
            || newHeight <= 0 || newWidth <= 0) {
      return 1;
    }
    return images.apply(source, result, img -> img.downscale(newHeight,newWidth),0);
  }
}
