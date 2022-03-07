package entity;

import tileMap.TileMap;

public abstract class MapObstacle extends MapObject {

	protected int damage;
	protected boolean triggered;
	
	public MapObstacle(TileMap tm) {
		super(tm);
	}

	public void setTriggered(boolean b) { triggered = b; }
	
	protected abstract void interaction(Player player);
	
	
	public abstract void update();

}
