package entity.collectables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import handlers.Content;
import tileMap.TileMap;
import entity.Actions;
import entity.Animation;
import entity.Collectable;
import entity.MapObject;
import entity.Player;

public class MagaHat extends Collectable{
	
	ArrayList<ArrayList<BufferedImage>> sprites;
	
	
	public MagaHat(TileMap tm, Player player) {
		super(tm);
		
		width = 16;
		height = 16;
		cwidth = 16;
		cheight = 16;
				
		// loadSprites
		sprites = Content.magaHatSprites;
		
		animation = new Animation();
		animation.setFrames(sprites.get(0));
		animation.setDelay(350);
	}
	
	public void setRemove(Boolean b) { remove = b; };
	
	// sets projectiles to max 
	public void Action(Player player) {
		player.setNumMagaProjectiles(5);
		player.setCanAttack(true);
		player.setCurrentAction(Actions.NONE); // resets action so hat appears right away
		player.setSpriteIndex(7);  // sets sprites to use magahats
	}
	
	public void update() {
		super.update();
	}

	@Override
	public void collisionEvent(MapObject m) {
		// TODO Auto-generated method stub
		
	}

}
