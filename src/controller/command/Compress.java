package controller.command;

import java.util.Map;

import controller.ImageCommand;
import model.ImageModel;
import util.ImageTransformer;

public class Compress implements ImageCommand {
  private final String source;
  private final String result;
  private final int ratio;

  public Compress(String[] args) {
    if (args.length == 4) {
      this.ratio = Integer.parseInt(args[1]);
      this.source = args[2];
      this.result = args[3];
    } else {
      throw new IllegalArgumentException(
              "Error: Illegal number of arguments in compress!");
    }
  }

  @Override
  public int apply(Map<String, ImageModel> images) {
    return ImageTransformer.apply(images, source, result, img -> img.compress(ratio));
  }
}
