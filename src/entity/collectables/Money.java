package entity.collectables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.Animation;
import entity.Collectable;
import entity.MapObject;
import entity.Player;
import handlers.Content;
import tileMap.TileMap;

public class Money extends Collectable{
	
	private ArrayList<ArrayList<BufferedImage>> sprites;
	
	Player player;

	public Money(TileMap tm, Player player) {
		super(tm);
		
		width = 16; 
		height = 16;
		cwidth = 16;
		cheight = 16;
		
		this.player = player;
		
		// load sprites
		sprites = Content.moneySprites;
		
		animation = new Animation();
		animation.setFrames(sprites.get(0));
		animation.setDelay(130);
		
		facingRight = true;
	}
	
	// get current num money and increment it by random value 
	public void Action(Player player) {
		int money = player.getMoney();
		
		int value = (int)(Math.random() * 1000) + 25;
		player.setNumMoney(money += value - value%5);
	}
	
	public void update() {
		super.update();
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}

	@Override
	public void collisionEvent(MapObject m) {
		// TODO Auto-generated method stub
		
	}
}