//package controller;
//
//import java.awt.image.BufferedImage;
//import java.util.List;
//import java.util.Stack;
//
//import controller.command.Histogram;
//import controller.command.ImageCommand;
//import model.ImageMap;
//import view.ImageView;
//
//public class GUIController extends AbstractController {
//  private final ImageMap imageMap;
//  private final ImageView view;
//
//  private final Stack<String> recents;
//  private boolean isSaved;
//
//  public GUIController(ImageMap model, ImageView view) {
//    this.imageMap = model;
//    this.view = view;
//    this.recents = new Stack<>();
//    isSaved = false;
//  }
//
//  @Override
//  public void execute() {
////    view.listen(this);
//  }
//
//  private void display() {
//    if (recents.isEmpty()) {
////      view.refresh(null, null);
//      return;
//    }
//
//    String currImg = recents.peek();
//    ImageCommand fn = new Histogram(new String[]{currImg, "currHistogram"});
//    fn.apply(imageMap);
//
//    BufferedImage curr = imageMap.get(currImg).getBufferedImage();
//    BufferedImage currHist = imageMap.get("currHistogram").getBufferedImage();
//
////    view.refresh(curr, currHist);
//  }
//
//  public void applyImageTransform(List<String> tokens, int ratio) {
//    int currImg;
//    if (!recents.isEmpty()) {
//      currImg = Integer.parseInt(recents.peek());
//    } else {
//      currImg = 0;
//    }
//
//    // if user tries to split a non-split command
//    // TODO: split with load is ok
//    if (0 < ratio && ratio < 100 && !splitCommands.contains(tokens.get(0))) {
////      view.popUpError("This operation cannot be previewed!");
//      return;
//    }
//
//    // first command must be load
//    if (currImg == 0 && !tokens.get(0).equals("load")) {
//      return;
//    } else if (currImg != 0 && tokens.get(0).equals("load") && !isSaved) {
////      boolean go = view.popUpConfirmation("Would you like to load the new image without saving?");
////      if (!go) {
////         return;
////      }
//    }
//
//    // if load command, check if saved, set curr to 0
//
//    // if command is undo
//    if (tokens.get(0).equals("undo")) {
//      recents.pop();
//      this.display();
//      return;
//    }
//
//    // append image names to command
//    tokens.add(Integer.toString(currImg));
//    this.isSaved = true;
//    if (!tokens.get(0).equals("load") && !tokens.get(0).equals("save")) {
//      tokens.add(Integer.toString(currImg + 1));
//      recents.push(Integer.toString(currImg + 1));
//      this.isSaved = false;
//    }
//
//    // append split to command if applicable
//    if (0 < ratio && ratio < 100 && splitCommands.contains(tokens.get(0))) {
//      tokens.add("split");
//      tokens.add(Integer.toString(ratio));
//    }
//
//    String[] args = new String[tokens.size()];
//    args = tokens.toArray(args);
//    ImageCommand fn = commands.get(args[0]).apply(args);
//
//    fn.apply(imageMap);
////    this.out.append(String.format("%s performed successfully!", args[0]));
//    this.display();
//  }
//}
