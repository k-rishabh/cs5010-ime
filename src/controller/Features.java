package controller;

import java.io.File;

/**
 * Interface which specifies the functionality of a GUI controller. It defines a unique function
 * for each callback method (each button) in the GUI.
 */
public interface Features {
  /**
   * Loads an image into the GUI. Requires the user to select a file from their explorer, of the
   * specified file formats.
   *
   * @param f the file to be opened and displayed in the GUI
   */
  void load(File f);

  /**
   * Saves the current image that is displayed in the GUI. The user is required to select a file
   * path through their explorer, which is where the image will be saved, in the specified file
   * formats.
   *
   * @param f the location of the image to be saved
   */
  void save(File f);

  /**
   * Undoes the latest operation on the image and displays the most recent image.
   */
  void undo();

  /**
   * Exits the program. If the user hasn't saved the current image, prompts the user to save first.
   */
  void exit();

  /**
   * Applies the selected grayscale on the image. The user selection is passed in as a string.
   * If the user is on preview mode, the ratio will be used to preview the split image.
   *
   * @param feature the type of grayscale to be applied
   * @param ratio the split ratio in preview mode
   */
  void applyGrayscale(String feature, int ratio);

  /**
   * Applies a blur filter on the current image. If the user is on preview mode,
   * the ratio will be used to preview the split image.
   *
   * @param ratio the split ratio in preview mode
   */
  void applyBlur(int ratio);

  /**
   * Applies a sharpen filter on the current image. If the user is on preview mode,
   * the ratio will be used to preview the split image.
   *
   * @param ratio the split ratio in preview mode
   */
  void applySharpen(int ratio);

  /**
   * Applies a sepia filter on the current image. If the user is on preview mode,
   * the ratio will be used to preview the split image.
   *
   * @param ratio the split ratio in preview mode
   */
  void applySepia(int ratio);

  /**
   * Color corrects the current image by adjusting its color peaks.
   * If the user is on preview mode, the ratio will be used to preview the split image.
   *
   * @param ratio the split ratio in preview mode
   */
  void applyColorCorrect(int ratio);

  /**
   * Adjusts the black point, mid-tones and white point of the image.
   * If the user is on preview mode, the ratio will be used to preview the split image.
   *
   * @param b the black point value
   * @param m the mid-tone value
   * @param w the white point value
   * @param ratio the split ratio in preview mode
   */
  void applyLevelsAdjust(int b, int m, int w, int ratio);

  /**
   * Brightens the image by the specified amount.
   *
   * @param val the amount to be brightened by
   */
  void applyBrighten(int val);

  /**
   * Flips the current image horizontally.
   */
  void applyHorizontalFlip();

  /**
   * Flips the current image vertically.
   */
  void applyVerticalFlip();

  /**
   * Compresses the current image by the given compression ratio.
   *
   * @param ratio the compression ratio
   */
  void applyCompress(int ratio);

  /**
   * Downscales (reduces the size) of the current image. Sets the height and width of the image
   * to the height and width provided by the user.
   *
   * @param h the new height of the image
   * @param w the new width of the image
   */
  void applyDownscale(String h, String w);
}
