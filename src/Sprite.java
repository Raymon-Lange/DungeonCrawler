import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Sprite extends BaseGameObject {

	// The Sprite Sheet
	BufferedImage spriteSheet;
	
	Map<Integer,AnimatedAction> mActions = new HashMap<Integer, AnimatedAction>();

	// Since this sprite can only move 8 directions
	int dir = 0;
	
	AnimatedAction mCurrentAction;
	
	public Sprite(int x, int y, int height, int width, URL imageName) {
		super(x, y, height, width);
		loadImage(imageName);
	}

	
	public void loadAction(int id, int x, int y, int width, int height,int maxFrames, int columns, int rows, int frameDelay, boolean isLoop) {
		AnimatedAction newAction = new AnimatedAction();
		newAction.setId(id);
		newAction.setImageX(x);
		newAction.setImageY(y);
		newAction.setFrameHeight(height);
		newAction.setFrameWidth(width);
		newAction.setFramedelay(frameDelay);
		newAction.setMaxframe(maxFrames);
		newAction.setLoop(isLoop);
		
		mActions.put(id, newAction);
	}
	
	public void setAnimation(int id)
	{
		mCurrentAction = mActions.get(id);
	}
	
	public int getAnimationId()
	{
		return mCurrentAction.getId();
	}

	public void draw(Graphics2D back) {
		if (spriteSheet != null) {
			BufferedImage nextFame = grabFrame();
			back.drawImage(nextFame, null, this.getX(), this.getY());

			mCurrentAction.updateFrame();
			
			drawBoudingBox(back);
		}
	}
	
	public void draw(Graphics2D back, int xPos, int yPos)
	{
		if (spriteSheet != null) {
			BufferedImage nextFame = grabFrame();
			back.drawImage(nextFame, null, xPos, yPos);

			mCurrentAction.updateFrame();
			
			if(mCurrentAction.isEndAction())
			{
				mCurrentAction.resetEndAction(false);
				setAnimation(0);	
			}
			//drawBoudingBox(back);
		}
	}
	
	private void drawBoudingBox(Graphics2D back) {
		back.setColor(Color.blue);
		back.drawRect(getX(), getY(), getWidth(), getHeight());
	}

	private void loadImage(URL imageName) {
		try {
			spriteSheet = ImageIO.read(imageName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BufferedImage grabFrame() {
		int x = mCurrentAction.getImageX() + ( mCurrentAction.getCurframe() / mCurrentAction.getRows()) * mCurrentAction.getFrameWidth();
		//     the start    the row for anim  what frame we are on   how tall is the anim
		int y = mCurrentAction.getImageY() + (mCurrentAction.getFrameHeight() * dir) + 
					( mCurrentAction.getCurframe() % mCurrentAction.getColumns()) * mCurrentAction.getFrameHeight();
		
		//System.out.println(x + "  " + y + " " + imageWidth + " " + imageHeight);
		
		BufferedImage temp = spriteSheet.getSubimage(x, y, mCurrentAction.getFrameWidth() - 1,
				mCurrentAction.getFrameHeight() - 1);

		return temp;
	}

	
	public void setDir(int dir){this.dir = dir;}
	public int getDir(){return dir;}
	public int getImageHeight(){ return mCurrentAction.getFrameHeight(); }
	public int getImageWidth(){ return mCurrentAction.getFrameWidth();}

}
