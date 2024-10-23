package controller;

public class CommandInterpreter {
  static ImageHandler images = new ImageHandler();

  public static void execute(String command, int lineNo) {
    command = command.toLowerCase();

    // ignore if command is comment
    if (command.charAt(0) == '#') {
      return;
    }

    String[] words = command.split(" ");

    switch (words[0]) {
      case "exit":
        System.exit(0);
        break;

      case "load":
        if (words.length == 3) {
          images.loadImage(words[1], words[2]);
        } else {
          System.out.printf("Invalid load command on line %d!\n", lineNo);
        }
        break;

      case "save":
        if (words.length == 3) {
          images.saveImage(words[1], words[2]);
        } else {
          System.out.printf("Invalid save command on line %d!\n", lineNo);
        }
        break;

      case "red-component":
        if (words.length == 3) {
          int fail = images.redComponent(words[1], words[2]);
          if (fail != 0) {
            System.out.printf("red-component on line %d failed!\n", lineNo);
          }
        } else {
          System.out.printf("Invalid red-component command on line %d!\n", lineNo);
        }
        break;

      case "green-component":
        if (words.length == 3) {
          int fail = images.greenComponent(words[1], words[2]);
          if (fail != 0) {
            System.out.printf("red-component on line %d failed!\n", lineNo);
          }
        } else {
          System.out.printf("Invalid green-component command on line %d!\n", lineNo);
        }
        break;

      case "blue-component":
        if (words.length == 3) {
          int fail = images.blueComponent(words[1], words[2]);
          if (fail != 0) {
            System.out.printf("red-component on line %d failed!\n", lineNo);
          }
        } else {
          System.out.printf("Invalid blue-component command on line %d!\n", lineNo);
        }
        break;

      case "value-component":
        if (words.length == 3) {
          int fail = images.valueComponent(words[1], words[2]);
          if (fail != 0) {
            System.out.printf("red-component on line %d failed!\n", lineNo);
          }
        } else {
          System.out.printf("Invalid value-component command on line %d!\n", lineNo);
        }
        break;

      case "intensity-component":
        if (words.length == 3) {
          int fail = images.intensityComponent(words[1], words[2]);
          if (fail != 0) {
            System.out.printf("red-component on line %d failed!\n", lineNo);
          }
        } else {
          System.out.printf("Invalid intensity-component command on line %d!\n", lineNo);
        }
        break;

      case "luma-component":
        if (words.length == 3) {
          int fail = images.lumaComponent(words[1], words[2]);
          if (fail != 0) {
            System.out.printf("red-component on line %d failed!\n", lineNo);
          }
        } else {
          System.out.printf("Invalid luma-component command on line %d!\n", lineNo);
        }
        break;

      case "horizontal-flip":
        if (words.length == 3) {
          int fail = images.horizontalFlip(words[1], words[2]);
          if (fail != 0) {
            System.out.printf("red-component on line %d failed!\n", lineNo);
          }
        } else {
          System.out.printf("Invalid horizontal-flip command on line %d!\n", lineNo);
        }
        break;

      case "vertical-flip":
        if (words.length == 3) {
          int fail = images.verticalFlip(words[1], words[2]);
          if (fail != 0) {
            System.out.printf("red-component on line %d failed!\n", lineNo);
          }
        } else {
          System.out.printf("Invalid vertical-flip command on line %d!\n", lineNo);
        }
        break;

      case "brighten":
        if (words.length == 4) {
          int fail = images.brighten(Integer.parseInt(words[1]), words[2], words[3]);
          if (fail != 0) {
            System.out.printf("red-component on line %d failed!\n", lineNo);
          }
        } else {
          System.out.printf("Invalid brighten command on line %d!\n", lineNo);
        }
        break;

      case "rgb-split":
        if (words.length == 5) {
          int fail = images.rgbSplit(words[1], words[2], words[3], words[4]);
          if (fail != 0) {
            System.out.printf("red-component on line %d failed!\n", lineNo);
          }
        } else {
          System.out.printf("Invalid rgb-split command on line %d!\n", lineNo);
        }
        break;

      case "rgb-combine":
        if (words.length == 5) {
          int fail = images.rgbCombine(words[1], words[2], words[3], words[4]);
          if (fail != 0) {
            System.out.printf("red-component on line %d failed!\n", lineNo);
          }
        } else {
          System.out.printf("Invalid rgb-combine command on line %d!\n", lineNo);
        }
        break;

      case "blur":
        if (words.length == 3) {
          int fail = images.blur(words[1], words[2]);
          if (fail != 0) {
            System.out.printf("red-component on line %d failed!\n", lineNo);
          }
        } else {
          System.out.printf("Invalid blur command on line %d!\n", lineNo);
        }
        break;

      case "sharpen":
        if (words.length == 3) {
          int fail = images.sharpen(words[1], words[2]);
          if (fail != 0) {
            System.out.printf("red-component on line %d failed!\n", lineNo);
          }
        } else {
          System.out.printf("Invalid sharpen command on line %d!\n", lineNo);
        }
        break;

      case "sepia":
        if (words.length == 3) {
          int fail = images.sepia(words[1], words[2]);
          if (fail != 0) {
            System.out.printf("red-component on line %d failed!\n", lineNo);
          }
        } else {
          System.out.printf("Invalid sepia command on line %d!\n", lineNo);
        }
        break;

      case "run":
        if (words.length == 2) {
          ScriptRunner.go(words[1]);
        } else {
          System.out.printf("Invalid run command on line %d!\n", lineNo);
        }

      default:
        System.out.printf("Unknown command: " + command);
        break;
    }
  }
}
