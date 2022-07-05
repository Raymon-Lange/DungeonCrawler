import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;


public class LightRadius {
	BufferedImage mOverlay;
	
	int mWidth = 0;
	int mHeight = 0;
	
	public LightRadius(int mapWidth, int mapHeight)
	{
		mWidth = mapWidth;
		mHeight = mapHeight;
		
		init();
	}

	private void init() {
		mOverlay= new BufferedImage( mWidth,mHeight,java.awt.Transparency.TRANSLUCENT);
		
		Graphics2D overlay = mOverlay.createGraphics();
		
		Color color = Color.white;
		overlay.setColor(color);
		overlay.fillRect(0,0,mWidth-1,mHeight-1);
	}
	
	private BufferedImage getOverlay()
	{
		BufferedImage dest = TransformGrayToTransparency(mOverlay);
		return dest;
	}
	
	public void calculateLight(int posX, int posY, int radius)
	{
		Graphics2D overlay = mOverlay.createGraphics();
		Color color = null;
		double step = 0.05;
		double r = radius / 10 ;
		
		for( float i = 1; i > 0 ; i-= step)
		{
			float alpha = ((1.0f * i ));
			color = new Color((float) 0,(float) 0, (float) 0,alpha);
			overlay.setColor(color);
			
			r += 10;
		
			drawCirlce(overlay, (int)r, posX, posY);
		}
	}
	
	private void drawCirlce(Graphics2D overlay, int r, int ox, int oy) {

		overlay.fillOval(ox-r/2, oy-r/2, r, r);
	}
	
	  private BufferedImage TransformGrayToTransparency(BufferedImage image)
	  {
	    RGBImageFilter filter = new RGBImageFilter()
	    {
	      public final int filterRGB(int x, int y, int rgb)
	      {
	        return (rgb << 8) & 0xFF000000;
	      }
	    };

	    ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
	    Image tmp  = Toolkit.getDefaultToolkit().createImage(ip);
	    
		BufferedImage dest= new BufferedImage( mWidth,mHeight,java.awt.Transparency.TRANSLUCENT);
		
	    Graphics2D g2 = dest.createGraphics();
	    g2.drawImage(tmp, 0, 0, null);
	    g2.dispose();
	    	
	    return dest;
	  }

	public void draw(Graphics2D g2d, int topX, int topY, int bottomX, int bottomY) {
		BufferedImage dest = getOverlay();
		g2d.drawImage(dest, 0, 0, bottomX - topX, bottomY - topY, topX, topY, bottomX, bottomY , null);	
	}


}
