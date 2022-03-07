package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import handlers.Content;
import tileMap.Tile;
import tileMap.TileMap;

public class MovingTile extends MapObject {
	
	private ArrayList<BufferedImage> sprites;
	
	private TriggerBox tb;
	
	private Player p;
	
	private boolean up;
	private boolean moving;
	private boolean alwaysMoving;
	
	public MovingTile(TileMap tm, Player p) {
		super(tm);
		
		this.p = p;

		width = 32;
		height = 8;
		cwidth = 32;
		cheight = 68;
		
		moveSpeed = .3;
		fallSpeed = .3;
		
		up = true;
		moving = false;
		alwaysMoving = false;
		facingRight = true;
		
		// sprites and animation
		sprites = Content.elevatorSprites.get(0);
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(-1);
		
	}
	
	public void init(TriggerBox tb) {
		this.tb = tb;
	}
	
	public void checkTileMapCollision() {
		// x and y destination // current position plus vector
		xdest = x + dx;
		ydest = y + dy;
		
		// holds new position
		xtemp = x;
		ytemp = y;
		
		calculateCorners(x, ydest);
		
		if (dy < 0) {
			
			if (topLeft == Tile.BLOCKED || topRight == Tile.BLOCKED	 ) {
				dy = 0;
			}
			else 
				ytemp += dy;
		}
		
		if (dy > 0) {
			if (bottomLeft == Tile.BLOCKED || bottomRight == Tile.BLOCKED) {
				dy = 0;
			}
			else 
				ytemp += dy;
		}
	}
	
	public void getNextPosition() {
		
		if (dy == 0) {
			if (up) {
				up = false;
				moving = false;
			}
				
			else {
				up = true;
				moving = false;
			}
		}	
		if (up) {
			dy -= moveSpeed;
			if (dy < -moveSpeed) {
				dy = -moveSpeed;
			}
		}
		else {	
			dy += moveSpeed;
			if(dy > moveSpeed) {
				dy = moveSpeed;
			}
		}
	}
	
	public void TBoxInteraction() {
		
		p.setElevating(triggered, this);
	}
	
	public void update() { 
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		TBoxInteraction();
		tb.setPosition(xtemp, ytemp);
		
	}

	@Override
	public void collisionEvent(MapObject m) {
		// TODO Auto-generated method stub
		
	}
	
}
