import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import controller.GUIController;
import controller.ImageController;
import controller.ScriptController;
import model.ImageMap;
import model.ImageMapModel;
import model.ImageModel;
import model.MockImage;
import view.ImageView;
import view.MockGUI;

import static org.junit.Assert.assertEquals;

public class GUIControllerTest {
  private ImageMap mockMap;
  private ImageModel mockModel;
  private ImageController controller;
  private ImageView mockView;

  private StringBuilder log;
  private Reader in;

  @Before
  public void setup() {
    mockMap = new ImageMapModel();
    log = new StringBuilder();

    mockModel = new MockImage(log);
    mockMap.put("0", mockModel);
  }

  @Test
  public void testRedComponent() {
    in = new StringReader("red-component");
    mockView = new MockGUI(in, log);
    controller = new GUIController(mockMap, mockView);

    controller.execute();
    assertEquals("In applyImageFilter with filter controller.filter.ComponentRed.",
            log.toString().trim());
  }

}
