import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

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
  private ImageModel mock;
  private ImageController controller;

  private StringBuilder log;
  private Reader in;
  private Appendable out;

  @Before
  public void setup() {
    mockMap = new ImageMapModel();

    log = new StringBuilder();
    mock = new MockImage(log);

    mockMap.put("mock", mock);
  }

  @Test
  public void testScriptParse() {

  }

  @Test
  public void testLoad() {

  }

  @Test
  public void testSave() {

  }

  @Test
  public void testComments() {

  }

  @Test
  public void testUnknownCommand() {
    in = new StringReader("darken mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.ComponentRed.",
            log.toString().trim());
  }

  @Test
  public void testFilterCompValid() {
    in = new StringReader("red-component mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyColorFilter with filter controller.filter.CompRedFilter.",
            log.toString().trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterCompInvalid() {
    in = new StringReader("red-component mock mock-res 20");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);
    controller.execute();
  }

  @Test
  public void downscale() {
    in = new StringReader("downscale 300 200 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);
    controller.execute();

    assertEquals("In downscale with height 300 and width 200.",
            log.toString().trim());

  }

  @Test
  public void testValCompValid() {
    in = new StringReader("value-component mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In valueComponent.",
            log.toString().trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValCompInvalid() {
    in = new StringReader("value-component mock mock-res 20");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
  }

  @Test
  public void testFlipValid() {
    in = new StringReader("vertical-flip mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In verticalFlip.",
            log.toString().trim());
  }

  @Test
  public void testFlipInvalid() {
    in = new StringReader("vertical-flip mock mock-res split 50");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);
    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.",
            log.toString().trim());
  }

  @Test
  public void testBrightenValid() {
    in = new StringReader("brighten 50 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In brighten with value 50.",
            log.toString().trim());
  }

  @Test
  public void testBrightenOutOfBounds() {
    in = new StringReader("brighten 256 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In brighten with value 256.",
            log.toString().trim());
  }

  @Test
  public void testDarkenValid() {
    in = new StringReader("brighten -50 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In brighten with value -50.",
            log.toString().trim());
  }

  @Test
  public void testDarkenOutOfBounds() {
    in = new StringReader("brighten -500 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In brighten with value -500.",
            log.toString().trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenTooFewArgs() {
    in = new StringReader("brighten mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
  }

  @Test
  public void testBrightenInvalid() {
    in = new StringReader("brighten 50 mock mock-res split 20");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.",
            log.toString().trim());
  }

  @Test(expected = NullPointerException.class)
  public void testRGBSplitValid() {
    in = new StringReader("rgb-split mock mock-red mock-green mock-blue");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.",
            log.toString().trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRGBSplitTooLessArgs() {
    in = new StringReader("rgb-split mock mock-rgb");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
  }

  @Test
  public void testRGBCombineValid() {
    in = new StringReader("rgb-combine mock mock-red mock-green mock-blue");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRGBCombineTooLessArgs() {
    in = new StringReader("rgb-combine mock mock-rgb");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
  }

  @Test
  public void testBlurValid() {
    in = new StringReader("blur mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.",
            log.toString().trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurTooManyArgs() {
    in = new StringReader("blur 10 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
  }

  @Test
  public void testSharpenValid() {
    in = new StringReader("sharpen mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.SharpenFilter.",
            log.toString().trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenTooManyArgs() {
    in = new StringReader("sharpen 10 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.",
            log.toString().trim());
  }

  @Test
  public void testSepiaValid() {
    in = new StringReader("sepia mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyColorFilter with filter controller.filter.SepiaFilter.",
            log.toString().trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaTooManyArgs() {
    in = new StringReader("sepia 10 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.",
            log.toString().trim());
  }

  @Test
  public void testRunValid() {
  }

  @Test
  public void testRunInvalid() {
  }

  @Test
  public void testCompressValid() {
    in = new StringReader("compress 40 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In compress with ratio 40.",
            log.toString().trim());
  }

  @Test
  public void testCompressOutOfBounds() {
    in = new StringReader("compress 120 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In compress with ratio 120.",
            log.toString().trim());
  }

  @Test
  public void testCompressInvalid() {
    in = new StringReader("compress 40 mock mock-res split");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.",
            log.toString().trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressTooFewArgs() {
    in = new StringReader("compress mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();

  }

  @Test
  public void testHistogramValid() {
    in = new StringReader("histogram mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In histogram.",
            log.toString().trim());
  }

  @Test
  public void testHistogramInvalid() {
    in = new StringReader("histogram mock mock-res split 20");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.",
            log.toString().trim());
  }

  @Test
  public void testColorCorrectValid() {
    in = new StringReader("color-correct mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In colorCorrect.",
            log.toString().trim());
  }

  @Test(expected = NullPointerException.class)
  public void testColorCorrectInvalid() {
    in = new StringReader("color-correct mock mock-res split 20");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
  }

  @Test
  public void testLevelsAdjustValid() {
    in = new StringReader("levels-adjust 10 20 30 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In levelsAdjust with black 10, mid 20, white 30.",
            log.toString().trim());
  }

  @Test
  public void testLevelsAdjustOutOfBounds1() {
    in = new StringReader("levels-adjust 10 20 300 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In levelsAdjust with black 10, mid 20, white 300.",
            log.toString().trim());
  }

  @Test
  public void testLevelsAdjustOutOfBounds2() {
    in = new StringReader("levels-adjust -10 20 30 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In levelsAdjust with black -10, mid 20, white 30.",
            log.toString().trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustTooFewArgs() {
    in = new StringReader("levels-adjust 50 100 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.",
            log.toString().trim());
  }

  @Test
  public void testLevelsAdjustInvalidOrder() {
    in = new StringReader("levels-adjust 10 30 20 mock mock-res");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.",
            log.toString().trim());
  }

  @Test(expected = NullPointerException.class)
  public void testLevelsAdjustInvalid() {
    in = new StringReader("levels-adjust 10 20 30 mock mock-res split 20");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSplitViewValid() {
    in = new StringReader("rgb-split mock mock-rgb");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();

  }

  @Test
  public void testSplitViewOutOfBounds1() {
    in = new StringReader("blur mock mock-res split 120");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.",
            log.toString().trim());
  }

  @Test
  public void testSplitViewOutOfBounds2() {
    in = new StringReader("blur mock mock-res split -20");
    out = new StringWriter();
    controller = new ScriptController(mockMap, in, out);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.BlurFilter.",
            log.toString().trim());
  }
}