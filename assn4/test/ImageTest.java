//import org.junit.Test;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//
//import model.Image;
//import model.PixelADT;
//import model.RGBImage;
//import model.RGBPixel;
//
//import static javax.imageio.ImageIO.read;
//
//public class ImageTest {
//
//  public Image load_image() throws IOException {
//    File f = new File("/Users/hariharasudan/CS5010/group/cs5010-group-assignments/assn4/images/paint_new.png");
//    BufferedImage img = read(f);
//
//    int width = img.getWidth();
//    int height = img.getHeight();
//    Image img1 = new RGBImage(height,width);
//    for (int y = 0; y < height; y++) {
//      for (int x = 0; x < width; x++) {
//        int pixel = img.getRGB(x, y);
//
//        RGBPixel rgb_pixel = new RGBPixel((pixel >> 16) & 0xFF,(pixel >> 8) & 0xFF,pixel & 0xFF);
//        img1.setPixel(y,x,rgb_pixel);
//      }
//    }
//    return img1;
//  }
//
//  public void save_image(Image img) throws IOException {
//
//    BufferedImage buffer_image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
//    for (int y = 0; y < img.getHeight(); y++) {
//      for (int x = 0; x < img.getWidth(); x++) {
////        Color color = new Color(img., green[y][x], blue[y][x]);
//        PixelADT rgb_pixel = img.getPixel(y,x);
//        int rgb_pixel_int = (rgb_pixel.getRed() <<16) | (rgb_pixel.getGreen() <<8) | (rgb_pixel.getBlue());
//        buffer_image.setRGB(x, y, rgb_pixel_int);
//      }
//    }
//
//    ImageIO.write(buffer_image, "png",new File("/Users/hariharasudan/CS5010/group/cs5010-group-assignments/assn4/images/paint_new_2.png"));
//  }
//
//
//  @Test
//  public void test_loadsave() throws IOException {
//    Image img = load_image();
//    save_image(img);
//  }
//
//  @Test
//  public void test_brighten() throws IOException {
//    Image img = load_image();
//    img.brighten(50);
//    save_image(img);
//
//  }
//
//  @Test
//  public void test_luma() throws IOException {
//    Image img = load_image();
//    img.lumaComponent();
//    save_image(img);
//  }
//
//  @Test
//  public void test_intensity() throws IOException {
//    Image img = load_image();
//    img.intensityComponent();
//    save_image(img);
//  }
//
//  @Test
//  public void test_value() throws IOException {
//    Image img = load_image();
//    img.valueComponent();
//    save_image(img);
//  }
//
//  @Test
//  public void test_sepia() throws IOException {
//    Image img = load_image();
//    img.sepia();
//    save_image(img);
//  }
//
//  @Test
//  public void test_verticalFlip() throws IOException {
//    Image img = load_image();
//    img.verticalFlip();
//    save_image(img);
//  }
//
//  @Test
//  public void test_horizontalFlip() throws IOException {
//    Image img = load_image();
//    img.horizontalFlip();
//    save_image(img);
//  }
//
//  @Test
//  public void test_split() throws IOException{
//    Image img = load_image();
//    Image[] image_split = img.split();
//    save_image(image_split[0]);
//  }
//
//}
