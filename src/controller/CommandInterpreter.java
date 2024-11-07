package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import controller.command.Blur;
import controller.command.Brighten;
//import controller.command.ColorCorrect;
import controller.command.ComponentBlue;
import controller.command.ComponentGreen;
import controller.command.ComponentIntensity;
import controller.command.ComponentLuma;
import controller.command.ComponentRed;
import controller.command.ComponentValue;
import controller.command.FlipHorizontal;
import controller.command.FlipVertical;
//import controller.command.Histogram;
import controller.command.Load;
import controller.command.Save;
import controller.command.Sepia;
import controller.command.Sharpen;

import static java.lang.Integer.parseInt;

/**
 * Functional class that is responsible for interpreting and executing each line of code.
 * Also provides functionality to run another script file, or exit the program.
 * Consists of a static ImageHandler which contains and performs operations on all images.
 */
public class CommandInterpreter {
  Map<String, Function<Scanner, ImageCommand>> commands;
  protected Readable in;
  protected Appendable out;

  public CommandInterpreter(Readable in, Appendable out) {
    this.in = in;
    this.out = out;

    this.commands = new HashMap<>();
  }

  public void apply(ImageHandler img) throws IOException {
    Scanner scan = new Scanner(this.in);
    commands.put("load", s -> new Load(s.next(), s.next()));
    commands.put("save", s -> new Save(s.next(), s.next()));
    commands.put("blur", s -> new Blur(s.next(), s.next()));
    commands.put("brighten", s -> new Brighten(s.nextInt(), s.next(), s.next()));
//    commands.put("color-correct", s -> new ColorCorrect(s.next(), s.next()));
    commands.put("sepia", s -> new Sepia(s.next(), s.next()));
//    commands.put("histogram", s -> new Histogram(s.next(), s.next()));
    commands.put("sharpen", s -> new Sharpen(s.next(), s.next()));
    commands.put("vertical-flip", s -> new FlipVertical(s.next(), s.next()));
    commands.put("horizontal-flip", s -> new FlipHorizontal(s.next(), s.next()));
    commands.put("green-component", s -> new ComponentGreen(s.next(), s.next()));
    commands.put("blue-component", s -> new ComponentBlue(s.next(), s.next()));
    commands.put("red-component", s -> new ComponentRed(s.next(), s.next()));
    commands.put("luma-component", s -> new ComponentLuma(s.next(), s.next()));
    commands.put("intensity-component", s -> new ComponentIntensity(s.next(), s.next()));
    commands.put("value-component", s -> new ComponentValue(s.next(), s.next()));

    while (scan.hasNext()) {
      try {
        ImageCommand c;
        String in = scan.next();
        if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
          return;
        }
        Function<Scanner, ImageCommand> cmd = commands.getOrDefault(in, null);
        if (cmd == null) {
          throw new IllegalArgumentException("Please provide a valid command.");
        } else {
          c = cmd.apply(scan);
          c.apply(img);
        }
      } catch (IllegalArgumentException | InputMismatchException e) {
        out.append(e.getMessage());
      }
    }


//    switch (words[0]) {
//      case "exit":
//        System.exit(0);
//        break;
//
//      case "load":
//        if (words.length == 3) {
//          images.loadImage(words[1], words[2]);
//        } else {
//          System.out.printf("Error: Invalid load command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "save":
//        if (words.length == 3) {
//          images.saveImage(words[1], words[2]);
//        } else {
//          System.out.printf("Error: Invalid save command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "red-component":
//        if (words.length == 3) {
//          int fail = images.redComponent(words[1], words[2]);
//          if (fail != 0) {
//            System.out.printf("Error: red-component on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid red-component command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "green-component":
//        if (words.length == 3) {
//          int fail = images.greenComponent(words[1], words[2]);
//          if (fail != 0) {
//            System.out.printf("Error: red-component on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid green-component command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "blue-component":
//        if (words.length == 3) {
//          int fail = images.blueComponent(words[1], words[2]);
//          if (fail != 0) {
//            System.out.printf("Error: red-component on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid blue-component command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "value-component":
//        if (words.length == 3) {
//          int fail = images.valueComponent(words[1], words[2]);
//          if (fail != 0) {
//            System.out.printf("Error: red-component on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid value-component command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "intensity-component":
//        if (words.length == 3) {
//          int fail = images.intensityComponent(words[1], words[2]);
//          if (fail != 0) {
//            System.out.printf("Error: red-component on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid intensity-component command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "luma-component":
//        if (words.length == 3) {
//          int fail = images.lumaComponent(words[1], words[2]);
//          if (fail != 0) {
//            System.out.printf("Error: red-component on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid luma-component command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "color-correct":
//        if (words.length == 3) {
//          int fail = images.colorCorrectness(words[1], words[2]);
//          if (fail != 0) {
//            System.out.printf("Error: color-correct on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid color-correct command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "level-adjust":
//        if (words.length == 6) {
//          int fail = images.levelAdjust(words[4], words[5],parseInt(words[1]),parseInt(words[2]),parseInt(words[3]));
//          if (fail != 0) {
//            System.out.printf("Error: color-correct on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid color-correct command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "histogram":
//        if(words.length == 3) {
//          int fail = images.histogram(words[1], words[2]);
//          if (fail != 0) {
//            System.out.printf("Error: histogram on line %d failed!\n", lineNo);
//          }
//        }
//        else{
//          System.out.printf("Error: Invalid histogram command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "horizontal-flip":
//        if (words.length == 3) {
//          int fail = images.horizontalFlip(words[1], words[2]);
//          if (fail != 0) {
//            System.out.printf("Error: red-component on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid horizontal-flip command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "vertical-flip":
//        if (words.length == 3) {
//          int fail = images.verticalFlip(words[1], words[2]);
//          if (fail != 0) {
//            System.out.printf("Error: red-component on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid vertical-flip command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "brighten":
//        if (words.length == 4) {
//          int fail = images.brighten(parseInt(words[1]), words[2], words[3]);
//          if (fail != 0) {
//            System.out.printf("Error: red-component on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid brighten command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "rgb-split":
//        if (words.length == 5) {
//          int fail = images.rgbSplit(words[1], words[2], words[3], words[4]);
//          if (fail != 0) {
//            System.out.printf("Error: red-component on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid rgb-split command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "rgb-combine":
//        if (words.length == 5) {
//          int fail = images.rgbCombine(words[1], words[2], words[3], words[4]);
//          if (fail != 0) {
//            System.out.printf("Error: red-component on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid rgb-combine command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "blur":
//        if (words.length == 3) {
//          int fail = images.blur(words[1], words[2]);
//          if (fail != 0) {
//            System.out.printf("Error: red-component on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid blur command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "sharpen":
//        if (words.length == 3) {
//          int fail = images.sharpen(words[1], words[2]);
//          if (fail != 0) {
//            System.out.printf("Error: red-component on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid sharpen command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "sepia":
//        if (words.length == 3) {
//          int fail = images.sepia(words[1], words[2]);
//          if (fail != 0) {
//            System.out.printf("Error: red-component on line %d failed!\n", lineNo);
//          }
//        } else {
//          System.out.printf("Error: Invalid sepia command on line %d!\n", lineNo);
//        }
//        break;
//
//      case "run":
//        if (words.length == 2) {
//          ScriptReader.build(words[1]);
//        } else {
//          System.out.printf("Error: Invalid run command on line %d!\n", lineNo);
//        }
//        break;
//
//      default:
//        System.out.printf("Error: Unknown command \"%s\" on line %d!\n", command, lineNo);
//        break;

  }
}

