package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import tileMap.TileMap;


public class TriggerBox {
	
	private MapObject m;
	private double x;
	private double y;
	private int width;
	private int height;
	
	private int xOffset;
	private int yOffset;
	
	private int boxType;
	
	public final int INTERSECT = 0;
	public final int CONTAIN = 1;
	
	public TriggerBox(MapObject m, int width, int height) {
		this.width = width;
		this.height = height;
		
		boxType = 0;
		
		this.m = m;
	}
	
	public void setTriggered(boolean b) {
		m.setTriggered(b);
	}
	
	public void setPosition(double x, double y) {
		this.x = x + xOffset;
		this.y = y + yOffset;
	}
	
	public void setType(int i) { boxType = i; }
	
	public void setxOffset(int i) {
		xOffset = i;
	}
	
	public void setyOffset(int i) {
		yOffset = i;
	}
	
	public int getType() { return boxType; }
	
	public Rectangle getRectangle() {
		return new Rectangle((int)(x - width/2) , (int)(y - height/2), width, height);
	}
	
	public boolean contains(MapObject m) {
		
		Rectangle r = getRectangle();
		Rectangle r1 = m.getRectangle();
		
		return r.contains(r1);
	}
	
	public boolean intersects(MapObject m) {
		
		Rectangle r = getRectangle();
		Rectangle r1 = m.getRectangle();
		
		return r.intersects(r1);
	}
	
	public void drawCollisionBox(Graphics2D g, TileMap tileMap) {
		Rectangle r = getRectangle();
		r.x += tileMap.getx();
		r.y += tileMap.gety();
		g.draw(r);
	}
	
}
