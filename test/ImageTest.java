import org.junit.Before;
import org.junit.Test;


import controller.filter.BlurFilter;
import controller.filter.CompBlueFilter;
import controller.filter.CompGreenFilter;
import controller.filter.CompRedFilter;
import controller.filter.IntensityFilter;
import controller.filter.LumaFilter;
import controller.filter.SepiaFilter;
import controller.filter.SharpenFilter;
import model.ImageModel;
import model.RGBImage;
import model.pixel.Pixel;
import model.pixel.RGBPixel;

import static org.junit.Assert.assertEquals;

/**
 * This class contains JUnit tests for the Model.
 * The tests verify the correctness of image transformations such as blurring,
 * color component extraction, flipping, brightening, color-correctness, compression, level adjust
 * and histogram generation.
 */
public class ImageTest {
  ImageModel testImage;
  int[][][] imageArray;

  /**
   * Compares two images pixel by pixel to check if they are equal.
   *
   * @param expected the expected Image object.
   * @param actual   the actual Image object to compare with.
   */
  private void assertImageEquals(int[][][] expected, ImageModel actual) {
    for (int y = 0; y < actual.getHeight(); y++) {
      for (int x = 0; x < actual.getWidth(); x++) {
        int expectedPixelR = expected[y][x][0];
        int expectedPixelG = expected[y][x][1];
        int expectedPixelB = expected[y][x][2];

        int actualPixelR = actual.getPixel(y, x).getRed();
        int actualPixelG = actual.getPixel(y, x).getGreen();
        int actualPixelB = actual.getPixel(y, x).getBlue();
//        System.out.println(actualPixelR + " " + actualPixelG + " " + actualPixelB);
        assertEquals(expectedPixelR, actualPixelR);
        assertEquals(expectedPixelG, actualPixelG);
        assertEquals(expectedPixelB, actualPixelB);

      }
    }
  }

  @Before
  public void setUp() {
    Pixel[][] pixel_arr = new Pixel[5][7];
    this.imageArray = new int[][][]{
            {{34, 125, 244}, {67, 210, 183}, {94, 120, 45}, {88, 200, 56}, {134, 249, 37},
                    {192, 165, 78}, {112, 255, 23}},
            {{90, 176, 230}, {14, 5, 150}, {89, 205, 60}, {142, 199, 250}, {33, 121, 23},
                    {45, 88, 130}, {72, 155, 244}},
            {{243, 55, 162}, {99, 201, 39}, {118, 40, 109}, {20, 235, 93}, {129, 204, 180},
                    {78, 49, 100}, {12, 200, 244}},
            {{244, 25, 99}, {38, 56, 34}, {150, 200, 13}, {202, 40, 78}, {101, 150, 237},
                    {73, 119, 111}, {244, 78, 99}},
            {{128, 90, 115}, {145, 170, 199}, {17, 199, 23}, {221, 100, 34}, {72, 55, 139},
                    {200, 233, 187}, {149, 5, 49}},
    };
    for (int i = 0; i < imageArray.length; i++) {
      for (int j = 0; j < imageArray[0].length; j++) {
        RGBPixel pixel = new RGBPixel(imageArray[i][j][0], imageArray[i][j][1],
                imageArray[i][j][2]);
        pixel_arr[i][j] = pixel;
      }
    }
    this.testImage = new RGBImage(pixel_arr);
  }


  @Test
  public void testBlurImage() {
    ImageModel actualImage = testImage.deepCopy();
    BlurFilter blur_filter = new BlurFilter();
    actualImage.applyImageFilter(blur_filter);

    assertImageEquals(new int[][][]{
            {{29, 79, 122}, {45, 107, 118}, {63, 119, 73}, {75, 141, 60}, {84, 140, 52},
                    {90, 132, 59}, {63, 109, 54}},
            {{69, 92, 140}, {77, 121, 136}, {85, 149, 107}, {93, 183, 114}, {88, 163, 100},
                    {82, 140, 118}, {56, 120, 121}},
            {{118, 67, 98}, {112, 107, 91}, {99, 133, 84}, {102, 161, 121}, {90, 148, 137},
                    {80, 120, 145}, {59, 98, 131}},
            {{127, 54, 78}, {120, 112, 77}, {114, 136, 56}, {133, 126, 94}, {117, 128, 148},
                    {118, 122, 143}, {107, 77, 93}},
            {{83, 50, 68}, {83, 99, 78}, {83, 114, 43}, {107, 83, 54}, {100, 84, 103},
                    {108, 94, 105}, {97, 47, 54}}
    }, actualImage);
  }

  @Test
  public void testBlueComponentImage() {
    ImageModel actualImage = testImage.deepCopy();
    CompBlueFilter blue_filter = new CompBlueFilter();
    actualImage.applyImageFilter(blue_filter);
    assertImageEquals(new int[][][]{
            {{81, 215, 255}, {183, 255, 105}, {230, 255, 255}, {167, 255, 60}, {237, 253, 208},
                    {184, 255, 255}, {0, 0, 0}},
            {{180, 255, 255}, {255, 255, 214}, {250, 255, 255}, {255, 255, 240}, {255, 255, 255},
                    {196, 255, 255}, {0, 0, 0}},
            {{151, 255, 223}, {255, 255, 182}, {255, 255, 255}, {255, 255, 255}, {196, 255, 255},
                    {255, 255, 255}, {0, 0, 0}},
            {{255, 255, 255}, {255, 255, 145}, {255, 255, 205}, {255, 255, 255}, {255, 255, 255},
                    {255, 255, 255}, {0, 0, 0}},
            {{183, 226, 233}, {167, 255, 36}, {255, 140, 112}, {173, 205, 255}, {255, 255, 255},
                    {255, 83, 148}, {0, 0, 0}}
    }, actualImage);
  }

  @Test
  public void testGreenComponentImage() {
    ImageModel actualImage = testImage.deepCopy();
    CompGreenFilter green_filter = new CompGreenFilter();
    actualImage.applyImageFilter(green_filter);
    assertImageEquals(new int[][][]{
                    {{124, 255, 255}, {81, 215, 255}, {183, 255, 105}, {230, 255, 255},
                            {167, 255, 60}, {237, 253, 208}, {184, 255, 255}},
                    {{255, 255, 255}, {180, 255, 255}, {255, 255, 214}, {250, 255, 255},
                            {255, 255, 240}, {255, 255, 255}, {196, 255, 255}},
                    {{255, 255, 255}, {151, 255, 223}, {255, 255, 182}, {255, 255, 255},
                            {255, 255, 255}, {196, 255, 255}, {255, 255, 255}},
                    {{255, 170, 255}, {255, 255, 255}, {255, 255, 145}, {255, 255, 205},
                            {255, 255, 255}, {255, 255, 255}, {255, 255, 255}},
                    {{255, 115, 214}, {183, 226, 233}, {167, 255, 36}, {255, 140, 112},
                            {173, 205, 255}, {255, 255, 255}, {255, 83, 148}}}
            , actualImage);
  }

  @Test
  public void testHorizontalFlipImage() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.horizontalFlip();
    assertImageEquals(new int[][][]{
            {{112, 255, 23}, {192, 165, 78}, {134, 249, 37}, {88, 200, 56}, {94, 120, 45},
                    {67, 210, 183}, {34, 125, 244}},
            {{72, 155, 244}, {45, 88, 130}, {33, 121, 23}, {142, 199, 250}, {89, 205, 60},
                    {14, 5, 150}, {90, 176, 230}},
            {{12, 200, 244}, {78, 49, 100}, {129, 204, 180}, {20, 235, 93}, {118, 40, 109},
                    {99, 201, 39}, {243, 55, 162}},
            {{244, 78, 99}, {73, 119, 111}, {101, 150, 237}, {202, 40, 78}, {150, 200, 13},
                    {38, 56, 34}, {244, 25, 99}},
            {{149, 5, 49}, {200, 233, 187}, {72, 55, 139}, {221, 100, 34}, {17, 199, 23},
                    {145, 170, 199}, {128, 90, 115}}
    }, actualImage);
  }

  @Test
  public void testIntensityImage() {
    ImageModel actualImage = testImage.deepCopy();
    IntensityFilter intensityFilter = new IntensityFilter();
    actualImage.applyImageFilter(intensityFilter);
    assertImageEquals(new int[][][]{
            {{68, 171, 255}, {129, 255, 255}, {164, 255, 247}, {193, 255, 156}, {211, 255, 191},
                    {195, 255, 178}, {140, 220, 158}},
            {{182, 255, 255}, {255, 255, 255}, {243, 255, 255}, {255, 255, 255}, {255, 255, 255},
                    {255, 255, 255}, {170, 255, 255}},
            {{242, 172, 237}, {255, 255, 255}, {255, 255, 255}, {255, 255, 255}, {255, 255, 255},
                    {255, 255, 255}, {174, 229, 255}},
            {{255, 198, 215}, {255, 255, 255}, {255, 255, 207}, {255, 255, 255}, {255, 255, 255},
                    {255, 255, 255}, {251, 227, 255}},
            {{184, 113, 148}, {240, 246, 160}, {255, 254, 126}, {254, 247, 174}, {255, 232, 255},
                    {255, 213, 255}, {221, 144, 148}}
    }, actualImage);
  }

  @Test
  public void testLumaImage() {
    ImageModel actualImage = testImage.deepCopy();
    LumaFilter lumaFilter = new LumaFilter();
    actualImage.applyColorFilter(lumaFilter);
    assertImageEquals(new int[][][]{
            {{114, 114, 114}, {178, 178, 178}, {109, 109, 109}, {166, 166, 166}, {209, 209, 209},
                    {164, 164, 164}, {208, 208, 208}},
            {{162, 162, 162}, {17, 17, 17}, {170, 170, 170}, {191, 191, 191}, {95, 95, 95},
                    {82, 82, 82}, {144, 144, 144}},
            {{103, 103, 103}, {168, 168, 168}, {62, 62, 62}, {179, 179, 179}, {186, 186, 186},
                    {59, 59, 59}, {163, 163, 163}},
            {{77, 77, 77}, {51, 51, 51}, {176, 176, 176}, {77, 77, 77}, {146, 146, 146},
                    {109, 109, 109}, {115, 115, 115}},
            {{100, 100, 100}, {167, 167, 167}, {148, 148, 148}, {121, 121, 121}, {65, 65, 65},
                    {223, 223, 223}, {39, 39, 39}}
    }, actualImage);
  }

  @Test
  public void testRedComponentImage() {
    ImageModel actualImage = testImage.deepCopy();
    CompRedFilter redFilter = new CompRedFilter();
    actualImage.applyImageFilter(redFilter);
    assertImageEquals(new int[][][]{
            {{0, 0, 0}, {124, 255, 255}, {81, 215, 255}, {183, 255, 105}, {230, 255, 255},
                    {167, 255, 60}, {237, 253, 208}},
            {{0, 0, 0}, {255, 255, 255}, {180, 255, 255}, {255, 255, 214}, {250, 255, 255},
                    {255, 255, 240}, {255, 255, 255}},
            {{0, 0, 0}, {255, 255, 255}, {151, 255, 223}, {255, 255, 182}, {255, 255, 255},
                    {255, 255, 255}, {196, 255, 255}},
            {{0, 0, 0}, {255, 170, 255}, {255, 255, 255}, {255, 255, 145}, {255, 255, 205},
                    {255, 255, 255}, {255, 255, 255}},
            {{0, 0, 0}, {255, 115, 214}, {183, 226, 233}, {167, 255, 36}, {255, 140, 112},
                    {173, 205, 255}, {255, 255, 255}}
    }, actualImage);
  }

  @Test
  public void testSepiaImage() {
    ImageModel actualImage = testImage.deepCopy();
    SepiaFilter sepiaFilter = new SepiaFilter();
    actualImage.applyColorFilter(sepiaFilter);
    assertImageEquals(new int[][][]{
                    {{156, 139, 108}, {222, 198, 154}, {138, 123, 96}, {199, 177, 138},
                            {251, 224, 174}, {217, 193, 151}, {244, 218, 170}},
                    {{214, 191, 149}, {38, 34, 26}, {204, 182, 142}, {255, 228, 178},
                            {110, 98, 77}, {110, 98, 76}, {194, 172, 134}},
                    {{168, 150, 117}, {201, 179, 139}, {98, 87, 68}, {206, 184, 143},
                            {242, 215, 168}, {87, 78, 60}, {205, 182, 142}},
                    {{134, 119, 93}, {64, 57, 45}, {215, 192, 149}, {125, 111, 87},
                            {200, 178, 139}, {141, 126, 98}, {175, 155, 121}},
                    {{141, 126, 98}, {225, 201, 156}, {164, 146, 114}, {170, 151, 118},
                            {97, 86, 67}, {255, 255, 203}, {72, 64, 50}}
            }
            , actualImage);
  }

  @Test
  public void testSharpenImage() {
    ImageModel actualImage = testImage.deepCopy();
    SharpenFilter sharpenFilter = new SharpenFilter();
    actualImage.applyImageFilter(sharpenFilter);
    assertImageEquals(new int[][][]{
            {{0, 145, 255}, {58, 251, 255}, {81, 149, 80}, {115, 255, 27}, {168, 255, 34}, {
                    232, 246, 76}, {141, 254, 63}},
            {{112, 244, 255}, {112, 168, 255}, {74, 255, 124}, {185, 255, 255}, {81, 255, 96},
                    {126, 255, 246}, {92, 229, 255}},
            {{255, 0, 176}, {200, 180, 88}, {76, 44, 37}, {63, 247, 137}, {57, 146, 255},
                    {63, 110, 255}, {0, 142, 255}},
            {{255, 64, 163}, {226, 181, 111}, {204, 255, 0}, {255, 155, 124}, {215, 222, 255},
                    {227, 245, 255}, {255, 132, 152}},
            {{156, 65, 154}, {176, 228, 205}, {61, 208, 0}, {244, 97, 25}, {156, 64, 187}, {
                    255, 231, 254}, {229, 30, 35}}
    }, actualImage);
  }

  @Test
  public void testValueImage() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.valueComponent();
    assertImageEquals(new int[][][]{
            {{244, 244, 244}, {210, 210, 210}, {120, 120, 120}, {200, 200, 200}, {249, 249, 249},
                    {192, 192, 192}, {255, 255, 255}},
            {{230, 230, 230}, {150, 150, 150}, {205, 205, 205}, {250, 250, 250}, {121, 121, 121},
                    {130, 130, 130}, {244, 244, 244}},
            {{243, 243, 243}, {201, 201, 201}, {118, 118, 118}, {235, 235, 235}, {204, 204, 204},
                    {100, 100, 100}, {244, 244, 244}},
            {{244, 244, 244}, {56, 56, 56}, {200, 200, 200}, {202, 202, 202}, {237, 237, 237},
                    {119, 119, 119}, {244, 244, 244}},
            {{128, 128, 128}, {199, 199, 199}, {199, 199, 199}, {221, 221, 221}, {139, 139, 139},
                    {233, 233, 233}, {149, 149, 149}}
    }, actualImage);
  }

  @Test
  public void testVerticalFlipImage() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.verticalFlip();
    assertImageEquals(new int[][][]{
            {{128, 90, 115}, {145, 170, 199}, {17, 199, 23}, {221, 100, 34}, {72, 55, 139},
                    {200, 233, 187}, {149, 5, 49}},
            {{244, 25, 99}, {38, 56, 34}, {150, 200, 13}, {202, 40, 78}, {101, 150, 237},
                    {73, 119, 111}, {244, 78, 99}},
            {{243, 55, 162}, {99, 201, 39}, {118, 40, 109}, {20, 235, 93}, {129, 204, 180},
                    {78, 49, 100}, {12, 200, 244}},
            {{90, 176, 230}, {14, 5, 150}, {89, 205, 60}, {142, 199, 250}, {33, 121, 23},
                    {45, 88, 130}, {72, 155, 244}},
            {{34, 125, 244}, {67, 210, 183}, {94, 120, 45}, {88, 200, 56}, {134, 249, 37},
                    {192, 165, 78}, {112, 255, 23}}
    }, actualImage);
  }

  @Test
  public void testRGBSplit() {
    ImageModel actualImage = testImage.deepCopy();
    ImageModel[] splits = actualImage.splitComponents();

    assertImageEquals(new int[][][]{
            {{34, 0, 0}, {67, 0, 0}, {94, 0, 0}, {88, 0, 0}, {134, 0, 0}, {192, 0, 0},
                    {112, 0, 0}},
            {{90, 0, 0}, {14, 0, 0}, {89, 0, 0}, {142, 0, 0}, {33, 0, 0}, {45, 0, 0},
                    {72, 0, 0}},
            {{243, 0, 0}, {99, 0, 0}, {118, 0, 0}, {20, 0, 0}, {129, 0, 0}, {78, 0, 0},
                    {12, 0, 0}},
            {{244, 0, 0}, {38, 0, 0}, {150, 0, 0}, {202, 0, 0}, {101, 0, 0}, {73, 0, 0},
                    {244, 0, 0}},
            {{128, 0, 0}, {145, 0, 0}, {17, 0, 0}, {221, 0, 0}, {72, 0, 0}, {200, 0, 0},
                    {149, 0, 0}}
    }, splits[0]); // red split.
    assertImageEquals(new int[][][]{
            {{0, 125, 0}, {0, 210, 0}, {0, 120, 0}, {0, 200, 0}, {0, 249, 0}, {0, 165, 0},
                    {0, 255, 0}},
            {{0, 176, 0}, {0, 5, 0}, {0, 205, 0}, {0, 199, 0}, {0, 121, 0}, {0, 88, 0},
                    {0, 155, 0}},
            {{0, 55, 0}, {0, 201, 0}, {0, 40, 0}, {0, 235, 0}, {0, 204, 0}, {0, 49, 0},
                    {0, 200, 0}},
            {{0, 25, 0}, {0, 56, 0}, {0, 200, 0}, {0, 40, 0}, {0, 150, 0}, {0, 119, 0},
                    {0, 78, 0}},
            {{0, 90, 0}, {0, 170, 0}, {0, 199, 0}, {0, 100, 0}, {0, 55, 0}, {0, 233, 0},
                    {0, 5, 0}}
    }, splits[1]); // green split.
    assertImageEquals(new int[][][]{
            {{0, 0, 244}, {0, 0, 183}, {0, 0, 45}, {0, 0, 56}, {0, 0, 37}, {0, 0, 78}, {0, 0, 23}},
            {{0, 0, 230}, {0, 0, 150}, {0, 0, 60}, {0, 0, 250}, {0, 0, 23}, {0, 0, 130},
                    {0, 0, 244}},
            {{0, 0, 162}, {0, 0, 39}, {0, 0, 109}, {0, 0, 93}, {0, 0, 180}, {0, 0, 100},
                    {0, 0, 244}},
            {{0, 0, 99}, {0, 0, 34}, {0, 0, 13}, {0, 0, 78}, {0, 0, 237}, {0, 0, 111}, {0, 0, 99}},
            {{0, 0, 115}, {0, 0, 199}, {0, 0, 23}, {0, 0, 34}, {0, 0, 139}, {0, 0, 187},
                    {0, 0, 49}}
    }, splits[2]); // blue split.
  }

  @Test
  public void testBrightenValueGreaterThan255() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.brighten(50);
    assertImageEquals(new int[][][]{
            {{84, 175, 255}, {117, 255, 233}, {144, 170, 95}, {138, 250, 106}, {184, 255, 87},
                    {242, 215, 128}, {162, 255, 73}},
            {{140, 226, 255}, {64, 55, 200}, {139, 255, 110}, {192, 249, 255}, {83, 171, 73},
                    {95, 138, 180}, {122, 205, 255}},
            {{255, 105, 212}, {149, 251, 89}, {168, 90, 159}, {70, 255, 143}, {179, 254, 230},
                    {128, 99, 150}, {62, 250, 255}},
            {{255, 75, 149}, {88, 106, 84}, {200, 250, 63}, {252, 90, 128}, {151, 200, 255},
                    {123, 169, 161}, {255, 128, 149}},
            {{178, 140, 165}, {195, 220, 249}, {67, 249, 73}, {255, 150, 84}, {122, 105, 189},
                    {250, 255, 237}, {199, 55, 99}}
    }, actualImage);
  }

  @Test
  public void testDarkenImage() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.brighten(-50);
    assertImageEquals(new int[][][]{
            {{0, 75, 194}, {17, 160, 133}, {44, 70, 0}, {38, 150, 6}, {84, 199, 0}, {142, 115, 28},
                    {62, 205, 0}},
            {{40, 126, 180}, {0, 0, 100}, {39, 155, 10}, {92, 149, 200}, {0, 71, 0}, {0, 38, 80},
                    {22, 105, 194}},
            {{193, 5, 112}, {49, 151, 0}, {68, 0, 59}, {0, 185, 43}, {79, 154, 130}, {28, 0, 50},
                    {0, 150, 194}},
            {{194, 0, 49}, {0, 6, 0}, {100, 150, 0}, {152, 0, 28}, {51, 100, 187}, {23, 69, 61},
                    {194, 28, 49}},
            {{78, 40, 65}, {95, 120, 149}, {0, 149, 0}, {171, 50, 0}, {22, 5, 89}, {150, 183, 137},
                    {99, 0, 0}}
    }, actualImage);
  }

  @Test
  public void testDarkenValueGreaterThan255() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.brighten(-260);
    assertImageEquals(new int[][][]{
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    }, actualImage);
  }

  @Test
  public void testHorizontalVerticalFlipImage() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.horizontalFlip();
    actualImage.verticalFlip();
    assertImageEquals(new int[][][]{
            {{149, 5, 49}, {200, 233, 187}, {72, 55, 139}, {221, 100, 34}, {17, 199, 23},
                    {145, 170, 199}, {128, 90, 115}},
            {{244, 78, 99}, {73, 119, 111}, {101, 150, 237}, {202, 40, 78}, {150, 200, 13},
                    {38, 56, 34}, {244, 25, 99}},
            {{12, 200, 244}, {78, 49, 100}, {129, 204, 180}, {20, 235, 93}, {118, 40, 109},
                    {99, 201, 39}, {243, 55, 162}},
            {{72, 155, 244}, {45, 88, 130}, {33, 121, 23}, {142, 199, 250}, {89, 205, 60},
                    {14, 5, 150}, {90, 176, 230}},
            {{112, 255, 23}, {192, 165, 78}, {134, 249, 37}, {88, 200, 56}, {94, 120, 45},
                    {67, 210, 183}, {34, 125, 244}}
    }, actualImage);
  }

  @Test
  public void testVerticalHorizontalFlipImage() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.verticalFlip();
    actualImage.horizontalFlip();
    assertImageEquals(new int[][][]{
            {{149, 5, 49}, {200, 233, 187}, {72, 55, 139}, {221, 100, 34}, {17, 199, 23},
                    {145, 170, 199}, {128, 90, 115}},
            {{244, 78, 99}, {73, 119, 111}, {101, 150, 237}, {202, 40, 78}, {150, 200, 13},
                    {38, 56, 34}, {244, 25, 99}},
            {{12, 200, 244}, {78, 49, 100}, {129, 204, 180}, {20, 235, 93}, {118, 40, 109},
                    {99, 201, 39}, {243, 55, 162}},
            {{72, 155, 244}, {45, 88, 130}, {33, 121, 23}, {142, 199, 250}, {89, 205, 60},
                    {14, 5, 150}, {90, 176, 230}},
            {{112, 255, 23}, {192, 165, 78}, {134, 249, 37}, {88, 200, 56}, {94, 120, 45},
                    {67, 210, 183}, {34, 125, 244}}
    }, actualImage);
  }

  @Test
  public void testVerticalVerticalFlipImage() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.verticalFlip();
    actualImage.verticalFlip();
    assertImageEquals(new int[][][]{
            {{34, 125, 244}, {67, 210, 183}, {94, 120, 45}, {88, 200, 56}, {134, 249, 37},
                    {192, 165, 78}, {112, 255, 23}},
            {{90, 176, 230}, {14, 5, 150}, {89, 205, 60}, {142, 199, 250}, {33, 121, 23},
                    {45, 88, 130}, {72, 155, 244}},
            {{243, 55, 162}, {99, 201, 39}, {118, 40, 109}, {20, 235, 93}, {129, 204, 180},
                    {78, 49, 100}, {12, 200, 244}},
            {{244, 25, 99}, {38, 56, 34}, {150, 200, 13}, {202, 40, 78}, {101, 150, 237},
                    {73, 119, 111}, {244, 78, 99}},
            {{128, 90, 115}, {145, 170, 199}, {17, 199, 23}, {221, 100, 34}, {72, 55, 139},
                    {200, 233, 187}, {149, 5, 49}},
    }, actualImage);
  }

  @Test
  public void testBlurTwice() {
    ImageModel actualImage = testImage.deepCopy();
    BlurFilter blur_filter = new BlurFilter();
    actualImage.applyImageFilter(blur_filter);
    assertImageEquals(new int[][][]{
            {{29, 79, 122}, {45, 107, 118}, {63, 119, 73}, {75, 141, 60}, {84, 140, 52},
                    {90, 132, 59}, {63, 109, 54}},
            {{69, 92, 140}, {77, 121, 136}, {85, 149, 107}, {93, 183, 114}, {88, 163, 100},
                    {82, 140, 118}, {56, 120, 121}},
            {{118, 67, 98}, {112, 107, 91}, {99, 133, 84}, {102, 161, 121}, {90, 148, 137},
                    {80, 120, 145}, {59, 98, 131}},
            {{127, 54, 78}, {120, 112, 77}, {114, 136, 56}, {133, 126, 94}, {117, 128, 148},
                    {118, 122, 143}, {107, 77, 93}},
            {{83, 50, 68}, {83, 99, 78}, {83, 114, 43}, {107, 83, 54}, {100, 84, 103},
                    {108, 94, 105}, {97, 47, 54}}
    }, actualImage);
  }

  @Test
  public void testSharpenTwice() {
    ImageModel actualImage = testImage.deepCopy();
    SharpenFilter sharpenFilter = new SharpenFilter();
    actualImage.applyImageFilter(sharpenFilter);
    assertImageEquals(new int[][][]{
            {{0, 145, 255}, {58, 251, 255}, {81, 149, 80}, {115, 255, 27}, {168, 255, 34},
                    {232, 246, 76}, {141, 254, 63}},
            {{112, 244, 255}, {112, 168, 255}, {74, 255, 124}, {185, 255, 255}, {81, 255, 96},
                    {126, 255, 246}, {92, 229, 255}},
            {{255, 0, 176}, {200, 180, 88}, {76, 44, 37}, {63, 247, 137}, {57, 146, 255},
                    {63, 110, 255}, {0, 142, 255}},
            {{255, 64, 163}, {226, 181, 111}, {204, 255, 0}, {255, 155, 124}, {215, 222, 255},
                    {227, 245, 255}, {255, 132, 152}},
            {{156, 65, 154}, {176, 228, 205}, {61, 208, 0}, {244, 97, 25}, {156, 64, 187},
                    {255, 231, 254}, {229, 30, 35}}
    }, actualImage);
  }

  @Test
  public void testOperationOnComponent() {
    ImageModel actualImage = testImage.deepCopy();
    CompRedFilter redFilter = new CompRedFilter();
    actualImage.applyImageFilter(redFilter);
    BlurFilter blurFilter = new BlurFilter();
    actualImage.applyImageFilter(blurFilter);
    assertImageEquals(new int[][][]{
            {{31, 47, 47}, {84, 138, 143}, {113, 181, 169}, {143, 186, 148}, {164, 191, 144},
                    {163, 191, 134}, {127, 142, 106}},
            {{55, 63, 63}, {148, 188, 189}, {188, 250, 231}, {217, 252, 214}, {240, 255, 221},
                    {237, 254, 223}, {176, 191, 171}},
            {{63, 58, 63}, {173, 180, 187}, {219, 249, 228}, {237, 255, 210}, {254, 255, 229},
                    {247, 255, 250}, {176, 191, 190}},
            {{63, 44, 61}, {180, 150, 182}, {227, 232, 213}, {233, 246, 172}, {244, 237, 192},
                    {241, 241, 239}, {178, 188, 191}},
            {{47, 25, 42}, {134, 94, 130}, {162, 161, 146}, {160, 173, 99}, {170, 156, 115},
                    {170, 164, 170}, {133, 137, 143}}
    }, actualImage);
  }

  @Test
  public void testLevelAdjust() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.levelsAdjust(20, 50, 100);
    assertImageEquals(new int[][][]{
            {{64, 255, 17}, {183, 154, 227}, {245, 255, 109}, {234, 185, 148}, {255, 0, 77},
                    {206, 255, 212}, {255, 0, 14}},
            {{238, 241, 80}, {0, 0, 255}, {236, 170, 162}, {255, 188, 0}, {60, 255, 14},
                    {109, 234, 255}, {197, 255, 17}},
            {{22, 145, 255}, {253, 182, 85}, {255, 89, 255}, {0, 58, 243}, {255, 173, 233},
                    {212, 124, 255}, {0, 185, 17}},
            {{17, 24, 253}, {81, 148, 64}, {255, 185, 0}, {179, 89, 212}, {255, 255, 50},
                    {199, 255, 255}, {17, 212, 253}},
            {{255, 238, 255}, {255, 251, 188}, {0, 188, 14}, {115, 255, 64}, {197, 145, 255},
                    {185, 67, 218}, {255, 0, 124}}
    }, actualImage);
  }

  @Test
  public void testColorCorrect() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.colorCorrect();
    assertImageEquals(new int[][][]{
            {{60, 23, 255}, {93, 108, 255}, {120, 18, 120}, {114, 98, 131}, {160, 147, 112},
                    {218, 63, 153}, {138, 153, 98}},
            {{116, 74, 255}, {40, 0, 225}, {115, 103, 135}, {168, 97, 255}, {59, 19, 98},
                    {71, 0, 205}, {98, 53, 255}},
            {{255, 0, 237}, {125, 99, 114}, {144, 0, 184}, {46, 133, 168}, {155, 102, 255},
                    {104, 0, 175}, {38, 98, 255}},
            {{255, 0, 174}, {64, 0, 109}, {176, 98, 88}, {228, 0, 153}, {127, 48, 255},
                    {99, 17, 186}, {255, 0, 174}},
            {{154, 0, 190}, {171, 68, 255}, {43, 97, 98}, {247, 0, 109}, {98, 0, 214},
                    {226, 131, 255}, {175, 0, 124}}
    }, actualImage);
  }

  @Test
  public void testCompression() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.compress(50);
    assertImageEquals(new int[][][]{
            {{72, 97, 255}, {27, 193, 156}, {77, 90, 64}, {118, 171, 59}, {153, 250, 47},
                    {171, 183, 112}, {91, 231, 40}},
            {{73, 186, 212}, {28, 27, 108}, {89, 186, 22}, {130, 255, 196}, {29, 148, 47},
                    {47, 81, 112}, {71, 175, 242}},
            {{255, 109, 153}, {116, 134, 96}, {126, 23, 59}, {8, 225, 96}, {114, 191, 196},
                    {94, 67, 105}, {18, 170, 247}},
            {{196, 0, 94}, {33, 25, 37}, {164, 191, 12}, {196, 39, 49}, {98, 199, 196},
                    {78, 75, 105}, {250, 114, 156}},
            {{106, 83, 149}, {99, 138, 154}, {23, 122, 42}, {212, 87, 48}, {51, 78, 162},
                    {196, 223, 168}, {121, 26, 11}}
    }, actualImage);
  }

  @Test
  public void testHistogram() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.histogram();
    assertImageEquals(new int[][][]{
            {{34, 125, 244}, {67, 210, 183}, {94, 120, 45}, {88, 200, 56}, {134, 249, 37},
                    {192, 165, 78}, {112, 255, 23}},
            {{90, 176, 230}, {14, 5, 150}, {89, 205, 60}, {142, 199, 250}, {33, 121, 23},
                    {45, 88, 130}, {72, 155, 244}},
            {{243, 55, 162}, {99, 201, 39}, {118, 40, 109}, {20, 235, 93}, {129, 204, 180},
                    {78, 49, 100}, {12, 200, 244}},
            {{244, 25, 99}, {38, 56, 34}, {150, 200, 13}, {202, 40, 78}, {101, 150, 237},
                    {73, 119, 111}, {244, 78, 99}},
            {{128, 90, 115}, {145, 170, 199}, {17, 199, 23}, {221, 100, 34}, {72, 55, 139},
                    {200, 233, 187}, {149, 5, 49}}
    }, actualImage);
  }

  @Test
  public void testSepiaSplit() {
    ImageModel actualImage = testImage.deepCopy();
    SepiaFilter sepiaFilter = new SepiaFilter();
    actualImage.applyColorFilter(sepiaFilter);
    ImageModel[] splitImages = actualImage.splitImage(60);
    splitImages[0].mergeSplits(splitImages[1]);
    assertImageEquals(new int[][][]{
            {{156, 139, 108}, {222, 198, 154}, {138, 123, 96}, {199, 177, 138}, {251, 224, 174},
                    {217, 193, 151}, {244, 218, 170}},
            {{214, 191, 149}, {38, 34, 26}, {204, 182, 142}, {255, 228, 178}, {110, 98, 77},
                    {110, 98, 76}, {194, 172, 134}},
            {{168, 150, 117}, {201, 179, 139}, {98, 87, 68}, {206, 184, 143}, {242, 215, 168},
                    {87, 78, 60}, {205, 182, 142}},
            {{134, 119, 93}, {64, 57, 45}, {215, 192, 149}, {125, 111, 87}, {200, 178, 139},
                    {141, 126, 98}, {175, 155, 121}},
            {{141, 126, 98}, {225, 201, 156}, {164, 146, 114}, {170, 151, 118}, {97, 86, 67},
                    {255, 255, 203}, {72, 64, 50}}
    }, splitImages[0]);
  }

  @Test
  public void testLevelAdjustColorCorrect() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.levelsAdjust(30, 50, 200);
    actualImage.colorCorrect();
    assertImageEquals(new int[][][]{
            {{45, 219, 31}, {234, 175, 255}, {255, 219, 116}, {255, 219, 179}, {255, 0, 65},
                    {255, 219, 255}, {255, 0, 18}},
            {{255, 219, 122}, {18, 0, 255}, {255, 197, 200}, {255, 219, 18}, {38, 219, 18},
                    {116, 219, 255}, {255, 219, 31}},
            {{38, 119, 255}, {255, 214, 78}, {255, 31, 255}, {18, 37, 255}, {255, 202, 255},
                    {255, 86, 255}, {18, 219, 31}},
            {{31, 0, 255}, {72, 125, 45}, {255, 219, 18}, {255, 31, 255}, {255, 219, 78},
                    {255, 219, 255}, {31, 219, 255}},
            {{255, 219, 255}, {255, 219, 255}, {18, 219, 18}, {173, 219, 45}, {255, 119, 255},
                    {255, 49, 255}, {255, 0, 140}}
    }, actualImage);
  }

  @Test
  public void testBlurSplit() {
    ImageModel actualImage = testImage.deepCopy();
    BlurFilter blurFilter = new BlurFilter();
    actualImage.applyColorFilter(blurFilter);
    ImageModel[] splitImages = actualImage.splitImage(60);
    splitImages[0].mergeSplits(splitImages[1]);
    assertImageEquals(new int[][][]{
            {{33, 66, 33}, {42, 84, 42}, {24, 47, 24}, {34, 68, 34}, {42, 84, 42}, {38, 75, 38},
                    {40, 81, 40}},
            {{42, 84, 42}, {11, 22, 11}, {35, 70, 35}, {49, 99, 49}, {19, 37, 19}, {22, 44, 22},
                    {39, 78, 39}},
            {{32, 64, 32}, {34, 68, 34}, {19, 38, 19}, {36, 73, 36}, {45, 90, 45}, {17, 35, 17},
                    {41, 82, 41}},
            {{25, 49, 25}, {12, 23, 12}, {35, 70, 35}, {23, 45, 23}, {40, 80, 40}, {26, 53, 26},
                    {31, 62, 31}},
            {{26, 53, 26}, {43, 86, 43}, {27, 55, 27}, {28, 57, 28}, {20, 40, 20}, {53, 107, 53},
                    {13, 26, 13}}
    }, actualImage);
  }

  @Test
  public void testDownscaleValid() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.downscale(4, 3);
    assertImageEquals(new int[][][]{
            {{34, 125, 244}, {92, 147, 49}, {173, 193, 64}},
            {{128, 146, 213}, {101, 179, 118}, {55, 99, 102}},
            {{244, 40, 131}, {126, 126, 69}, {89, 115, 140}},
            {{157, 74, 111}, {106, 161, 29}, {139, 163, 167}}
    }, actualImage);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleInvalidGreaterDimensions() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.downscale(10, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleInvalidDimensionZero() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.downscale(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleInvalidDimensionNegative() {
    ImageModel actualImage = testImage.deepCopy();
    actualImage.downscale(-2, -2);
  }

}