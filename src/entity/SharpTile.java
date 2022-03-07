package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import handlers.Content;
import tileMap.Tile;
import tileMap.TileMap;

public class SharpTile extends MapObstacle {

	private ArrayList<BufferedImage> sprite;
	
	private boolean home;
	private boolean goingUp;
	private boolean cycle;

	public SharpTile(TileMap tm) {
		super(tm);
		
		width  = 30;
		height = 32;
		cwidth = 26;
		cheight = 28;
		
		moveSpeed = 1.5;
		maxMoveSpeed = 2;
		damage = 1;
		
		triggered = false;

		goingUp = false;
		cycle = false;
		home = true;

		sprite = new ArrayList<BufferedImage>();
		
		try {
			sprite.add(ImageIO.read(Content.class.getResourceAsStream("/sprites/objects/sharpTile.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprite);
		animation.setDelay(-1);
	}
		
	public void getNextPosition() {

		if (dy == 0) {
			if (home) {
				home = false;
			}
			else if (goingUp) {
				home = true;
				cycle = false;
				goingUp = false;
			}
			else
				goingUp = true;
		}
		
		if (goingUp) {
			dy += -moveSpeed;
			
			if (dy < -moveSpeed) 
				dy = -moveSpeed;
		}
		
		else {
			dy += maxMoveSpeed;
			
			if (dy > maxMoveSpeed) {
				dy = maxMoveSpeed;
			}
		}
		
	}
	
	public void interaction(Player p) {
		p.hit(damage);
	}
	
	public void update() {
		if (triggered) {
			cycle = true;
		}
		if (cycle) {
			getNextPosition();
			checkTileMapCollision();
			setPosition(xtemp, ytemp);
		}
	}

	@Override
	public void collisionEvent(MapObject m) {
		// TODO Auto-generated method stub
		
	}
}
