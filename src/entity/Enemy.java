package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import handlers.Content;
import tileMap.TileMap;

public abstract class Enemy extends MapObject{
	
	// enemy stuff
	protected boolean dead;
	protected int health;
	protected int maxHealth;
	protected int damage;
	
	protected ArrayList<ArrayList<BufferedImage>> sprites;
	
	//actions
	public final int IDLE = 0;
	public final int WALKING = 1;
	public final int DYING = 2;
	public final int ATTACKING = 3;
	
	public Enemy(TileMap tm) {
		super(tm);
	}
	
	public void setDead(Boolean b) { dead = b;	}
	public Boolean isDead() { return dead; }
	public void setHealth(int i) { health = i; }
	public int getHealth() { return health; }
	public int getDamage() { return damage;}
	
	public void hit(int damage) {
		
		health -= damage;
		
		if (health < 1) {
			dead = true;
		}
	}
	
	
	public void getNextPosition() {
	}
	
	public void update() {
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}
