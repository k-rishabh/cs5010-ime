package controller.command;

import java.util.Map;

import controller.ImageCommand;
import model.ImageModel;
import util.ImageTransformer;

public class LevelsAdjust implements ImageCommand {

  private final String source;
  private final String result;
  private final int b;
  private final int m;
  private final int w;
  
  public LevelsAdjust(String[] args) {
    if (args.length == 6) {
      this.b = Integer.parseInt(args[1]);
      this.m = Integer.parseInt(args[2]);
      this.w = Integer.parseInt(args[3]);
      this.source = args[4];
      this.result = args[5];
    } else {
      throw new IllegalArgumentException("Illegal number of arguments in levels-adjust command!");
    }
  }

  @Override
  public int apply(Map<String, ImageModel> images) {
    return ImageTransformer.apply(images, source, result, img -> img.levelsAdjust(b, m, w));
  }
}
