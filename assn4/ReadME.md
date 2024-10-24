# IME: Image Manipulation and Enhancement

This assignment implements an image processing system with support for image manipulation, filtering, and pixel operations. The system supports operations such as flipping, brightening, and applying various image filters (blur & sharpen) on an image represented as a grid of pixels. Our design is modular, allowing for easy extension and modification of image processing techniques.

## Model Components

### 1. `ImageADT` Interface
The `ImageADT` interface contains methods for performing various image operations like flipping, filtering, color component extraction, and deep copying. Implementations of this interface provide specific ways to manipulate images. The interface ensures that any image can apply the operations, such as:

- `valueComponent()`, `intensityComponent()`, `lumaComponent()` – Extract different grayscale representations of an image.
- `redComponent()`, `greenComponent()`, `blueComponent()` - Extracts the red, green, and blue component of the image.
- `horizontalFlip()`, `verticalFlip()` – Flip the image along horizontal and vertical axes.
- `brighten(int val)` – Brighten or darken an image by adding/subtracting a constant value with the pixel values.
- `blur()`, `sharpen()` – Apply blur and sharpen filters to an image.
- `sepia()` – Apply a sepia tone to an image.
- `split()` – Split the image into its red, green, and blue color channels.
- `combine()` – Combine split images back into one image.
- `deepCopy()` – Create a deep copy of an image to avoid reference sharing.

### 2. `Image` Abstract Class
The `Image` abstract class implements the `ImageADT` interface methods and serves as a base class for specific types of images. Features of the class include:

- Manages a 2D array of `PixelADT` objects (`pixels`), representing the image's pixels.
- Implements basic operations like flipping, brightening, and component extraction.
- Abstract methods like `split()`, `combine()`, and `deepCopy()` must be implemented by concrete subclass - `RGBImage`.

### 3. `RGBImage` Class
The `RGBImage` class extends the `Image` abstract class and provides an implementation specifically for images with RGB pixels. Key features include:

- Constructor methods for creating an `RGBImage` from a 2D array of `PixelADT` objects or packed integer values representing RGB color components.
- Implementation of the `split()` method that separates the image into three color channels (red, green, and blue).
- Deep copying and pixel manipulation functionality.

### 4. `PixelADT` Interface
The `PixelADT` interface defines the operations for an individual pixel. A pixel in the image can perform operations like brightening, applying filters, or converting to grayscale based on value, intensity, or luma. It includes methods such as:

- `brighten(int val)` – Adjust the brightness of the pixel.
- `applyFilter(double val)` – Apply a filter matrix to modify pixel values.
- `applySepia()`, `applyRedTint()`, `applyGreenTint()`, `applyBlueTint()` – Modify the pixel with color effects.
- `showValue()`, `showIntensity()`, `showLuma()` – Convert the pixel to grayscale.
- `deepCopy()` – Create a deep copy of the pixel to prevent reference issues.

### 5. `RGBPixel` Class
The `RGBPixel` class implements `PixelADT` and represents a pixel with red, green, and blue components. Features include:

- Constructing a pixel from RGB values or a packed integer value.
- Implementing all pixel-level operations like `brighten()`, `applySepia()`, and `showLuma()`.
- Ensuring pixel values are clamped to a valid range (0–255) after operations.
- Support for creating deep copies of pixel objects.

### 6. `PixelProcessor` Utility Class
The `PixelProcessor` class is a utility class that provides helper functions to apply operations to the entire image by iterating over the pixel array. It uses a higher order function (`Consumer`) to apply pixel-level operations, making the code more modular and reusable.

- `apply(PixelADT[][] pixels, Consumer<PixelADT> func)` – Takes a function and applies it to each pixel in the 2D array of pixels.

### 7. `FilterADT` Interface
The `FilterADT` interface defines the contract for any filter that can be applied to an image. Each filter contains a filter matrix (kernel) that will modify the pixel values of an image. It includes methods to:

- `setFilter(double[][] newFilter)` – Set the filter matrix.
- `getFilter()` – Retrieve the filter matrix.

### 8. `Filter` Abstract Class
The `Filter` abstract class provides a basic implementation of the `FilterADT` interface. It serves as a base class for specific filters (blur, sharpen) and stores the filter matrix. Subclasses can define specific filter matrices.

### 9. `BlurFilter` Class
The `BlurFilter` class extends `Filter` and provides a predefined filter matrix for blurring an image. The filter matrix is a 3x3 kernel that applies a Gaussian blur effect by averaging the surrounding pixels.

### 10. `SharpenFilter` Class
The `SharpenFilter` class extends `Filter` and provides a 5x5 filter matrix that sharpens an image by increasing the contrast between adjacent pixels. This enhances edges and fine details in the image.

## Controller Components

### 1. `ImageHandler` Class
The `ImageHandler` class is responsible for handling images and applying transformations. It stores images in a `HashMap` and provides methods to perform various transformations such as flipping, splitting, and filtering images. The key methods include:

- **loadImage(filePath, imageName)**: Loads an image from a file path and stores it in memory.
- **saveImage(filePath, imageName)**: Saves an image from memory to a file path.
- **brighten(val, srcName, destName)**: Brightens an image by a given value.
- **rgbSplit(srcName, redName, greenName, blueName)**: Splits an image into its RGB components.
- **rgbCombine(destName, redName, greenName, blueName)**: Combines images into a single image using RGB channels.

### 2. `CommandInterpreter` Class
The `CommandInterpreter` class parses and interprets commands from a script. It maps commands to the corresponding methods in the `ImageHandler` class.

- **execute(command, lineNo)**: Executes a given command by determining the operation and passing it to the `ImageHandler`.

### 3. `ImageTransformer` Class
The `ImageTransformer` class provides extends the ImageHandler class and provides a modular way to apply image transformations using higher order functions.

- **apply(srcName, destName, Consumer<Image> func)**: Applies a transformation to a given image.

### 4. `ScriptRunner` Class
The `ScriptRunner` class reads a script file and executes commands line-by-line using the `CommandInterpreter`.

- **go(scriptFilePath)**: Reads the script file and executes each command.

## `Util` Component

### `ImageUtil` Class
The `ImageUtil` class provides static methods to load and save images in raster and raw formats.

- **loadImageRaster(filePath)**: Loads a raster image (JPG/PNG).
- **loadImageRaw(filePath)**: Loads a raw image (PPM format).
- **saveImageRaster(filePath, image)**: Saves an image in raster format.
- **saveImageRaw(filePath, image)**: Saves an image in raw format.

## Design Considerations

- **Separation of operations**: The assignment is designed with clear separation between the pixel-level operations (`PixelADT` and `RGBPixel`) and image-level operations (`ImageADT` and `RGBImage`). Filters are separated into their own classes to make the system easily extendable.
- **Modularity**: New types of images or filters can be added without modifying the existing classes. For example, you can add a new filter by extending the `Filter` class and defining a custom filter matrix.
- **Removing redundancy**: By the use of `PixelProcessor` and `ImageTransformer` we eliminated the need to write repetitive loops to iterate over the image's pixels. The code passes a lambda function that defines the transformation.

## How to run the script

- If the assignment is run on IntelliJ, then run ProgramRunner.java by adding the script file path in the run configuration.
- On command line the assignment can be run by the following commands:
```cmd
javac ProgramRunner.java
java ProgramRunner <script-file-path>
```

## Image Citation
 - **We own all the images used in this assignment.**