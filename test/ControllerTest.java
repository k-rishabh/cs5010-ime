import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import controller.ScriptController;
import controller.ImageController;
import model.ImageMap;
import model.MockImage;

import static org.junit.Assert.assertEquals;


/**
 * A test class for the controller package. Uses mock models for testing purposes.
 */
public class ControllerTest {
  private ImageController controller;
  private Reader in;
  private Appendable out;
  private ImageMap mock;
  private StringBuilder log;
  @Before
  public void setUp(){
    log = new StringBuilder();
    mock = new MockImage(log);
  }

  @Test
  public void testSepiaValidCommand(){

    in = new StringReader("sepia test-image test-image-sepia");
    out = new StringWriter();

    controller = new ScriptController(in, out);
    try {
      controller.execute(mock);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals("Command Arguments: test-image test-image-sepia", log.toString().trim());
  }

  @Test
  public void testBlurValidCommand(){

    in = new StringReader("blur test-image test-image-blur");
    out = new StringWriter();

    controller = new ScriptController(in, out);
    try {
      controller.execute(mock);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals("Command Arguments: test-image test-image-blur", log.toString().trim());
  }


}