package entity;

import tileMap.TileMap;

public abstract class Collectable extends MapObject {
	
	
	public Collectable(TileMap tm) {
		super(tm);
				
		remove = false;
	}
	
	public abstract void Action(Player p);
	
	public void update() {
		animation.update();
	}
}
