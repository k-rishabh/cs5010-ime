package controller.command;

import java.util.Map;

import controller.ImageCommand;
import controller.filter.SharpenFilter;
import model.ImageModel;
import util.ImageTransformer;

public class Sharpen implements ImageCommand {
  private final String source;
  private final String result;
  private final int split;

  public Sharpen(String[] args) {
    if (args.length != 3 && args.length != 5) {
      throw new IllegalArgumentException("Error: Illegal number of arguments in sharpen command!");
    } else if (args.length == 5 && !args[3].equals("split")) {
      throw new IllegalArgumentException("Error: Illegal argument in sharpen command!");
    }

    this.source = args[1];
    this.result = args[2];

    if (args.length == 5) {
      this.split = Integer.parseInt(args[4]);
    } else {
      this.split = 0;
    }
  }

  @Override
  public int apply(Map<String, ImageModel> images) {
    if (split == 0 || split == 100) {
      return ImageTransformer.apply(images, source, result,
              img -> img.applyImageFilter(new SharpenFilter()));
    } else {
      return ImageTransformer.applySplit(images, source, result,
              img -> img.applyImageFilter(new SharpenFilter()), split);
    }
  }
}
