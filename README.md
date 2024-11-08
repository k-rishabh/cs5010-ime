# IME: Image Manipulation and Enhancement

This assignment implements an image processing system with support for image manipulation, filtering, and pixel operations. The system supports operations such as flipping, brightening, and applying various image filters (blur & sharpen) on an image represented as a grid of pixels. Our design is modular, allowing for easy extension and modification of image processing techniques.

## Changes and Justifications
### 1. src/model
- The filter package has been moved from src/model to src/controller. This was done to give the command package access to the filters. This improved our code abstraction since our model functions are now independent of the type of transformation.
- As mentioned in the previous point, we abstracted the functions for all color transformations into one function. Hence the functions for blur, red-component, etc. were removed and abstracted as applyColorFilter() (in both Pixel and Image).
- We added some getter functions in ImageModel, to enable us to perform the new operations with more ease.

### 2. src/controller
- Added the filter package to the controller package for the reasons mentioned above.
- Unified the functioning of ScriptReader, ImageHandler, and CommandInterpreter into a single file ImageController. This was done to incorporate the Command Design Pattern. The movement of functionality is mentioned in the points below.
- ScriptReader was responsible for parsing the script and passing each command to the CommandInterpreter. The parsing of each line is now performed in the ImageController.
- CommandInterpreter was responsible for understanding the command and delegating it to the respective function in the ImageHandler. Since we now have a command map, this functioning was redundant.
- ImageHandler was responsible for applying a transformation on the given image. This is now handled in the individual classes for each transformation in the command package.

### 3. src/util
- Added an applySplit() method to the ImageTransformer class. This was done to incorporate split view transformations in our program.

### 4. General
- Renaming of some files for better readability.
- Following the feedback from the previous assignment, we removed many static methods. Hence, our ProgramRunner now initializes a controller.

## Completeness
All parts of our code are working and complete. However, our controller tests do not use the MockImage class to perform its opertaions.

## How to Run the Program

To run the program in CLI input mode, use the following commands:
```cmd
javac ProgramRunner.java
java ProgramRunner
```

To run the program with a script file input, use the following commands:
```cmd
javac ProgramRunner.java
java ProgramRunner -file <script-file-path>
```

If an invalid script file is provided, it defaults to CLI mode.

## Image Citation
**We own all the images used in this assignment.**