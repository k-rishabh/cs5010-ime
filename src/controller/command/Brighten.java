package controller.command;

import controller.ImageCommand;
import controller.ImageHandler;

public class Brighten implements ImageCommand {
  int val;
  private final String source;
  private final String result;

  public Brighten(int val,String source, String result) {
    this.val = val;
    this.source = source;
    this.result = result;
  }


  @Override
  public void apply(ImageHandler img) {
    img.brighten(this.val,source,result);
  }
}
