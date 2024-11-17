package controller;

import java.io.IOException;

import model.ImageMapInterface;

public interface ImageControllerInterface {
  void execute(ImageMapInterface processor) throws IOException;
}
