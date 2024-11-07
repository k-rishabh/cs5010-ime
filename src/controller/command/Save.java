package controller.command;

import controller.ImageCommand;
import controller.ImageHandler;

public class Save implements ImageCommand {
  private final String source;
  private final String result;

  public Save(String source, String result) {
    this.source = source;
    this.result = result;
  }

  @Override
  public void apply(ImageHandler img) {
    img.saveImage(source, result);
  }
}
