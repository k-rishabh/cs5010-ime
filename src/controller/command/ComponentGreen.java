package controller.command;

import java.util.Map;

import controller.ImageCommand;
import controller.filter.CompGreenFilter;
import model.ImageModel;
import util.ImageTransformer;

public class ComponentGreen implements ImageCommand {
  private final String source;
  private final String result;

  public ComponentGreen(String[] args) {
    if(args.length == 3) {
      this.source = args[1];
      this.result = args[2];
    } else {
      throw new IllegalArgumentException("Error: Illegal number of arguments in green-component!");
    }
  }

  @Override
  public int apply(Map<String, ImageModel> images) {
    return ImageTransformer.apply(images, source, result,
            img -> img.applyColorFilter(new CompGreenFilter()));
  }
}

