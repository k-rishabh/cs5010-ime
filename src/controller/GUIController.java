package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;


import javax.swing.JOptionPane;

import controller.command.Histogram;
import controller.command.ImageCommand;
import model.ImageMap;
import util.ImageUtil;
import view.ImageView;

/**
 * Controller class that delegates tasks to the passed in view and model. Built for the GUI view.
 */
public class GUIController extends AbstractController implements Features {
  private final ImageMap imageMap;
  private final ImageView view;

  private final Stack<String> recents;
  private boolean isSaved;

  /**
   * Constructor function for the GUI Controller that initializes itself with the passed in
   * model and view. It automatically initializes a list of known commands as well as a stack of
   * recent images that are used for undoing operations.
   *
   * @param model a mapping from strings to images on which the controller will operate
   * @param view the view which is responsible for displaying and getting user input
   */
  public GUIController(ImageMap model, ImageView view) {
    this.imageMap = model;
    this.view = view;

    commands = new HashMap<>();
    this.initializeCommands();

    this.recents = new Stack<>();

    // mock test mode
    if (model.containsKey("0")) {
      recents.push("0");
    }

    isSaved = true;
  }

  @Override
  public void execute() {
    view.setAllInputs(false);
    view.listen(this);
  }

  /**
   * Displays the split previewed image and asks the user for confirmation.
   *
   * @return true if the user applies, else false
   */
  private boolean displayPreview() {
    if (recents.isEmpty()) {
      view.displayMessage("Nothing to display!", "Error", JOptionPane.ERROR_MESSAGE);
      view.refreshScreen(null, null);
      return false;
    }

    BufferedImage currImg = ImageUtil.toBufferedImage(imageMap.get(recents.peek()));
    return view.splitViewWindow(currImg);
  }

  /**
   * Refreshes the view screen with the current image and its respective histogram.
   */
  private void display() {
    if (recents.isEmpty()) {
      view.displayMessage("Nothing to display!", "Error", JOptionPane.ERROR_MESSAGE);
      view.refreshScreen(null, null);
      return;
    }

    String currImg = recents.peek();
    ImageCommand fn = new Histogram(new String[]{"histogram", currImg, "currHistogram"});
    fn.apply(imageMap);

    BufferedImage curr = ImageUtil.toBufferedImage(imageMap.get(currImg));
    BufferedImage currHist = ImageUtil.toBufferedImage(imageMap.get("currHistogram"));

    view.refreshScreen(curr, currHist);
  }

  /**
   * Simply calls the command stored in the token list. If it is the first command,
   * it must be a load command. It also sets, by default, isSaved to false.
   *
   * @param tokens the command to be executed
   */
  private int callCommand(List<String> tokens) {
    if (recents.isEmpty() && !tokens.get(0).equals("load")) {
      view.displayMessage("Please load an image before performing an operation!",
              "ERROR", JOptionPane.ERROR_MESSAGE);
      return 1;
    }

    String[] args = new String[tokens.size()];
    args = tokens.toArray(args);

    ImageCommand fn = commands.get(args[0]).apply(args);

    int ret = fn.apply(this.imageMap);
    if (ret == 0) {
      this.isSaved = false;
      if (args[args.length - 2].equals("split")) {
        recents.push(args[args.length - 3]);
      } else {
        recents.push(args[args.length - 1]);
      }
    }

    return ret;
  }

  /**
   * If the ratio is between 0 and 100, calls a split command and asks the user for confirmation.
   * If the user confirms, it will call the operation on the complete image.
   * If the ratio lies out of bounds, it will simply apply the operation on the whole image.
   *
   * @param tokens the command to be executed
   * @param ratio the split preview ratio
   */
  private void callSplitCommand(List<String> tokens, int ratio) {
    tokens.add("split");
    tokens.add(Integer.toString(ratio));

    if (callCommand(tokens) == 1) {
      view.displayMessage(
              String.format("ERROR: Unable to perform operation %s, invalid inputs provided!",
                      tokens.get(0)), "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    boolean apply = displayPreview();
    recents.pop();

    // if confirmed, apply on entire image
    if (apply) {
      tokens.remove(tokens.size() - 1);
      tokens.remove(tokens.size() - 1);
      callCommand(tokens);
    }
  }

  @Override
  public void load(File f) {
    if (!isSaved) {
      boolean ret = view.displayConfirmation(
              "Image not saved! Are you sure you want to load new?", "Confirmation");
      if (!ret) {
        return;
      }
    }

    List<String> tokens = new ArrayList<>();
    tokens.add("load");
    tokens.add(f.getPath());

    int curr = 0;
    if (!recents.isEmpty()) {
      curr = Integer.parseInt(recents.peek()) + 1;
    }

    tokens.add(Integer.toString(curr));
    if (callCommand(tokens) == 0) {
      recents.push(Integer.toString(curr));
      this.display();
      this.isSaved = true;
      view.setAllInputs(true);
    }

    view.displayMessage("Successfully loaded the image!", "Success",
            JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void save(File f) {
    List<String> tokens = new ArrayList<>();
    tokens.add("save");
    tokens.add(f.getPath());
    tokens.add(recents.peek());

    if (callCommand(tokens) == 0) {
      this.isSaved = true;
    }

    view.displayMessage("Successfully saved the image!", "Success",
            JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void undo() {
    if (recents.isEmpty()) {
      view.displayMessage("Nothing to undo!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    recents.pop();
    this.display();
  }

  @Override
  public void exit() {
    if (!isSaved) {
      boolean ret = view.displayConfirmation(
              "Image not saved! Are you sure you want to quit?", "Confirmation");
      if (!ret) {
        return;
      }
    }

    System.exit(0);
  }

  /**
   * Applies a general image transformation to an image. It may or may not be a split view
   * operation. It also automatically displays the resulting image in the view.
   *
   * @param command the operation to be applied on the image
   * @param ratio the split ratio, if required, else provide 0
   */
  private void applyTransformation(String command, int ratio) {
    String[] words = command.split(" ");
    List<String> tokens = new ArrayList<>(Arrays.asList(words));

    // apply transformation on most recent image
    int curr = Integer.parseInt(recents.peek());
    tokens.add(Integer.toString(curr));
    tokens.add(Integer.toString(curr + 1));

    if (0 < ratio && ratio < 100) {
      callSplitCommand(tokens, ratio);
    } else {
      callCommand(tokens);
    }

    display();
  }

  @Override
  public void applyGrayscale(String feature, int ratio) {
    if (feature == null) {
      view.displayMessage("Please select a grayscale filter!", "ERROR",
              JOptionPane.ERROR_MESSAGE);
      return;
    }

    switch (feature) {
      case "Red Component":
        applyTransformation("red-component", ratio);
        break;
      case "Green Component":
        applyTransformation("green-component", ratio);
        break;
      case "Blue Component":
        applyTransformation("blue-component", ratio);
        break;
      case "Luma Component":
        applyTransformation("luma-component", ratio);
        break;
      case "Intensity Component":
        applyTransformation("intensity-component", ratio);
        break;
      case "Value Component":
        applyTransformation("value-component", ratio);
        break;
      default:
        applyTransformation(feature, ratio);
        break;
    }
  }

  @Override
  public void applyBlur(int ratio) {
    applyTransformation("blur", ratio);
  }

  @Override
  public void applySharpen(int ratio) {
    applyTransformation("sharpen", ratio);
  }

  @Override
  public void applySepia(int ratio) {
    applyTransformation("sepia", ratio);
  }

  @Override
  public void applyColorCorrect(int ratio) {
    applyTransformation("color-correct", ratio);
  }

  @Override
  public void applyLevelsAdjust(int b, int m, int w, int ratio) {
    if (b > m || m > w) {
      view.displayMessage("The value of black, mid, and white must be in ascending order!",
              "ERROR", JOptionPane.ERROR_MESSAGE);
      return;
    }
    String cmd = String.format("levels-adjust %d %d %d", b, m, w);
    applyTransformation(cmd, ratio);
  }

  @Override
  public void applyBrighten(int val) {
    String cmd = String.format("brighten %d", val);
    applyTransformation(cmd, 0);
  }

  @Override
  public void applyHorizontalFlip() {
    applyTransformation("horizontal-flip", 0);
  }

  @Override
  public void applyVerticalFlip() {
    applyTransformation("vertical-flip", 0);
  }

  @Override
  public void applyCompress(int ratio) {
    String cmd = String.format("compress %d", ratio);
    applyTransformation(cmd, 0);
  }

  @Override
  public void applyDownscale(String height, String width) {
    int h;
    int w;

    try {
      h = Integer.parseInt(height);
      w = Integer.parseInt(width);
    } catch (NumberFormatException e) {
      view.displayMessage("Entered height/width are not numbers!", "ERROR",
              JOptionPane.ERROR_MESSAGE);
      return;
    }

    String cmd = String.format("downscale %d %d", h, w);
    applyTransformation(cmd, 0);
  }
}
