# How to Use IME?

This file summarizes the syntax of our application. It states all the commands supported by our along with the conditions (such as bounds, order) placed on these commands.

### 1. `load image-path image-name`
This command is responsible for loading an image into our application. It supports the file formats: png, jpg, ppm. An image with the same image-name will be overwritten. <br>
`image-path`: the relative/absolute file path of the image to be loaded.<br>
`image-name`: a variable name that will be used to refer to this image. 

### 2. `save image-path image-name`
This command is responsible for saving an image from the application into the file system. It supports the file formats: png, jpg, ppm. <br>
`image-path`: the relative/absolute file path where the image must be saved. <br>
`image-name`: the variable name of the image in our application, that is to be saved.

### 3. `red-component image-name dest-image-name`
