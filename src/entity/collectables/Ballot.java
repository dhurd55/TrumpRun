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

public class Ballot extends Collectable{
	
	private ArrayList<ArrayList<BufferedImage>>  sprites;

	public Ballot(TileMap tm, Player player) {
		super(tm);
		
		width = 16;
		height = 16;
		cwidth = 16;
		cheight = 16;
				
		// load sprites
		sprites = Content.ballotSprites;
		
		animation = new Animation();
		animation.setFrames(sprites.get(0));
		animation.setDelay(200);
	}
	
	// gets players current number of ballots and increments by one
	public void Action(Player player) {

		int ballots = player.getBallots();
		player.setNumBallots(ballots += 1);
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
