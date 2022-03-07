package entity.Enemies;

import entity.Actions;
import entity.Animation;
import entity.Enemy;
import entity.MapObject;
import handlers.Content;
import tileMap.TileMap;

public class AntifaEnemy extends Enemy {
	
	public AntifaEnemy(TileMap tm) {
		super(tm);
		
		width = 32;
		height = 32;
		cwidth = 16;
		cheight = 32;
		health = maxHealth = 5;
		damage = 1;
		moveSpeedWalk = 0.2;
		maxWalkSpeed = 0.4;
		dead = false;
		facingRight = true;
		walkRight = true;
		
		sprites = Content.antifaSprites;
		
		animation = new Animation();
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(90);
		currentAction = IDLE;
	}
	
	public void getNextPosition() {
		
		if (dx == 0) {
			if (walkRight) {
				walkRight = false;
				walkLeft = true;
			}
			else if (walkLeft) {
				walkLeft = false;
				walkRight = true;
			}
		
		} 
		if (walkRight) {
			dx += moveSpeedWalk;
			if(dx > maxWalkSpeed) 
				dx = maxWalkSpeed;
		}
		
		if (walkLeft) {
			dx -= moveSpeedWalk;
			if (dx < -maxWalkSpeed) {
				dx = -maxWalkSpeed;
			}
		}
		if (dy > 0) {
			falling = true;
			dy += fallSpeed;
		}
		if (falling && dy == 0) {
			falling = false;
			walkRight = true;
		}
	}
	
	public void update() {
		
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);	
		
		if (dead) {
			if (currentAction != DYING) {
				currentAction = DYING;
				
				animation.setFrames(sprites.get(DYING));
				animation.setDelay(50);
			}
		}
		else if (walkRight || walkLeft) {
			if (currentAction != WALKING ) {
				currentAction = WALKING;
				
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(90);
			}
		}
		else {
			if (currentAction != IDLE) {
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(100);
				currentAction = IDLE;
			}
		}
		
		animation.update();
		
		if (currentAction == DYING && animation.hasPlayedOnce()) {
			remove = true;
		}
		
		if (walkRight) { 
			facingRight = true;
		}
		else if(walkLeft)
			facingRight = false;
		
	}

	@Override
	public void collisionEvent(MapObject m) {
		// TODO Auto-generated method stub
		
	}

}
