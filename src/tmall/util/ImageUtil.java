package tmall.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;

public class ImageUtil {

	public static BufferedImage change2jpg(File file) {
		// TODO Auto-generated method stub
		try {
			Image image = Toolkit.getDefaultToolkit().createImage(file.getAbsolutePath());
			PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, -1, -1, true);
			pixelGrabber.grabPixels();
			int width = pixelGrabber.getWidth(),hight = pixelGrabber.getHeight();
			final int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
			final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
			DataBuffer dataBuffer = new DataBufferInt((int[])pixelGrabber.getPixels(), width*hight);
			WritableRaster raster = Raster.createPackedRaster(dataBuffer, width, hight, width, RGB_MASKS, null);
			BufferedImage bufferedImage = new BufferedImage(RGB_OPAQUE, raster, false, null);
			return bufferedImage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
