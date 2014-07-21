package graphics;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class GraphicsStorage {
	
	/*
	 * 
	 * This is where entities will get a reference to the Image that fits them
	 * 
	 * This also has simple manipulating methods for rotation or color changes
	 * 
	 */

	private static BufferedImage Fighter1;
	
	StringBuilder imagename=new StringBuilder();
	
	public GraphicsStorage(){
		applyGraphics();
	}

	public BufferedImage getGraphic(String side,String facing){
		imagename.setLength(0);
		imagename.append(side+facing);
		
		/*switch(side){
		case "WEST":
			switch(facing){
			case "NORTH": return Fighter1; 
			case "NORTHEAST": return Fighter1;  
			case "EAST": return Fighter1; 
			case "SOUTHEAST": return Fighter1;  
			case "SOUTH": return Fighter1; 
			case "SOUTHWEST": return Fighter1; 
			case "WEST": return Fighter1;
			case "NORTHWEST": return Fighter1; 
			}
		}
		return null;*/
		return Fighter1; 
	}



	private void applyGraphics() {
		try {
			
			//System.out.println(getClass().getResource("/images/fighter1.png").getUserInfo());
			final URL Fighter1res = getClass().getResource("/images/fighter1.png");
			

			Fighter1 = ImageIO.read(Fighter1res);
		}
		catch (final IOException e) {
			e.printStackTrace();
		}

	}
	//SCALED IMAGE CODE
	public static BufferedImage getScaledImage(BufferedImage image, int width, int height) {
		int imageWidth  = image.getWidth();
		int imageHeight = image.getHeight();

		double scaleX = (double)width/imageWidth;
		double scaleY = (double)height/imageHeight;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

		return bilinearScaleOp.filter(
				image,
				new BufferedImage(width, height, image.getType()));
	}

	public BufferedImage rotate(BufferedImage image,double theta)
	{
		/*
		 * Affline transform only works with perfect squares. The following
		 * code is used to take any rectangle image and rotate it correctly.
		 * To do this it chooses a center point that is half the greater
		 * length and tricks the library to think the image is a perfect
		 * square, then it does the rotation and tells the library where
		 * to find the correct top left point. The special cases in each
		 * orientation happen when the extra image that doesn't exist is
		 * either on the left or on top of the image being rotated. In
		 * both cases the point is adjusted by the difference in the
		 * longer side and the shorter side to get the point at the
		 * correct top left corner of the image. NOTE: the x and y
		 * axes also rotate with the image so where width > height
		 * the adjustments always happen on the y axis and where
		 * the height > width the adjustments happen on the x axis.
		 *
		 */
		AffineTransform xform = new AffineTransform();

		if (image.getWidth() > image.getHeight())
		{
			xform.setToTranslation(0.5 * image.getWidth(), 0.5 * image.getWidth());
			xform.rotate(theta);

			int diff = image.getWidth() - image.getHeight();

			switch ((int)Math.toDegrees(theta))
			{
			case 90:
				xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth() + diff);
				break;
			case 180:
				xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth() + diff);
				break;
			default:
				xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth());
				break;
			}
		}
		else if (image.getHeight() > image.getWidth())
		{
			xform.setToTranslation(0.5 * image.getHeight(), 0.5 * image.getHeight());
			xform.rotate(theta);

			int diff = image.getHeight() - image.getWidth();

			switch ((int)Math.toDegrees(theta))
			{
			case 180:
				xform.translate(-0.5 * image.getHeight() + diff, -0.5 * image.getHeight());
				break;
			case 270:
				xform.translate(-0.5 * image.getHeight() + diff, -0.5 * image.getHeight());
				break;
			default:
				xform.translate(-0.5 * image.getHeight(), -0.5 * image.getHeight());
				break;
			}
		}
		else
		{
			xform.setToTranslation(0.5 * image.getWidth(), 0.5 * image.getHeight());
			xform.rotate(theta);
			xform.translate(-0.5 * image.getHeight(), -0.5 * image.getWidth());
		}

		AffineTransformOp op = new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR);

		return op.filter(image, null);
	} 
	
	static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	public BufferedImage recolor(BufferedImage original,String side){
		BufferedImage recolored=deepCopy(original);
		for (int i = 0; i < recolored.getHeight(); i++) {
			for (int j = 0; j < recolored.getWidth(); j++) {

				int rgb = recolored.getRGB(j, i);
				int alpha= (rgb>>24)&255;
				int red= (rgb>>16)&255;
				int green= (rgb>>8)&255;
				int blue= (rgb)&255;
				int newrgb=0;

				if (side.equals("OPFOR")&&red==91&&green==155&&blue==84) {// detect tha green
					newrgb = (alpha << 24) + (234 << 16) + (59 << 8) + 30;
					recolored.setRGB(j,i,newrgb);
				}
				else if(side.equals("INDEPENDENT")&&red==91&&green==155&&blue==84) {
					newrgb = (alpha << 24) + (66 << 16) + (125 << 8) + 246;
					recolored.setRGB(j,i,newrgb);
				}

			}
		}

		return recolored;
	}
}

