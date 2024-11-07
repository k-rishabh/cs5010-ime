import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class ModelTest {
  /**
   * Compares two images pixel by pixel to check if they are equal.
   *
   * @param expected the expected Image object.
   * @param actual   the actual Image object to compare with.
   */
  private void assertImageEquals(ImageV1 expected, ImageV1 actual) {
    for (int i = 0; i < expected.getHeight(); i++) {
      for (int j = 0; j < expected.getWidth(); j++) {
        assertEquals(expected.getPackedPixel(i, j), actual.getPackedPixel(i, j));
      }
    }
  }

  @Before
  public void setup() {

  }
}
