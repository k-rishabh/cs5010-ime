package controller;

import model.AbstractImage;

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
   * @param img the image to be operated on
   * @return 0 if successful, 1 if failure
   */
  int apply(AbstractImage img);
}
