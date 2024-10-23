package controller;

public class CommandInterpreter {
  public static void execute(String command) {
    command = command.toLowerCase();

    // ignore if command is comment
    if (command.charAt(0) == '#') {
      return;
    }

    String[] words = command.split(" ");
    ImageHandler images = new ImageHandler();

    switch (words[0]) {
      case "exit":
        System.exit(0);
        break;

      case "load":
        if (words.length == 3) {
          images.loadImage(words[1], words[2]);
        } else {
          System.out.println("Invalid load command!");
        }
        break;

      case "save":
        if (words.length == 3) {
          images.saveImage(words[1], words[2]);
        } else {
          System.out.println("Invalid save command!");
        }
        break;

      case "red-component":
        if (words.length == 3) {
          images.redComponent(words[1], words[2]);
        } else {
          System.out.println("Invalid red-component command!");
        }
        break;

      case "green-component":
        if (words.length == 3) {
          images.greenComponent(words[1], words[2]);
        } else {
          System.out.println("Invalid green-component command!");
        }
        break;

      case "blue-component":
        if (words.length == 3) {
          images.blueComponent(words[1], words[2]);
        } else {
          System.out.println("Invalid blue-component command!");
        }
        break;

      case "value-component":
        if (words.length == 3) {
          images.valueComponent(words[1], words[2]);
        } else {
          System.out.println("Invalid value-component command!");
        }
        break;

      case "intensity-component":
        if (words.length == 3) {
          images.intensityComponent(words[1], words[2]);
        } else {
          System.out.println("Invalid intensity-component command!");
        }
        break;

      case "luma-component":
        if (words.length == 3) {
          images.lumaComponent(words[1], words[2]);
        } else {
          System.out.println("Invalid luma-component command!");
        }
        break;

      case "horizontal-flip":
        if (words.length == 3) {
          images.horizontalFlip(words[1], words[2]);
        } else {
          System.out.println("Invalid horizontal-flip command!");
        }
        break;

      case "vertical-flip":
        if (words.length == 3) {
          images.verticalFlip(words[1], words[2]);
        } else {
          System.out.println("Invalid vertical-flip command!");
        }
        break;

      case "brighten":
        if (words.length == 4) {
          images.brighten(Integer.parseInt(words[1]), words[2], words[3]);
        } else {
          System.out.println("Invalid brighten command!");
        }
        break;

      case "rgb-split":
        if (words.length == 5) {
          images.rgbSplit(words[1], words[2], words[3], words[4]);
        } else {
          System.out.println("Invalid rgb-split command!");
        }
        break;

      case "rgb-combine":
        if (words.length == 5) {
          images.rgbCombine(words[1], words[2], words[3], words[4]);
        } else {
          System.out.println("Invalid rgb-combine command!");
        }
        break;

      case "blur":
        if (words.length == 3) {
          images.blur(words[1], words[2]);
        } else {
          System.out.println("Invalid blur command!");
        }
        break;

      case "sharpen":
        if (words.length == 3) {
          images.sharpen(words[1], words[2]);
        } else {
          System.out.println("Invalid sharpen command!");
        }
        break;

      case "sepia":
        if (words.length == 3) {
          images.sepia(words[1], words[2]);
        } else {
          System.out.println("Invalid sepia command!");
        }
        break;

      case "run":
        if (words.length == 2) {
          ScriptRunner.go(words[1]);
        } else {
          System.out.println("Invalid run command!");
        }

      default:
        System.out.println("Unknown command: " + command);
        break;
    }
  }
}
