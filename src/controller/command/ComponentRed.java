package controller.command;

import controller.ImageCommand;
import controller.ImageHandler;

public class ComponentRed implements ImageCommand {
  private final String source;
  private final String result;

  public ComponentRed(String source, String result) {
    this.source = source;
    this.result = result;
  }

  @Override
  public void apply(ImageHandler img) {
    img.redComponent(source,result);
  }
}
