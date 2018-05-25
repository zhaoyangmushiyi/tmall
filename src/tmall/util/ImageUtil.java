package tmall.util;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

    public static BufferedImage change2jpg(File file) {
        // TODO Auto-generated method stub
        try {
            Image image = Toolkit.getDefaultToolkit().createImage(file.getAbsolutePath());
            PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, -1, -1, true);
            pixelGrabber.grabPixels();
            int width = pixelGrabber.getWidth(), hight = pixelGrabber.getHeight();
            final int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
            final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
            DataBuffer dataBuffer = new DataBufferInt((int[]) pixelGrabber.getPixels(), width * hight);
            WritableRaster raster = Raster.createPackedRaster(dataBuffer, width, hight, width, RGB_MASKS, null);
            BufferedImage bufferedImage = new BufferedImage(RGB_OPAQUE, raster, false, null);
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void resizeImage(File srcFile, int width, int height, File destFile){
        try {
            Image image = ImageIO.read(srcFile);
            image = resizeImage(image, width, height);
            ImageIO.write((RenderedImage)image, "jps",destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Image resizeImage(Image srcImage, int width, int height) {
        try {
            BufferedImage bufferedImage = null;
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            return bufferedImage;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
