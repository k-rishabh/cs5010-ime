package controller.command;

import java.util.Map;

import controller.ImageCommand;
import model.ImageModel;
import util.ImageTransformer;

public class Histogram implements ImageCommand {

  private final String source;
  private final String result;

  public Histogram(String[] args) {
    if(args.length == 3) {
      this.source = args[1];
      this.result = args[2];
    } else {
      throw new IllegalArgumentException("Error: Illegal number of arguments in histogram command!");
    }
  }

  @Override
  public int apply(Map<String, ImageModel> images) {
    ImageModel srcImage = images.get(source).deepCopy();
    ImageModel destImage = srcImage.histogram();
    images.put(result, destImage);
    return 0;
  }
}
