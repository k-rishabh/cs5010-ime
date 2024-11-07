package controller.command;

import java.util.Map;

import controller.ImageCommand;
import model.ImageModel;
import util.ImageTransformer;

public class FlipVertical implements ImageCommand {
  String src;
  String dest;

  public FlipVertical(String[] args) {
    if (args.length == 3) {
      src = args[1];
      dest = args[2];
    } else {
      throw new IllegalArgumentException("Unknown number of arguments for vertical-flip!");
    }
  }
  @Override
  public int apply(Map<String, ImageModel> images) {
    return ImageTransformer.apply(images,src, dest, img -> img.verticalFlip());
  }
}
