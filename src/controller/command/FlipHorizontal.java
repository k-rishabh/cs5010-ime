package controller.command;

import java.util.Map;

import controller.ImageCommand;
import model.ImageModel;
import util.ImageTransformer;

public class FlipHorizontal implements ImageCommand {
  String src;
  String dest;

  public FlipHorizontal(String[] args) {
    if (args.length == 3) {
      src = args[1];
      dest = args[2];
    } else {
      throw new IllegalArgumentException("Unknown number of arguments for horizontalflip!");
    }
  }
  @Override
  public int apply(Map<String, ImageModel> images) {
    return ImageTransformer.apply(images,src, dest, img -> img.horizontalFlip());

  }
}
