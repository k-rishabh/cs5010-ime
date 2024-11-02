package controller.command;

import controller.ImageCommand;
import model.Image;

public class Brighten implements ImageCommand {
  int val;

  public Brighten(int val) {
    this.val = val;
  }

  @Override
  public int apply(Image img) {
    return 0;
  }
}
