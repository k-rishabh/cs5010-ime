package controller;

import java.util.Map;

import model.ImageModel;

/**
 * Interface that represents a command that the user can execute.
 * It applies a transformation on an image with the apply command.
 * Used for abstraction of different operations on images.
 */
public interface ImageCommand {
  /**
   * Applies a transformation/operation on the image.
   * The type of transformation depends on the calling object.
   *
   * @param images the images to be operated on
   */
  int apply(Map<String, ImageModel> images);
}
