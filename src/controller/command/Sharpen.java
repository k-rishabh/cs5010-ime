package controller.command;

import controller.ImageCommand;
import controller.ImageHandler;

public class Sharpen implements ImageCommand {
  private final String source;
  private final String result;

  public Sharpen(String source, String result) {
    this.source = source;
    this.result = result;
  }

  @Override
  public void apply(ImageHandler img) {
    img.sharpen(source, result);
  }
}
