package controller.command;

import controller.ImageCommand;
import controller.ImageHandler;

public class Load implements ImageCommand {
  private final String source;
  private final String result;

  public Load(String source, String result) {
    this.source = source;
    this.result = result;
  }

  @Override
  public void apply(ImageHandler img) {
    System.out.println(source);
    img.loadImage(source, result);
  }
}
