package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;
import tileMap.Tile;
import tileMap.TileMap;

public abstract class MapObject {
	
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	// position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	// dimensions
	protected int width;
	protected int height;
	
	// collision box
	protected int cwidth;
	protected int cheight;
	protected int xOffset;
	protected int yOffset;
	
	// collision
	protected int currRow;
	protected int currCol;
	protected int currRowBottom;
	protected int currColRight;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected int topLeft;
	protected int topRight;
	protected int bottomLeft;
	protected int bottomRight;
	protected int bottomCenter;
	
	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	// movement 
	protected boolean walkLeft;
	protected boolean walkRight;
	protected boolean runLeft;
	protected boolean runRight;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	protected boolean jumpEnabled;
	protected boolean elevating;
	
	// movement attributes 
	protected double moveSpeed;
	protected double maxMoveSpeed;
	protected double moveSpeedWalk;
	protected double maxWalkSpeed;
	protected double moveSpeedRun;
	protected double maxRunSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	protected boolean remove;
	protected boolean triggered;
	
	// constuctor
	public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
	}
	
	public boolean intersects(MapObject o) {
		
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		
		return r1.intersects(r2);
	}
	
	public boolean contains(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		
		return r1.contains(r2);
	}
	
	public Rectangle getRectangle() {
		
		return new Rectangle((int)(x - cwidth/2) + xOffset, (int)(y - cheight/2) + yOffset, cwidth, cheight);
	}
	
	public void calculateCorners(double x, double y) {
		
		// gets the row and columns that each side of player has
		int leftTile = (int)(x - cwidth / 2) / tileSize;
		int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
		int topTile = (int)(y - cheight / 2) / tileSize;
		int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
		
		topLeft = tileMap.getType(topTile, leftTile);
		topRight = tileMap.getType(topTile, rightTile);
		bottomRight = tileMap.getType(bottomTile, rightTile);
		bottomLeft = tileMap.getType(bottomTile, leftTile);
//		bottomCenter = tileMap.getType(bottomTile, (leftTile + rightTile) / 2);
	}
	
	public void checkTileMapCollision() {
		// sloped tiles 
//		int xOrigin;
//		int yOrigin;
//		double px;
//		double py;
		
		// current col and row player is in
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		currColRight = ((int)x + cwidth/2) / tileSize;
		currRowBottom = ((int)y + cheight/2 -1 ) / tileSize;
		
		
		// x and y destination // current position plus vector
		xdest = x + dx;
		ydest = y + dy;
		
		// holds new position
		xtemp = x;
		ytemp = y;
		
		
		// get neighboring tiles on y axis
		calculateCorners(x, ydest);
		
		if (dy < 0) {    
			
			if (topLeft == Tile.BLOCKED || topRight == Tile.BLOCKED) {
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
			}
			else 
				ytemp += dy;
		}
		if (dy > 0) {
			if (bottomLeft == Tile.BLOCKED || bottomRight == Tile.BLOCKED) {
				dy = 0;
				falling = false;
				jumping = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			}
			else 
				ytemp += dy;
		}
		
		
		calculateCorners(xdest, y);
		if (dx < 0) {
			if (topLeft == Tile.BLOCKED || bottomLeft == Tile.BLOCKED) {
				
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}
		if (dx > 0) {
			if (topRight == Tile.BLOCKED || bottomRight == Tile.BLOCKED) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}
		
		if(!falling && !elevating) {
			calculateCorners(x, ydest + 1);
			if(bottomLeft != Tile.BLOCKED && bottomRight != Tile.BLOCKED) {				
				falling = true;
			}
		}
		
	}
	
	public abstract void collisionEvent(MapObject m);
	
	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getCWidth() { return cwidth; }
	public int getCHeight() { return cheight; }
	public boolean shouldRemove() { return remove; }
	
	public void setPosition(double x, double y) {
		
		this.x = x;
		this.y = y;
	}
	
	public void setVector(double dx, double dy) {
		
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setMapPosition() {
		
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}
	
	public void setWalkLeft(boolean b ) {  walkLeft = b; }
	public void setWalkRight(boolean b) { walkRight = b; }
	public void setRunLeft(boolean b) { runLeft = b; }
	public void setRunRight(boolean b) { runRight = b; }
	public void setJumping(boolean b) { jumping = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	public void setTriggered(boolean b) { triggered = b; }
	
	
	public boolean notOnScreen() {
		return x + xmap + width < 0 || 
				x + xmap - width > GamePanel.WIDTH ||
				y + ymap + height < 0 ||
				y  + ymap - height > GamePanel.HEIGHT;
	}
	
	// draws CollisionBox
	public void drawCollisionBox(Graphics2D g) {
		// collision box
		Rectangle r = getRectangle();
		r.x += xmap;
		r.y += ymap;
		g.draw(r);
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();
		
		if (facingRight) {
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
			
		}
		else {
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2 + width), (int)(y + ymap - height / 2), -width, height, null);
		}
	}
}
