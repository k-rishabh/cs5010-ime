import org.junit.Before;
import org.junit.Test;

import controller.ImageHandler;
import model.Image;

import static org.junit.Assert.assertEquals;

/**
 * This class contains JUnit tests for the Model.
 * The tests verify the correctness of image transformations such as blurring,
 * color component extraction, flipping, and brightening.
 * The images are loaded  in the setup phase and used
 * for testing transformation methods to ensure expected results.
 * Each test case asserts that the transformed image matches the expected output
 * by comparing pixel values.
 */
public class ImageTest {
  ImageHandler images;

  /**
   * Compares two images pixel by pixel to check if they are equal.
   *
   * @param expected the expected Image object.
   * @param actual   the actual Image object to compare with.
   */
  private void assertImageEquals(Image expected, Image actual) {
    for (int y = 0; y < expected.getHeight(); y++) {
      for (int x = 0; x < expected.getWidth(); x++) {
        int expectedPixel = expected.getPackedPixel(y, x);
        int actualPixel = actual.getPackedPixel(y, x);

        assertEquals(expectedPixel >> 16, actualPixel >> 16);
        assertEquals(expectedPixel >> 8, actualPixel >> 8);
        assertEquals(expectedPixel, actualPixel);
      }
    }
  }

  @Before
  public void setUp() {
    images = new ImageHandler();
    images.loadImage("res/original-image.png", "original-image");
    images.loadImage("res/image-blur.png", "blur-image");
    images.loadImage("res/image-blue-component.png", "blue-component-image");
    images.loadImage("res/image-blue-split.png", "blue-split-image");
    images.loadImage("res/image-brighter.png", "brighter-image");
    images.loadImage("res/image-green-component.png", "green-component-image");
    images.loadImage("res/image-green-split.png", "green-split-image");
    images.loadImage("res/image-horizontal-flip.png", "horizontal-flip-image");
    images.loadImage("res/image-intensity.png", "intensity-image");
    images.loadImage("res/image-luma.png", "luma-image");
    images.loadImage("res/image-red-component.png", "red-component-image");
    images.loadImage("res/image-red-split.png", "red-split-image");
    images.loadImage("res/image-sepia.png", "sepia-image");
    images.loadImage("res/image-sharper.png", "sharpen-image");
    images.loadImage("res/image-value.png", "value-image");
    images.loadImage("res/image-vertical-flip.png", "vertical-flip-image");
    images.loadImage("res/image-brighten-gt255.png", "brighten-image-gt255");
    images.loadImage("res/image-darken.png", "darken-image");
    images.loadImage("res/image-darken-gt255.png", "darken-image-gt255");
    images.loadImage("res/image-horizontal-vertical-flip.png",
            "horizontal-vertical-flip-image");
    images.loadImage("res/image-vertical-horizontal-flip.png",
            "vertical-horizontal-flip-image");
    images.loadImage("res/image-vertical-vertical-flip.png",
            "vertical-vertical-flip-image");
    images.loadImage("res/image-horizontal-horizontal-flip.png",
            "horizontal-horizontal-flip-image");
    images.loadImage("res/image-blur-twice.png", "blur-twice");
    images.loadImage("res/image-sharpen-twice.png", "sharpen-twice");
    images.loadImage("res/image-red-component-blur.png", "red-component-blur");
    images.loadImage("res/image-red-split-sharpen.png", "red-split-sharpen");

  }

  @Test
  public void testLoadImage() {
    images.loadImage("res/original-image.png", "test-image");
    Image actualImage = images.getImage("test-image");
    Image expectedImage = images.getImage("original-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testBlurImage() {
    Image expectedImage = images.getImage("blur-image");
    images.blur("original-image", "test-blur-image");

    Image actualImage = images.getImage("test-blur-image");
    assertImageEquals(expectedImage, expectedImage);
  }

  @Test
  public void testBlueComponentImage() {
    Image expectedImage = images.getImage("blue-component-image");
    images.blueComponent("original-image", "test-blue-component-image");
    Image actualImage = images.getImage("test-blue-component-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testGreenComponentImage() {
    Image expectedImage = images.getImage("green-component-image");
    images.greenComponent("original-image", "test-green-component-image");
    Image actualImage = images.getImage("test-green-component-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testHorizontalFlipImage() {
    Image expectedImage = images.getImage("horizontal-flip-image");
    images.horizontalFlip("original-image", "test-horizontal-flip-image");
    Image actualImage = images.getImage("test-horizontal-flip-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testIntensityImage() {
    Image expectedImage = images.getImage("intensity-image");
    images.intensityComponent("original-image", "test-intensity-image");
    Image actualImage = images.getImage("test-intensity-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testLumaImage() {
    Image expectedImage = images.getImage("luma-image");
    images.lumaComponent("original-image", "test-luma-image");
    Image actualImage = images.getImage("test-luma-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testRedComponentImage() {
    Image expectedImage = images.getImage("red-component-image");
    images.redComponent("original-image", "test-red-component-image");
    Image actualImage = images.getImage("test-red-component-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testSepiaImage() {
    Image expectedImage = images.getImage("sepia-image");
    images.sepia("original-image", "test-sepia-image");
    Image actualImage = images.getImage("test-sepia-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testSharpenImage() {
    Image expectedImage = images.getImage("sharpen-image");
    images.sharpen("original-image", "test-sharpen-image");
    Image actualImage = images.getImage("test-sharpen-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testValueImage() {
    Image expectedImage = images.getImage("value-image");
    images.valueComponent("original-image", "test-value-image");
    Image actualImage = images.getImage("test-value-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testVerticalFlipImage() {
    Image expectedImage = images.getImage("vertical-flip-image");
    images.verticalFlip("original-image", "test-vertical-flip-image");
    Image actualImage = images.getImage("test-vertical-flip-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testRGBSplit() {
    Image expectedImageRed = images.getImage("red-split-image");
    Image expectedImageGreen = images.getImage("green-split-image");
    Image expectedImageBlue = images.getImage("blue-split-image");
    images.rgbSplit("original-image", "test-red-rgb-split",
            "test-green-rgb-split", "test-blue-rgb-split");
    Image actualImageRed = images.getImage("test-red-rgb-split");
    Image actualImageGreen = images.getImage("test-green-rgb-split");
    Image actualImageBlue = images.getImage("test-blue-rgb-split");
    assertImageEquals(expectedImageRed, actualImageRed);
    assertImageEquals(expectedImageGreen, actualImageGreen);
    assertImageEquals(expectedImageBlue, actualImageBlue);
  }

  @Test
  public void testBrightenValueGreaterThan255() {
    Image expectedImage = images.getImage("brighten-image-gt255");
    images.brighten(260, "original-image", "test-brighten-image-gt255");
    Image actualImage = images.getImage("test-brighten-image-gt255");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testDarkenImage() {
    Image expectedImage = images.getImage("darken-image");
    images.brighten(-50, "original-image", "test-darken-image");
    Image actualImage = images.getImage("test-darken-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testDarkenValueGreaterThan255() {
    Image expectedImage = images.getImage("darken-image-gt255");
    images.brighten(-260, "original-image", "test-darken-image-gt255");
    Image actualImage = images.getImage("test-darken-image-gt255");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testHorizontalVerticalFlipImage() {
    Image expectedImage = images.getImage("horizontal-vertical-flip-image");
    images.horizontalFlip("original-image", "test-horizontal-flip-image");
    images.verticalFlip("test-horizontal-flip-image",
            "test-horizontal-vertical-flip-image");
    Image actualImage = images.getImage("test-horizontal-vertical-flip-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testVerticalHorizontalFlipImage() {
    Image expectedImage = images.getImage("vertical-horizontal-flip-image");
    images.verticalFlip("original-image", "test-vertical-flip-image");
    images.horizontalFlip("test-vertical-flip-image",
            "test-vertical-horizontal-flip-image");
    Image actualImage = images.getImage("test-vertical-horizontal-flip-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testVerticalVerticalFlipImage() {
    Image expectedImage = images.getImage("vertical-vertical-flip-image");
    images.verticalFlip("original-image", "test-vertical-flip-image");
    images.verticalFlip("test-vertical-flip-image",
            "test-vertical-vertical-flip-image");
    Image actualImage = images.getImage("test-vertical-vertical-flip-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testHorizontalHorizontalFlipImage() {
    Image expectedImage = images.getImage("horizontal-horizontal-flip-image");
    images.horizontalFlip("original-image", "test-horizontal-flip-image");
    images.horizontalFlip("test-horizontal-flip-image",
            "test-horizontal-horizontal-flip-image");
    Image actualImage = images.getImage("test-horizontal-horizontal-flip-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testBlurTwice() {
    Image actualImage = images.getImage("blur-twice");
    images.blur("original-image", "test-blur-image");
    images.blur("test-blur-image", "test-blur-twice-image");
    Image expectedImage = images.getImage("test-blur-twice-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testSharpenTwice() {
    Image actualImage = images.getImage("sharpen-twice");
    images.sharpen("original-image", "test-sharpen-image");
    images.sharpen("test-sharpen-image", "test-sharpen-twice-image");
    Image expectedImage = images.getImage("test-sharpen-twice-image");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testOperationOnComponent() {
    Image actualImage = images.getImage("red-component-blur");
    images.redComponent("original-image", "test-red-component");
    images.blur("test-red-component", "test-red-component-blur");
    Image expectedImage = images.getImage("test-red-component-blur");
    assertImageEquals(expectedImage, actualImage);
  }

  @Test
  public void testOperationOnSplitComponent() {
    Image actualImage = images.getImage("red-split-sharpen");
    images.sharpen("red-split-image", "test-red-split-sharpen");
    Image expectedImage = images.getImage("test-red-split-sharpen");
    assertImageEquals(expectedImage, actualImage);
  }
}
