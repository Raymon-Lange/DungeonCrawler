
public class BaseGameObject {
	
	private int mX; 
	private int mY;

	private int mHeight;
	private int mWidth;
	
	private int mXVelocity = 3;
	private int mYVelocity = 3;
		
	private boolean canMove = false;

	public BaseGameObject(int x, int y, int height, int width)
	{
		setX(x); 
		setY(y);
		setHeight(height);
		setWidth(width);
	}
	
	public boolean getCanMove()
	{
		return canMove;
	}
   
	public void setCanMove(boolean value)
	{
		canMove = value;
	}
	
	public void UpdatePostion()
	{
		updateXPostion();
		updateYPostion();
	}
	
	public void updateXPostion()
	{
	    MovePlayerX(mXVelocity);
	}
	
	public void updateYPostion()
	{
	    MovePlayerY(mYVelocity);
	}
	
	public void MovePlayerX(int delta)
	{
	     mX += delta;
	}
	
	public void MovePlayerY(int delta)
	{
		mY += delta;
	}
	
	public Boolean checkCollisions(BaseGameObject other, int shrink)
	{
		//Top Left
		 boolean isInside = checkIfPointInside(other.getX()+shrink, other.getY()+shrink);		
		
		 if(isInside)
			 return true;
		 
		 //Bottom Left
		 isInside = checkIfPointInside(other.getX()+shrink, other.getY()+other.getWidth()-shrink);
		 
		 if(isInside)
			 return true;
		 
		 //Top Left
		 isInside = checkIfPointInside(other.getX()+other.getHeight()-shrink, other.getY()+shrink);		
			
		 if(isInside)
			 return true;
		 
		 //Bottom Right 
		 isInside = checkIfPointInside(other.getX()+other.getHeight()-shrink, other.getY()+other.getWidth()-shrink);		
			
		 if(isInside)
			 return true;
		 
		 return false;
	}

	private boolean checkIfPointInside(int x, int y) {
		if(mX <= x &&
		   mY <= y &&
		   mX + mHeight >= x &&
		   mY + mWidth >= y)
			{
			    return true;
			}
				
			return false;
	}

	int getX() { return mX; }
	int getY() { return mY; }
	public int getHeight() {return mHeight; }
	public int getWidth() { return mWidth; }
    
    void setPos(int x, int y){mX = x; mY =y;}
    void setX(int mX) { this.mX = mX; }
    void setY(int mY) { this.mY = mY; }
    public void setHeight(int mHeight) { this.mHeight = mHeight;}
    public void setWidth(int mWidth) { this.mWidth = mWidth; }
    
	public int getXVelocity() { return mXVelocity; }
	public void setXVelocity(int mXVelocity) { this.mXVelocity = mXVelocity; }
	public int getYVelocity() { return mYVelocity; }
	public void setYVelocity(int mYVelocity) { this.mYVelocity = mYVelocity; }
    


}
