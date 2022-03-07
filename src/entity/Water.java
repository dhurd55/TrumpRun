package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import handlers.Content;
import tileMap.TileMap;

public class Water extends MapObstacle {
	
	double damage;
	
	private ArrayList<ArrayList<BufferedImage>> sprites;
	
	
	public Water(TileMap tm) {
		super(tm);
		
		width = 32;
		height = 32;
		cwidth = 32;
		cheight = 28;
		
		sprites = Content.waterSprites;
		
		animation = new Animation();
		animation.setFrames(sprites.get(0));
		animation.setDelay(100);
	}
	
	@Override
	protected void interaction(Player player) {
	}
	
	public void update() {
		animation.update();
	}

	@Override
	public void collisionEvent(MapObject m) {
		// TODO Auto-generated method stub
		
	}
}
