package controller.command;

import java.util.Map;

import controller.ImageCommand;
import controller.filter.SharpenFilter;
import model.ImageModel;
import util.ImageTransformer;

public class Sharpen implements ImageCommand {
  private final String source;
  private final String result;

  public Sharpen(String[] args) {
    if(args.length == 3) {
      this.source = args[1];
      this.result = args[2];
    } else {
      throw new IllegalArgumentException("Error: Illegal number of arguments in sharpen command!");
    }
  }

  @Override
  public int apply(Map<String, ImageModel> images) {
    return ImageTransformer.apply(images, source, result,
            img -> img.applyImageFilter(new SharpenFilter()));
  }
}
