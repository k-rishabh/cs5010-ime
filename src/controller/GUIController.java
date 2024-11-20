package controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.swing.*;

import controller.command.Histogram;
import controller.command.ImageCommand;
import model.ImageMap;
import util.ImageUtil;
import view.ImageView;

public class GUIController extends AbstractController {
  private final ImageMap imageMap;
  private final ImageView view;

  private final Stack<String> recents;
  private boolean isSaved;

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

    isSaved = false;
  }

  @Override
  public void execute() {
    view.listen(this);
  }

  private void display() {
    if (recents.isEmpty()) {
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

  private boolean displayPreview() {
    if (recents.isEmpty()) {
      view.refreshScreen(null, null);
      return false;
    }

    BufferedImage currImg = ImageUtil.toBufferedImage(imageMap.get(recents.peek()));
    return view.splitViewWindow(currImg);
  }

  @Override
  public void applyImageTransform(List<String> tokens, int ratio) {
    int currImg;

    if (!recents.isEmpty()) {
      currImg = Integer.parseInt(recents.peek());
    } else {
      currImg = 0;
    }

    // if user tries to split a non-split command
    // TODO: split with load is ok
    if (0 < ratio && ratio < 100 && !splitCommands.contains(tokens.get(0))) {
      view.displayMessage("This operation cannot be previewed!", "ERROR",
              JOptionPane.ERROR_MESSAGE);
      return;
    }

    // first command must be load
    if (recents.empty() && !tokens.get(0).equals("load")) {
      return;
    } else if (currImg != 0 && tokens.get(0).equals("load") && !isSaved) {
      boolean go = view.displayConfirmation("Would you like to load the new image without saving?",
              "Confirmation");
      if (!go) {
         return;
      }
    }

    // if load command, check if saved, set curr to 0

    // if command is undo
    if (tokens.get(0).equals("undo")) {
      recents.pop();
      this.display();
      return;
    }

    // append image names to command
    tokens.add(Integer.toString(currImg));
    System.out.println(tokens);
    if (tokens.get(0).equals("load") || tokens.get(0).equals("save")) {
      recents.push(Integer.toString(currImg));
      this.isSaved = true;
    } else {
      tokens.add(Integer.toString(currImg + 1));
      recents.push(Integer.toString(currImg + 1));
      this.isSaved = false;
    }

    // if it is a valid split command
    if (0 < ratio && ratio < 100 && splitCommands.contains(tokens.get(0))) {
      tokens.add("split");
      tokens.add(Integer.toString(ratio));

      String[] args = new String[tokens.size()];
      args = tokens.toArray(args);
      ImageCommand fn = commands.get(args[0]).apply(args);

      fn.apply(this.imageMap);

      // if user cancels the operation
      if (!this.displayPreview()) {
        recents.pop();
//        this.display();
        return;
      } else {
        tokens.remove(tokens.size() - 1);
        tokens.remove(tokens.size() - 1);
      }
    }

    // apply complete operation
    String[] args = new String[tokens.size()];
    args = tokens.toArray(args);

    ImageCommand fn = commands.get(args[0]).apply(args);
    fn.apply(this.imageMap);

    this.display();
  }
}
