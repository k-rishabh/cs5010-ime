import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import controller.GUIController;
import controller.ImageController;
import model.ImageMap;
import model.ImageMapModel;
import model.ImageModel;
import model.MockImage;
import view.ImageView;
import view.MockGUI;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the GUI (view) controller. Tests the functionality of all public functions
 * present in features. Uses a mock view and model.
 */
public class GUIControllerTest {
  private ImageMap mockMap;
  private ImageController controller;
  private ImageView mockView;

  private Appendable out;
  private Reader in;

  @Before
  public void setup() {
    mockMap = new ImageMapModel();
    out = new StringBuilder();

    ImageModel mockModel = new MockImage(out);
    mockMap.put("0", mockModel);
  }

  // grayscale
  @Test
  public void testRedComponent() {
    in = new StringReader("red-component 0");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" + "Listening for input from user...\n" +
                    "In applyColorFilter with filter controller.filter.CompRedFilter.\n" +
                    "In histogram.\n" + "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  @Test
  public void testGreenComponent() {
    in = new StringReader("green-component 0");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" + "Listening for input from user...\n" +
                    "In applyColorFilter with filter controller.filter.CompGreenFilter.\n" +
                    "In histogram.\n" + "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  @Test
  public void testBlueComponent() {
    in = new StringReader("blue-component 0");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" + "Listening for input from user...\n" +
                    "In applyColorFilter with filter controller.filter.CompBlueFilter.\n" +
                    "In histogram.\n" + "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  @Test
  public void testLumaComponent() {
    in = new StringReader("luma-component 0");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" + "Listening for input from user...\n" +
                    "In applyColorFilter with filter controller.filter.LumaFilter.\n" +
                    "In histogram.\n" + "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  @Test
  public void testIntensityComponent() {
    in = new StringReader("intensity-component 0");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" + "Listening for input from user...\n" +
                    "In applyColorFilter with filter controller.filter.IntensityFilter.\n" +
                    "In histogram.\n" + "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  @Test
  public void testValueComponent() {
    in = new StringReader("value-component 0");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" +
                    "Listening for input from user...\n" +
                    "In valueComponent.\n" +
                    "In histogram.\n" +
                    "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  // other split preview operations
  @Test
  public void testBlur() {
    in = new StringReader("blur 0");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" + "Listening for input from user...\n" +
                    "In applyImageFilter with filter controller.filter.BlurFilter.\n" +
                    "In histogram.\n" + "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  @Test
  public void testSharpen() {
    in = new StringReader("sharpen 0");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" +
                    "Listening for input from user...\n" +
                    "In applyImageFilter with filter controller.filter.SharpenFilter.\n" +
                    "In histogram.\n" +
                    "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  @Test
  public void testSepia() {
    in = new StringReader("sepia 0");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" + "Listening for input from user...\n" +
                    "In applyColorFilter with filter controller.filter.SepiaFilter.\n" +
                    "In histogram.\n" + "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  @Test
  public void testColorCorrect() {
    in = new StringReader("color-correct 0");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" +
                    "Listening for input from user...\n" +
                    "In colorCorrect.\n" +
                    "In histogram.\n" +
                    "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  @Test
  public void testLevelsAdjust() {
    in = new StringReader("levels-adjust 10 20 30 0");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" +
                    "Listening for input from user...\n" +
                    "In levelsAdjust with black 10, mid 20, white 30.\n" +
                    "In histogram.\n" +
                    "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  @Test
  public void testLevelsAdjustInvalid() {
    in = new StringReader("levels-adjust 30 20 10 0");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" +
                    "Listening for input from user...\n" +
                    "Displaying type 0 message: " +
                    "The value of black, mid, and white must be in ascending order!",
            out.toString().trim());
  }

  @Test
  public void testPreviewMode() {
    in = new StringReader("blur 40");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" +
                    "Listening for input from user...\n" +
                    "In splitImage with ratio 40.\n" +
                    "In applyImageFilter with filter controller.filter.BlurFilter.\n" +
                    "In mergeSplits.\n" +
                    "Confirming split view operation in a new window.\n" +
                    "In histogram.\n" +
                    "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  // non-split operations
  @Test
  public void testBrighten() {
    in = new StringReader("brighten 50");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" +
                    "Listening for input from user...\n" +
                    "In brighten with value 50.\n" +
                    "In histogram.\n" +
                    "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  @Test
  public void testHorizontalFlip() {
    in = new StringReader("horizontal-flip");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" +
                    "Listening for input from user...\n" +
                    "In horizontalFlip.\n" +
                    "In histogram.\n" +
                    "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  @Test
  public void testVerticalFlip() {
    in = new StringReader("vertical-flip");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" +
                    "Listening for input from user...\n" +
                    "In verticalFlip.\n" +
                    "In histogram.\n" +
                    "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  @Test
  public void testCompress() {
    in = new StringReader("compress 50");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" +
                    "Listening for input from user...\n" +
                    "In compress with ratio 50.\n" +
                    "In histogram.\n" +
                    "Refreshing screen with new image and histogram.",
            out.toString().trim());
  }

  @Test
  public void testDownscaleInvalid() {
    in = new StringReader("downscale a b");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" +
                    "Listening for input from user...\n" +
                    "Displaying type 0 message: Entered height/width are not numbers!",
            out.toString().trim());
  }

  @Test
  public void testLoadWithoutSave() {
    in = new StringReader("blur 0\n" + "load res/ex-image.jpg mock");
    mockView = new MockGUI(in, out);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("Setting all inputs to false.\n" +
                    "Listening for input from user...\n" +
                    "In applyImageFilter with filter controller.filter.BlurFilter.\n" +
                    "In histogram.\n" +
                    "Refreshing screen with new image and histogram.\n" +
                    "Asking for confirmation with message: Image not saved! " +
                    "Are you sure you want to load new?\n" +
                    "Refreshing screen with new image and histogram.\n" +
                    "Setting all inputs to true.\n" +
                    "Displaying type 1 message: Successfully loaded the image!",
            out.toString().trim());
  }
}
