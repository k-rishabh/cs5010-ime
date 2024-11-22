# GRIME: Graphical Image Manipulation and Enhancement

This assignment implements a graphical image processing application with support for image manipulation, filtering, and pixel operations. The system supports operations such as flipping, brightening, and applying various image filters (blur & sharpen) on an image represented as a grid of pixels. Our design is modular, allowing for easy extension and modification of image processing techniques.

![GRIME_GUI](./res/GRIME-GUI.jpg)


## Changes and Justifications
### 1. src/model
- Added an ImageMap interface and ImageMapModel class which represent a model of our program. It includes a mapping from string names to images. It is used by the controller to store all the current images the user has used.
- Following feedback from our previous assignment, we removed the getColor methods from the ImageModel interface, since they were redundant.
- Improved our MockImage class to work better with the view controller testing for GUI. This includes changing the outputs for some of the functions.

### 2. src/controller
- To add functionality for masking, we had to change some of the commands in the command package to support the new syntax.
- Added an ImageController interface to abstract the execute (go) method for both controllers.
- Due to the new interface, we also added a new abstract class to initialize the list of known commands.
- Improved the error handling of the ScriptController.

### 3. src/util
- Added a function getBufferedImage() to ImageUtil. This was done in order to support visualization in GUI. This function reduced redundancy of this transformation code in multiple places.
- Removed the ImageTransformer class since its functionality was now implemented in the newly added ImageMap.

### 4. General
- Renaming of some files for better readability.

## Completeness
All parts of our code are working and complete. Additionally, we have completed both tasks for extra credits.

## Usage - How to Run the Program

To run the program in GUI mode, use the following commands:
```cmd
javac ProgramRunner.java
java ProgramRunner
```

To run the program in CLI mode, use the following commands:
```cmd
javac ProgramRunner.java
java ProgramRunner -text
```

To run the program with a script file input, use the following commands:
```cmd
javac ProgramRunner.java
java ProgramRunner -file <script-file-path>
```

To know about how to use our application, follow the [USEME.MD](USEME.md)

## Image Citation
**We own all the images used in this assignment.**