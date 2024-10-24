import org.junit.Before;
import org.junit.Test;

import controller.ImageHandler;
import model.Image;

import static org.junit.Assert.assertEquals;

public class ImageTest {
  ImageHandler images;

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
    images.loadImage("/Users/hariharasudan/CS5010/group/cs5010-group-assignments/assn4/images/manhattan-small.png", "original-image");
    images.loadImage("/Users/hariharasudan/CS5010/group/cs5010-group-assignments/assn4/res/landscape-blur.png", "blur-image");
  }

  @Test
  public void testLoadImage() {
    images.loadImage("/Users/hariharasudan/CS5010/group/cs5010-group-assignments/assn4/images/manhattan-small.png", "test-image");
    Image i1 = images.getImage("test-image");
    Image i2 = images.getImage("original-image");
    assertImageEquals(i1, i2);
  }

  @Test
  public void testLoadImage2() {

  }
}
