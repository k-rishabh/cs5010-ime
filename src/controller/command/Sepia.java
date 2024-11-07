package controller.command;

import java.util.Map;

import controller.ImageCommand;
import controller.filter.SepiaFilter;
import model.ImageModel;
import util.ImageTransformer;

public class Sepia implements ImageCommand {
  private final String source;
  private final String result;

  public Sepia(String[] args) {
    if(args.length == 3) {
      this.source = args[1];
      this.result = args[2];
    } else {
      throw new IllegalArgumentException("Error: Illegal number of arguments in sepia command!");
    }
  }

  @Override
  public int apply(Map<String, ImageModel> images) {
    return ImageTransformer.apply(images, source, result,
            img -> img.applyColorFilter(new SepiaFilter()));
  }
}
