
public class AnimatedAction {

	int id;

	int frameWidth, frameHeight;
	int imageX, imageY;
	// The tracking variables of the sprite
	int curframe = 0, maxframe = 0;
	int framecount = 0, framedelay = 0;
	int columns= 1, rows = 1;
	
	boolean isLoop = true;
	boolean endAction = false;
	
	public boolean isLoop() {
		return isLoop;
	}
	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}
	
	public boolean isEndAction() {
		return endAction;
	}
	public void resetEndAction(boolean endAction) {
		this.endAction = endAction;
		this.curframe = 0;
	}
	public int getFrameWidth() {
		return frameWidth;
	}
	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}
	public int getFrameHeight() {
		return frameHeight;
	}
	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}
	public int getImageX() {
		return imageX;
	}
	public void setImageX(int imageX) {
		this.imageX = imageX;
	}
	public int getImageY() {
		return imageY;
	}
	public void setImageY(int imageY) {
		this.imageY = imageY;
	}
	public int getCurframe() {
		return curframe;
	}
	public void setCurframe(int curframe) {
		this.curframe = curframe;
	}
	public int getMaxframe() {
		return maxframe;
	}
	public void setMaxframe(int maxframe) {
		this.maxframe = maxframe;
	}
	public int getFramecount() {
		return framecount;
	}
	public void setFramecount(int framecount) {
		this.framecount = framecount;
	}
	public int getFramedelay() {
		return framedelay;
	}
	public void setFramedelay(int framedelay) {
		this.framedelay = framedelay;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void updateFrame()
	{
		framecount++;
		if (framecount >= framedelay) {
			curframe++;
			if (curframe >= maxframe){
				curframe = 0;
				if(!isLoop)
					endAction = true;
			}
				

			framecount = 0;
		}
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
