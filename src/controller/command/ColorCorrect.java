package controller.command;

import controller.ImageCommand;
import controller.ImageHandler;
import model.Image;

public class ColorCorrect implements ImageCommand {

  private final String source;
  private final String result;

  public ColorCorrect(String source, String result) {
    this.source = source;
    this.result = result;
  }

  @Override
  public void apply(ImageHandler img) {
    img.colorCorrectness(source, result);
  }
}
