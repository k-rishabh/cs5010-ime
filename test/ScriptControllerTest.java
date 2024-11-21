import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import controller.ScriptController;
import controller.ImageController;
import model.ImageMap;
import model.ImageMapModel;
import model.ImageModel;
import model.MockImage;

import static org.junit.Assert.assertEquals;

/**
 * A test class for the controller package. Uses mock models for testing purposes.
 */
public class ScriptControllerTest {
  private ImageMap mockMap;
  private ImageController controller;

  private Reader in;
  private Appendable out;

  @Before
  public void setup() {
    mockMap = new ImageMapModel();

    out = new StringBuilder();
    ImageModel mock = new MockImage(out);

    mockMap.put("mock", mock);
  }

  // TODO
  @Test
  public void testScriptParse() {

  }

  @Test
  public void testComments() {
    in = new StringReader("# this is a comment");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("", out.toString().trim());
  }

  @Test
  public void testUnknownCommand() {
    in = new StringReader("darken mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("ERROR: Unknown command \"darken\" on line 1.", out.toString().trim());
  }

  @Test
  public void testFilterCompValid() {
    in = new StringReader("red-component mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyColorFilter with filter controller.filter.CompRedFilter.\n" +
            "red-component performed successfully!", out.toString().trim());
  }

  @Test
  public void testFilterCompInvalid() {
    in = new StringReader("red-component mock mock-res 20");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("Error while performing red-component on line 1!", out.toString().trim());
  }

  @Test
  public void downscaleInvalid() {
    in = new StringReader("downscale 300 200 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("Error while performing downscale on line 1!",
            out.toString().trim());

  }

  @Test
  public void testValCompValid() {
    in = new StringReader("value-component mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In valueComponent.\n" +
            "value-component performed successfully!", out.toString().trim());
  }

  @Test
  public void testValCompInvalid() {
    in = new StringReader("value-component mock mock-res 20");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("Error while performing value-component on line 1!",
            out.toString().trim());
  }

  @Test
  public void testFlipValid() {
    in = new StringReader("vertical-flip mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In verticalFlip.\n" +
            "vertical-flip performed successfully!", out.toString().trim());
  }

  @Test
  public void testFlipInvalid() {
    in = new StringReader("vertical-flip mock mock-res split 50");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("ERROR: Command \"vertical-flip\" on line 1 cannot be previewed!",
            out.toString().trim());
  }

  @Test
  public void testBrightenValid() {
    in = new StringReader("brighten 50 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In brighten with value 50.\n" +
            "brighten performed successfully!", out.toString().trim());
  }

  @Test
  public void testBrightenOutOfBounds() {
    in = new StringReader("brighten 256 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In brighten with value 256.\n" +
            "brighten performed successfully!", out.toString().trim());
  }

  @Test
  public void testDarkenValid() {
    in = new StringReader("brighten -50 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In brighten with value -50.\n" +
            "brighten performed successfully!", out.toString().trim());
  }

  @Test
  public void testDarkenOutOfBounds() {
    in = new StringReader("brighten -500 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In brighten with value -500.\n" +
            "brighten performed successfully!", out.toString().trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenTooFewArgs() {
    in = new StringReader("brighten mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
  }

  @Test
  public void testBrightenInvalid() {
    in = new StringReader("brighten 50 mock mock-res split 20");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("ERROR: Command \"brighten\" on line 1 cannot be previewed!",
            out.toString().trim());
  }

  @Test
  public void testRGBSplitValid() {
    in = new StringReader("rgb-split mock mock-red mock-green mock-blue");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In splitComponents.\n" +
            "rgb-split performed successfully!", out.toString().trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRGBSplitTooLessArgs() {
    in = new StringReader("rgb-split mock mock-rgb");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
  }

  @Test
  public void testRGBCombineValid() {
    in = new StringReader("rgb-combine mock-res mock mock mock");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In combineComponents.\n" + "In combineComponents.\n" +
            "rgb-combine performed successfully!", out.toString().trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRGBCombineTooLessArgs() {
    in = new StringReader("rgb-combine mock mock-rgb");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
  }

  @Test
  public void testBlurValid() {
    in = new StringReader("blur mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.\n" +
            "blur performed successfully!", out.toString().trim());
  }

  @Test
  public void testBlurTooManyArgs() {
    in = new StringReader("blur 10 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("Error while performing blur on line 1!", out.toString().trim());
  }

  @Test
  public void testSharpenValid() {
    in = new StringReader("sharpen mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyColorFilter with filter controller.filter.SharpenFilter.\n" +
            "sharpen performed successfully!", out.toString().trim());
  }

  @Test
  public void testSharpenTooManyArgs() {
    in = new StringReader("sharpen 10 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("Error while performing sharpen on line 1!",
            out.toString().trim());
  }

  @Test
  public void testSepiaValid() {
    in = new StringReader("sepia mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyColorFilter with filter controller.filter.SepiaFilter.\n" +
            "sepia performed successfully!", out.toString().trim());
  }

  @Test
  public void testSepiaTooManyArgs() {
    in = new StringReader("sepia 10 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("Error while performing sepia on line 1!",
            out.toString().trim());
  }

  @Test
  public void testCompressValid() {
    in = new StringReader("compress 40 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In compress with ratio 40.\n" +
            "compress performed successfully!", out.toString().trim());
  }

  @Test
  public void testCompressOutOfBounds() {
    in = new StringReader("compress 120 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In compress with ratio 120.\n" +
            "compress performed successfully!", out.toString().trim());
  }

  @Test
  public void testCompressInvalid() {
    in = new StringReader("compress 40 mock mock-res split");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("ERROR: Command \"compress\" on line 1 cannot be previewed!",
            out.toString().trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressTooFewArgs() {
    in = new StringReader("compress mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
  }

  @Test
  public void testHistogramValid() {
    in = new StringReader("histogram mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In histogram.\n" + "histogram performed successfully!",
            out.toString().trim());
  }

  @Test
  public void testHistogramInvalid() {
    in = new StringReader("histogram mock mock-res split 20");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("ERROR: Command \"histogram\" on line 1 cannot be previewed!",
            out.toString().trim());
  }

  @Test
  public void testColorCorrectValid() {
    in = new StringReader("color-correct mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In colorCorrect.\n" + "color-correct performed successfully!",
            out.toString().trim());
  }

  @Test
  public void testLevelsAdjustValid() {
    in = new StringReader("levels-adjust 10 20 30 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In levelsAdjust with black 10, mid 20, white 30.\n" +
            "levels-adjust performed successfully!", out.toString().trim());
  }

  @Test
  public void testLevelsAdjustOutOfBounds1() {
    in = new StringReader("levels-adjust 10 20 300 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In levelsAdjust with black 10, mid 20, white 300.\n" +
            "levels-adjust performed successfully!", out.toString().trim());
  }

  @Test
  public void testLevelsAdjustOutOfBounds2() {
    in = new StringReader("levels-adjust -10 20 30 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In levelsAdjust with black -10, mid 20, white 30.\n" +
            "levels-adjust performed successfully!", out.toString().trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustTooFewArgs() {
    in = new StringReader("levels-adjust 50 100 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
  }

  @Test
  public void testLevelsAdjustInvalidOrder() {
    in = new StringReader("levels-adjust 10 30 20 mock mock-res");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("Error while performing levels-adjust on line 1!",
            out.toString().trim());
  }

  @Test
  public void testSplitViewValid() {
    in = new StringReader("blur mock mock-res split 20");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In splitImage with ratio 20.\n" +
            "In applyImageFilter with filter controller.filter.BlurFilter.\n" +
            "In mergeSplits.\n" + "blur performed successfully!", out.toString().trim());
  }

  @Test
  public void testSplitViewOutOfBounds1() {
    in = new StringReader("blur mock mock-res split 120");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.\n" +
            "blur performed successfully!", out.toString().trim());
  }

  @Test
  public void testSplitViewOutOfBounds2() {
    in = new StringReader("blur mock mock-res split -20");
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.\n" +
            "blur performed successfully!", out.toString().trim());
  }
}