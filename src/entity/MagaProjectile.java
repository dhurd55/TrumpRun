package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import handlers.Content;
import tileMap.TileMap;

public class MagaProjectile extends MapObject{
	
	ArrayList<ArrayList<BufferedImage>>  sprites;
	
	private Boolean hit;
	
	public MagaProjectile(TileMap tm, boolean facingRight) {
		super(tm);
		
		sprites = Content.magaProjectileSprites;
		
		moveSpeed = 3;
		width = 16;
		height = 16;
		cwidth = 16;
		cheight = 11;
		
		hit = false;
		remove = false;
		this.facingRight = facingRight;
		
		if (facingRight) dx = moveSpeed;
		else dx = -moveSpeed;
		
		animation = new Animation();
		animation.setFrames(sprites.get(0));
		animation.setDelay(85);
	}
	
	public void setHit() {
		if (hit) return;
		hit = true;
		dx = 0;
	}
	
	public void update() {
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0) { 
			setHit();
			remove = true;
		}
		
		animation.update();
	}
	
	public void draw(Graphics2D g) {		
		super.draw(g);
	}

	@Override
	public void collisionEvent(MapObject m) {
		// TODO Auto-generated method stub
		
	}

}
