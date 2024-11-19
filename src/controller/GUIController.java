package controller;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import controller.command.Histogram;
import controller.command.ImageCommand;
import model.ImageMap;
import model.ImageModel;
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
    view.addFeatures(this);
    commands = new HashMap<>();
    this.initializeCommands();
    this.recents = new Stack<>();
    isSaved = false;
  }

  @Override
  public void execute() {
//    view.listen(this);
  }

  private void display() {
    if (recents.isEmpty()) {
      view.refreshScreen(null, null);
      return;
    }

    String currImg = recents.peek();
    ImageCommand fn = new Histogram(new String[]{"histogram", currImg, "currHistogram"});
    fn.apply(imageMap);
//    System.out.println(imageMap.toString());
    BufferedImage curr = ImageUtil.toBufferedImage(imageMap.get(currImg));
    BufferedImage currHist = ImageUtil.toBufferedImage(imageMap.get("currHistogram"));

    view.refreshScreen(curr, currHist);
  }

  public void applyImageTransform(List<String> tokens, int ratio) {
    int currImg;
    if (!recents.isEmpty()) {
      currImg = Integer.parseInt(recents.peek());
    } else {
      currImg = 0;
    }
    System.out.println(tokens);
    // if user tries to split a non-split command
    // TODO: split with load is ok
    if (0 < ratio && ratio < 100 && !splitCommands.contains(tokens.get(0))) {
      view.displayErrorMessage("This operation cannot be previewed!");
      return;
    }

    // first command must be load
    if (currImg == 0 && !tokens.get(0).equals("load")) {
      return;
    } else if (currImg != 0 && tokens.get(0).equals("load") && !isSaved) {
//      boolean go = view.popUpConfirmation("Would you like to load the new image without saving?");
//      if (!go) {
//         return;
//      }
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

    if(!tokens.get(0).equals("load") && !tokens.get(0).equals("save")){
      tokens.add(Integer.toString(currImg + 1));
    }

    this.isSaved = true;

    if (!tokens.get(0).equals("save")) {
      recents.push(Integer.toString(currImg + 1));
      this.isSaved = false;
    }

    // append split to command if applicable
    if (0 < ratio && ratio < 100 && splitCommands.contains(tokens.get(0))) {
      tokens.add("split");
      tokens.add(Integer.toString(ratio));
    }

    String[] args = new String[tokens.size()];
    args = tokens.toArray(args);
    System.out.println(Arrays.toString(args));
    ImageCommand fn = commands.get(args[0]).apply(args);

    fn.apply(this.imageMap);
//    BufferedImage curr = ImageUtil.toBufferedImage(imageMap.get("0"));
//    this.out.append(String.format("%s performed successfully!", args[0]));
    this.display();
  }
}
