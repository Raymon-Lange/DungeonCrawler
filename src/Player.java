import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.net.URL;


public class Player extends Sprite{

	private boolean canMove = false;
	int mMovement = 3;
	
	int maxHealth = 100;
	int currentHealth = 100;
	
	int damage = 20;
	
	public Player(int x, int y, int height, int width, URL imageName) {
		super(x, y, height, width, imageName);
		calculateDir();
	}

	boolean moveUp = false;
	boolean moveDown= false;
	boolean moveLeft= false;
    boolean moveRight= false;
    
	private int NORTH = 0;
	private int WEST = 1;
	private int SOUTH = 2;
	private int EAST = 3;
	private int NORTHEAST = 4;
	private int SOUTHEAST= 5;
	private int SOUTHWEST = 6;
	private int NORTHWEST = 7;
	private int STANDING = 8;
	
	public int getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	public int getCurrentHealth() {
		return currentHealth;
	}
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public void setMoveUp(boolean moveUp) {this.moveUp = moveUp; setDir(calculateDir());}
	public void setMoveDown(boolean moveDown) {this.moveDown = moveDown;setDir(calculateDir());}
	public void setMoveLeft(boolean moveLeft) {this.moveLeft = moveLeft;setDir(calculateDir());}
	public void setMoveRight(boolean moveRight) { this.moveRight = moveRight; setDir(calculateDir());}
	
	public void keyPress(int keyCode)
	{
	    if(keyCode == KeyEvent.VK_W)
            moveUp = true;
	    if(keyCode == KeyEvent.VK_S)
        	moveDown = true;    
	    if(keyCode == KeyEvent.VK_A)
        	moveLeft = true; 
	    if(keyCode == KeyEvent.VK_D)
        	moveRight = true; 
	    if(keyCode == KeyEvent.VK_SPACE)
	    	attack();
	    
	    this.setDir(calculateDir());
	}
	
	public void keyRelease(int keyCode) {
	    if(keyCode == KeyEvent.VK_W)
            moveUp = false;
	    if(keyCode == KeyEvent.VK_S)
        	moveDown = false;    
	    if(keyCode == KeyEvent.VK_A)
        	moveLeft = false; 
	    if(keyCode == KeyEvent.VK_D)
        	moveRight = false; 
	    
	    this.setDir(calculateDir());
	}
	
    private int calculateDir()
    {
    	if(moveUp && !moveDown && !moveLeft && !moveRight)
    	{
    		setYVelocity(-mMovement);
    		setXVelocity(0);
    		return NORTH;
    	}
    	if(!moveUp && moveDown && !moveLeft && !moveRight)
    	{
    		setYVelocity(mMovement);
    		setXVelocity(0);
    		return SOUTH;
    	}
    	if(!moveUp && !moveDown && moveLeft && !moveRight)
    	{
    		setYVelocity(0);
    		setXVelocity(-mMovement);
    		return EAST;
    	}
    	if(!moveUp && !moveDown && !moveLeft && moveRight)
    	{
    		setYVelocity(0);
    		setXVelocity(mMovement);
    		return WEST;
    	}
    	if(moveUp && !moveDown && moveLeft && !moveRight)
    	{
    		setYVelocity(-mMovement);
    		setXVelocity(-mMovement);
    		return NORTHWEST;
    	}
    	if(moveUp && !moveDown && !moveLeft && moveRight)
    	{
    		setYVelocity(-mMovement);
    		setXVelocity(mMovement);
    		return NORTHEAST;
    	}
    	if(!moveUp && moveDown && moveLeft && !moveRight)
    	{
    		setYVelocity(mMovement);
    		setXVelocity(-mMovement);
    		return SOUTHWEST;
    	}
    	if(!moveUp && moveDown && !moveLeft && moveRight)
    	{
    		setYVelocity(mMovement);
    		setXVelocity(mMovement);
    		return SOUTHEAST;
    	}
    	
    	if(canMove)
    	{
    		setYVelocity(0);
    		setXVelocity(0);
    	    return STANDING;
    	}
		setYVelocity(0);
		setXVelocity(0);
    	return getDir();
    }
    
	private void attack() {
		setAnimation(1);	
	}
	
	public void draw(Graphics2D back, int xPos, int yPos)
	{
		drawHealthBar(back, xPos, yPos);
		
		super.draw(back, xPos, yPos);
	}
	
	private void drawHealthBar(Graphics2D back, int xPos, int yPos) {

	    double percentOfLife = ((double) currentHealth / (double)maxHealth) ;
		
	    if(percentOfLife == 1.0)
	    {
	    	//DO nothing.
	    }
	    else if(percentOfLife < 1.0 && percentOfLife > 0.75)
	    {
			back.setColor(Color.GREEN);
			back.fillRect(xPos, yPos-7,(int)(this.getWidth()* (percentOfLife)) , 5);
			
			back.setColor(Color.black);
			back.drawRect(xPos, yPos-8, this.getWidth(), 6); 
	    }
	    else if(percentOfLife < 0.75 && percentOfLife > 0.25)
	    {
	    	back.setColor(Color.ORANGE);
			back.fillRect(xPos, yPos-7, (int)(this.getWidth()* (percentOfLife)), 5);
			
			back.setColor(Color.black);
			back.drawRect(xPos, yPos-8, this.getWidth(), 6);
	    }
	    else
	    {
	    	back.setColor(Color.RED);
			back.fillRect(xPos, yPos-7, (int)(this.getWidth()* (percentOfLife)), 5);
			
			back.setColor(Color.black);
			back.drawRect(xPos, yPos-8, this.getWidth(), 6);
	    }		
	}
	
	public void takeDamage(int damage) {
		currentHealth -= damage;
		
	}
	
	public void heal(int health)
	{
		currentHealth += health;
		
		if(currentHealth > maxHealth)
			currentHealth = maxHealth;
	}
	
}
