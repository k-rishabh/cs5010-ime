package controller;

import java.util.List;

public interface ImageController {
  void execute();
  public void applyImageTransform(List<String> tokens, int ratio);
}
