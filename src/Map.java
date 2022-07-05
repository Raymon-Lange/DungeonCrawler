import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Map {
	int mTileWidth, mTitleHeight;
	int mTilesAcross, mTitlesHigh;
	int mCols, mRows;
	int mMap[] = new int[10];
	ArrayList<Integer> mMoveableTiles = new ArrayList<Integer>();
	BufferedImage mMapSheet;
	BufferedImage mBackground;

	public Map(URL mapDef, URL mapName) {
		
		mTileWidth = 24;
		mTitleHeight = 24;
		mTilesAcross = 10;
		mTitlesHigh = 10;
		mCols = 5;
		mRows = 5;
		
		loadMapDefinition(mapDef);

		loadImage(mapName);
		
		buildMapImage();
		
	}
	
	private void loadImage(URL imageName) {
		try {
			mMapSheet = ImageIO.read(imageName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadMapDefinition(URL mapName) {
		String line;
		int count =0;

		try {
			
			InputStream in = mapName.openStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			StringBuffer strBuff = new StringBuffer();
			
			//Step: line will contain map def, just size right now (rows,cols)
			String mapDef = bf.readLine().toString();
			String[] mapArray = mapDef.split(",");
			mTitlesHigh = Integer.parseInt(mapArray[0]);
			mTilesAcross = Integer.parseInt(mapArray[1]);

			
		    //Step: Now we know how many tiles to expect
			mMap = new int[mTilesAcross * mTitlesHigh];
			
			//Step: next line will contain the size of the titles (height,width)
			mapDef = bf.readLine().toString();
			mapArray = mapDef.split(",");
			mTitleHeight = Integer.parseInt(mapArray[0]);
			mTileWidth = Integer.parseInt(mapArray[1]);
			
			//Step: next line will contain the how many rows and cols are in map sheet (rows,cols)
			mapDef = bf.readLine().toString();
			mapArray = mapDef.split(",");
			mRows = Integer.parseInt(mapArray[0]);
			mCols = Integer.parseInt(mapArray[1]);
			
			//Step: next line will contain be "walkable titles"
			mapDef = bf.readLine().toString();
			mapArray = mapDef.split(",");
			for(String rowValue : mapArray)
			{
				mMoveableTiles.add(Integer.parseInt(rowValue));
			}
			
			//Now for the rest of the map, read in line by line and split by a comma
			while ((line = bf.readLine()) != null) {
				strBuff.append(line + "\n");
				String[] mapRow = line.split(",");
				for(String rowValue : mapRow)
				{
					mMap[count] = Integer.parseInt(rowValue);
					count++;
				}
			}
				
			//System.out.println("File Name : " + mapDef + "\n");
			//System.out.println(strBuff.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private int getBlockId(int xPos, int yPos) {
		
		int xTile = xPos / mTileWidth;
		int yTitle = yPos / mTitleHeight;
		
		int location = yTitle * mTilesAcross;
		location += xTile;
		
		return mMap[location];
	}

	private boolean checkForCollision(int xPos, int yPos) {
		int blockId = getBlockId(xPos, yPos);
	
		boolean isSafe = mMoveableTiles.contains(blockId);
		
		//Return the opposite.
		return !isSafe;
	}
	
	public boolean checkForCollision(Sprite player, int shrink) {
				
	    boolean topLeft = checkForCollision(player.getX()+ shrink, player.getY()+ shrink);
	    boolean topRight = checkForCollision(player.getX() + player.getImageWidth() - shrink, player.getY()+ shrink);
	    boolean bottomLeft = checkForCollision(player.getX()+ shrink, player.getY()+ player.getImageHeight() - shrink);
	    boolean bottomRight = checkForCollision(player.getX()+player.getImageWidth() - shrink, player.getY() +player.getImageHeight() - shrink);
	    
		//Return the opposite.
		return !(topRight || topLeft || bottomRight || bottomLeft);
	}

	public void draw(Graphics2D back) {
		back.drawImage(mBackground,  null, 0, 0);
		
	}
	
	public void draw(Graphics2D back, int topX, int topY, int bottomX, int bottomY) {		
		back.drawImage(mBackground, 0, 0, bottomX - topX, bottomY - topY, topX, topY, bottomX, bottomY , null);	
	}

	
	
	private void buildMapImage() {
		int place = 0;
		int screenWidth = mTilesAcross * mTileWidth;
		int screenHeight = mTitlesHigh * mTitleHeight;
		
		mBackground = new BufferedImage(screenWidth, screenHeight,
                BufferedImage.TYPE_INT_RGB);
		
		Graphics2D back = mBackground.createGraphics();
		
		//System.out.println(screenWidth + " " +screenHeight);
		for(int y = 0; y  <  screenHeight ; y += mTitleHeight)
		{
			for(int x = 0; x < screenWidth ; x += mTileWidth )
			{
				BufferedImage mapTile = getMapTile(place++);
				back.drawImage(mapTile, null, x, y);
			}
		}
	}
	
	public BufferedImage getMapTile(int n)
	{
		BufferedImage tile = null;
		int tileValue = mMap[n];
		
	    int tileX = 0 + (tileValue % mCols) * mTileWidth;
		int tileY = 0 + (tileValue / mCols) * mTitleHeight;
	
		try {
			tile = mMapSheet.getSubimage(tileX, tileY, mTitleHeight, mTileWidth);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	   return tile;
	}

}
