package controller.command;

import controller.ImageCommand;
import controller.ImageHandler;
import model.AbstractImage;

public class Blur implements ImageCommand {
  private final String source;
  private final String result;

  public Blur(String source, String result) {
    this.source = source;
    this.result = result;
  }

  @Override
  public void apply(ImageHandler img) {
    img.blur(source, result);
  }
}
