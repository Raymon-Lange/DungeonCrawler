import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;



public class Game extends Applet implements Runnable, KeyListener {

	Thread game;
	Player mPlayer;
	LinkedList<Player> mBadGuys = new LinkedList<Player>();
	Sprite coin;
	LightRadius mLight;
	PowerUp mPotion;
	Map mMap;
	int mWidth = 960;
	int mHeight = 960;
	int mScreenWidth = 960;
	int mScreenHeight = 960;
	int score = 0;

	BufferedImage mBackground;
	BufferedImage mDark;
	Image mEnding = null;
	Image mHappyBday = null;

	Graphics2D g2d;

	boolean gameOver = false;

	// create a random number generator
	Random rand = new Random();

	int badGuyCounter = 0;
	
	public void init() {
		resize(mScreenWidth, mScreenHeight);

		loadPlayer();
		loadCoin();
		loadBadGuy();

		mBackground = new BufferedImage(mWidth, mHeight,
				BufferedImage.TYPE_INT_RGB);
		g2d = mBackground.createGraphics();

		mDark = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB);

		URL mapSrc = makeURL("resoures/level3.txt");
		URL mapImage = makeURL("resoures/maptitles.png");
		mMap = new Map(mapSrc, mapImage);
		mLight = new LightRadius(mWidth,mHeight);
		
		URL potionImage = makeURL("resoures/potions.png");
		mPotion = new PowerUp(400, 600, 16, 16, potionImage);
		mPotion.loadAction(0, 0, 0, 16, 16, 0, 1, 1, 1000000, false);
		mPotion.setAnimation(0);
		

		addKeyListener(this);
	}

	private void loadCoin() {

		URL imageSrc = makeURL("resoures/coin.png");

		coin = new Sprite(150, 150, 14, 14, imageSrc);
		coin.loadAction(0, 0, 0, 14, 14, 8, 1, 1, 10, true);
		coin.setAnimation(0);
	}

	private URL makeURL(String imageName) {
		URL imageSrc = null;

		try {
			imageSrc = new URL(getCodeBase(), imageName);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imageSrc;
	}

	private void loadBadGuy() {
		URL imageSrc = makeURL("resoures/misslink.png");
		
		Player badGuy = new Player( 200, 200, 33, 33, imageSrc);
	    badGuy.loadAction(0, 0, 0, 33, 33, 4, 1, 1, 14, true);
		badGuy.setAnimation(0);
		
		mBadGuys.add(badGuy);
	 
		
		for (int i = 1; i <= 4; i++) {
		
			badGuy = new Player(i * 200, i * 200, 33, 33, imageSrc);
		    badGuy.loadAction(0, 0, 0, 33, 33, 4, 1, 1, 14, true);
			badGuy.setAnimation(0);
			

			mBadGuys.add(badGuy);
		}
		
	
		/*
		imageSrc = makeURL("resoures/archer.png");
		
		for (int i = 1; i <= 4; i++) {
			Player badGuy;

			badGuy = new Player(1000 -(i * 200), i * 200, 43, 50, imageSrc);
			badGuy.loadAction(0, 0, 0, 43, 50, 5, 1, 1, 10, true);
			badGuy.setAnimation(0);

			mBadGuys.add(badGuy);
		}
		*/
	}

	private void loadPlayer() {

		URL imageSrc = makeURL("resoures/link2.png");

		mPlayer = new Player(32,32,24,18,imageSrc);
		mPlayer.loadAction(0, 0, 0, 18, 24, 7, 1, 1, 10, true);
		mPlayer.setAnimation(0);
		
		mPlayer.loadAction(1, 0, 219, 32, 35, 5, 1, 1, 1, false);
	}

	public void paint(Graphics g) {
		g.drawImage(mBackground, this.getX(), this.getY(), this);
	}

	public void update(Graphics g) {
		// Clear the screen
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, mWidth - 1, mHeight);

		// Draw the background
		// mMap.draw(g2d);

		int xPos = mPlayer.getX();// + (mPlayer.getImageWidth() / 2);
		int yPos = mPlayer.getY();// + (mPlayer.getImageHeight() / 2);

		int topX = xPos - (mScreenWidth / 2);
		int topY = yPos - (mScreenHeight / 2);

		int bottomX = xPos + (mScreenWidth / 2);
		int bottomY = yPos + (mScreenHeight / 2);

		if (topX < 0) {
			bottomX = mWidth;
			topX = 0;
		}

		if (bottomX > mWidth) {
			topX = mWidth - mScreenWidth;
			bottomX = mWidth;
		}

		if (topY < 0) {
			bottomY = mScreenHeight;
			topY = 0;
		}

		if (bottomY > mWidth) {
			topY = mWidth - mScreenHeight;
			bottomY = mWidth;
		}

		if (!gameOver) {
				mMap.draw(g2d, topX, topY, bottomX, bottomY);

				// Draw the title bar
				drawTitleBar(g2d);

				// Draw the objects
				coin.draw(g2d, coin.getX() - topX, coin.getY() - topY);
				mPotion.draw(g2d, mPotion.getX() - topX, mPotion.getY() - topY);
				mPlayer.draw(g2d, mPlayer.getX() - topX, mPlayer.getY() - topY);

				Iterator<Player> iterator = mBadGuys.iterator();
				while (iterator.hasNext()) {
					Sprite badGuy = iterator.next();
					badGuy.draw(g2d, badGuy.getX() - topX, badGuy.getY() - topY);
				}
				
				//mLight.draw(g2d, topX, topY, bottomX, bottomY);
				paint(g);
			}
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		mPlayer.keyPress(keyCode);

	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		mPlayer.keyRelease(keyCode);

		if (keyCode == KeyEvent.VK_P) {
			gameOver = false;
			gameReset();
			score = 0;
		}
	}

	private void gameReset() {
		loadPlayer();
		loadCoin();
		loadBadGuy();
	}

	public void keyTyped(KeyEvent e) {

	}

	public void start() {
		game = new Thread(this);
		game.start();
	}

	public void drawTitleBar(Graphics2D titleBar) {
		titleBar.setColor(Color.BLACK);
		String title = "Chaser Challenge v1.4      Score: " + score;
		titleBar.drawString(title, 50, 10);
	}

	@Override
	public void run() {
		// acquire the current thread
		Thread t = Thread.currentThread();
		// keep going as long as the thread is alive
		while (t == game) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (!gameOver) {
				updatePlayers();
				checkForPoint();
				checkForObjects();
				checkplayerAttack();
				checkEndGame();
				calculateLight();
			}
			repaint();
		}
	}

	private void checkForObjects() {

		boolean hitObject = mPlayer.checkCollisions(mPotion, 0);
		if(hitObject)
		{
			mPotion.use(mPlayer);
			mPotion.setPos(0, 0);
			
		}
	}

	private void checkplayerAttack() {
		if(mPlayer.getAnimationId() == 1)
		{
			Player badGuy = null;
			boolean hit = false;
			Iterator<Player> iterator = mBadGuys.iterator();
			while (iterator.hasNext()) {

				badGuy = iterator.next();
				hit = mPlayer.checkCollisions(badGuy, 0);
			
				if(hit)
				{			
					this.doDamage(badGuy, mPlayer);			
					break;
				}
			}
			
			if(hit)
			{
				if(badGuy.currentHealth < 0)
				{
					mBadGuys.remove(badGuy);
				}
			}
		}
		
	}

	private void doDamage(Player defending, Player attacking ) {
		defending.takeDamage(attacking.getDamage());
		
		weaponPushBack(defending,attacking);
	}

	private void weaponPushBack(Player defending, Player attacking) {
		int xVel = 5;
		int yVel = 5;

		int xPos = defending.getX();
		int yPos = defending.getY();
		
		if (attacking.getX() > defending.getX())
			xPos -= xVel;

		if (attacking.getX() < defending.getX())
			xPos += xVel;
		
		if (attacking.getY() > defending.getY())
			yPos -= yVel;

			yPos += yVel;

		defending.setPos(xPos, yPos);
	}

	private void calculateLight() {
		mLight.calculateLight(mPlayer.getX(), mPlayer.getY(), 800);
	}

	private void updatePlayers() {
		movePlayer();

		if (badGuyCounter++ > 3) {
			moveEnemies();
			badGuyCounter = 0;
		}
	}

	private void movePlayer() {
		//Step: Save current pos
		int xPos = mPlayer.getX();
		int yPos = mPlayer.getY();
		boolean isSafe = true; // its always safe

		// Updating one vertices at time to allow the player to "glide" along
		// the walls
		mPlayer.updateXPostion();
		isSafe = mMap.checkForCollision(mPlayer, 8);
		if (!isSafe)
			mPlayer.setX(xPos);

		// Now check the other vertices
		mPlayer.updateYPostion();
		isSafe = mMap.checkForCollision(mPlayer, 8);
		if (!isSafe)
			mPlayer.setY(yPos);

		// Keep player in the screen
		if (mPlayer.getX() > mWidth - mPlayer.getWidth())
			mPlayer.setX(mWidth - mPlayer.getWidth());

		if (mPlayer.getX() < 0)
			mPlayer.setX(0);

		if (mPlayer.getY() > mHeight - mPlayer.getHeight())
			mPlayer.setY(mHeight - mPlayer.getHeight());

		if (mPlayer.getY() < 0)
			mPlayer.setY(0);
	}

	private void checkEndGame() {
		Iterator<Player> iterator = mBadGuys.iterator();
		
		boolean hit = false;
		while (iterator.hasNext()) {
			if (gameOver)
				return;

			Player badGuy = iterator.next();
			hit = mPlayer.checkCollisions(badGuy, 10);
			
			if(hit)
			{
				doDamage(mPlayer, badGuy);
			}
		}
		
		if(mPlayer.getCurrentHealth() < 0)
		{
			gameOver = true;
		}
	}

	private void checkForPoint() {

		boolean ateApple = mPlayer.checkCollisions(coin, 0);
		if (ateApple) {
			score += 1;

			int xPos = 0;
			int yPos = 0;
			boolean found = false;

			while (!found) {
				xPos = Math.abs(rand.nextInt() % mWidth);
				yPos = Math.abs(rand.nextInt() % mHeight);

				coin.setX(xPos);
				coin.setY(yPos);

				found = mMap.checkForCollision(coin, 0);
			}
		}
	}

	private void moveEnemies() {
		Iterator<Player> iterator = mBadGuys.iterator();
		while (iterator.hasNext()) {
			Player badGuy = iterator.next();
			if (mPlayer.getX() > badGuy.getX())
				badGuy.setMoveRight(true);
			else
				badGuy.setMoveRight(false);

			if (mPlayer.getX() < badGuy.getX())
				badGuy.setMoveLeft(true);
			else
				badGuy.setMoveLeft(false);

			if (mPlayer.getY() > badGuy.getY())
				badGuy.setMoveDown(true);
			else
				badGuy.setMoveDown(false);

			if (mPlayer.getY() < badGuy.getY())
				badGuy.setMoveUp(true);
			else
				badGuy.setMoveUp(false);

			int xPos = badGuy.getX();
			int yPos = badGuy.getY();
			boolean isSafe = true; // its always safe

			// Updating one vertices at time to allow the player to "glide"
			// along the walls
			badGuy.updateXPostion();
			isSafe = mMap.checkForCollision(badGuy, 8);
			if (!isSafe)
				badGuy.setX(xPos);

			// Now check the other vertices
			badGuy.updateYPostion();
			isSafe = mMap.checkForCollision(badGuy, 8);
			if (!isSafe)
				badGuy.setY(yPos);
		}

	}

}
