package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.imageio.ImageIO;

import tileMap.TileMap;
import handlers.Content;

public class Player extends MapObject {
	
	// player stuff
	private int ballots;
	private int money;
	private int health;
	private int maxHealth;
	private int numMagaProjectiles;
	private int maxMagaProjectiles;
	private int damage;
	private int magaDamage;
	private boolean dead;
	private boolean flinching;
	private int flinchCount;
	private boolean attacking;
	private boolean magAttacking;
	private boolean canJump;
	private boolean canMagattack;
	
	// player weapon objects
	ArrayList<MagaProjectile> magaProjectiles;
	
	// Map objects and enemies
	ArrayList<Enemy> enemies;
	ArrayList<Collectable> collectables;
	ArrayList<MapObstacle> mapObstacles;
	ArrayList<TriggerBox> triggerBoxes;
	
	// animations
	private ArrayList<ArrayList<BufferedImage>> sprites;
	private final int NUMFRAMES[] = {3, 6, 21, 6, 1, 2, 1, 3, 6, 21, 6, 1, 2, 1};
	private Actions currentAction;
//	private Actions previousAction;
	private int spriteIndex;
	
	public Player(TileMap tm) {
		
		super(tm);
		
		width = 32;
		height = 32;
		cwidth = 13;
		cheight = 31;
		
		// movement
		moveSpeedWalk = 0.08;
		maxWalkSpeed = 1;
		moveSpeedRun = 0.15;
		maxRunSpeed = 1.8;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.5;
		stopJumpSpeed = 0.3;
		
		// initial setup
		ballots = 0;
		money = 0;
		maxMagaProjectiles = 5;
		numMagaProjectiles = 0;
		health = maxHealth = 5;
		damage = 1;
		magaDamage = 5;
		flinchCount = 0;
		
		magaProjectiles = new ArrayList<MagaProjectile>();
		
		facingRight = true;
		canJump = true;
		attacking = false;
		magAttacking = false;
		canMagattack = false;
		elevating = false;
		
		// load sprites 
		sprites = new ArrayList<ArrayList<BufferedImage>>();
		
		try {
			BufferedImage loadedSheet = ImageIO.read(Content.class.getResourceAsStream("/sprites/player/TRUMP_FULLSHEET.png"));
		
			int spritesDown = loadedSheet.getHeight() / height;
		
			
			for (int i = 0; i < spritesDown; i++) {
				ArrayList<BufferedImage> spritesRow = new ArrayList<BufferedImage>();
				
				for (int j = 0; j < NUMFRAMES[i]; j++) {
					spritesRow.add(loadedSheet.getSubimage(j * width, i * height, width, height));
				}
				
				sprites.add(spritesRow);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
			
		animation = new Animation();
		setAnimation(Actions.IDLE);
		spriteIndex = 0;
	}
	
	public void init(ArrayList<Enemy> enemies, ArrayList<Collectable> collectables, ArrayList<MapObstacle> mapObstacles, ArrayList<TriggerBox> triggerBoxes) {
		this.enemies = enemies;
		this.collectables = collectables;
		this.mapObstacles = mapObstacles;
		this.triggerBoxes = triggerBoxes;
	}
	
	public void hit(int damage) {
		if (flinching) return;
		flinching = true;
		flinchCount = 0;
		health -= damage;
		if (facingRight) {
			dx -= 3;
		} else
			dx += 3;
		if (dy < 0) {
			dy += 1;
		}
	}
	
	public void setAnimation(Actions a) {
		animation.setFrames(sprites.get(a.index() + spriteIndex));
		animation.setDelay(a.delay());
		width = a.width();
	}
	
	public void setWalkLeft(boolean b ) { 
		if (!walkLeft) {
			if (!walkRight && b) walkLeft = b;
		}
		else walkLeft = b;
	}
	
	public void setWalkRight(boolean b) {
		if (!walkRight) {
			if (!walkLeft && b) walkRight = b;
		} else walkRight = b;
	}
	
	public void setRunLeft(boolean b) {
		if (!runLeft) {
			if (!runRight && b) runLeft = b;
		} else runLeft = b;
	}
	
	public void setRunRight(boolean b) { 
		if (!runRight) {
			if (!runLeft & b) runRight = b;
		} else runRight = b; 
	}
	
	public void setJumping(boolean b) {
		if (canJump) {
			jumping = b;
		}
	}
	
	public void setCanJump(boolean b) {
		if(b) return;
		else if (dy == 0) {
			canJump = true;
		}
	}
	
	public void setMagAttacking(Boolean b) { 
		if(numMagaProjectiles > 0)
			magAttacking = b;
	}
	
	public void setElevating(boolean b, MovingTile e) {
		
		elevating = b;
		
		if (elevating) {
			if(dy > 0) {
				falling = false;
				jumping = false;
				dy = 0;
				y = e.y - e.height/2 - cheight/2;
			}
			else {
				y += e.dy;
				x += e.dx;
			}
		}
	}
	
	public void setNumBallots(int i) { ballots = i; }
	public void setNumMoney(int i) { money = i; }
	public void setNumMagaProjectiles(int i){ numMagaProjectiles = i;}
	public void setCanAttack(boolean b) { canMagattack = b; }
	public void setCurrentAction(Actions a) {currentAction = a; }
	public void setSpriteIndex(int i ) { spriteIndex = i; }
	
//	//public void setAttacking(Boolean b ) { attacking = b; }
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	public int getNumMagaProjectiles() { return numMagaProjectiles; }
	public int getMaxMagaProjectiles() { return maxMagaProjectiles; }
	public int getMoney() { return money; }
	public int getBallots() { return ballots; }
	
	// get player next position
	public void getNextPosition() {
		
		/*
		///// movement
		*/
		//runninggetNextPosition
		if (runRight) {
			dx += moveSpeedRun;
			if (dx > maxRunSpeed) {
				dx = maxRunSpeed;
			}
		}
		else if (runLeft) {
			dx -= moveSpeedRun;
			if (dx < -maxRunSpeed) {
				dx = -maxRunSpeed;
			}
		}
		// walking
		else if(walkLeft) {
			dx -= moveSpeedWalk;
			if (dx < -maxWalkSpeed) {
				dx = -maxWalkSpeed;
			}
		}
		else if(walkRight) {
			dx += moveSpeedWalk;
			if (dx > maxWalkSpeed) {
				dx = maxWalkSpeed;
			}
		}
		// stopping
		else {
			if (dx > 0) {
				dx -= stopSpeed;
				if (dx < 0) {
					dx = 0;
				}
			}
			else if (dx < 0 ) {
				dx += stopSpeed;
				if (dx > 0) {
					dx = 0;
				}
			}
		}
		// falling
		if (falling) {
			dy += fallSpeed;
			
			if (dy > 0) {
				jumping = false;
				canJump = false;
				
			}
			if (dy < 0 && !jumping) dy += stopJumpSpeed;
			
			if (dy > maxFallSpeed) dy = maxFallSpeed;
		}
		// jumping
		if (jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}
		
		if ((magAttacking || attacking) && !
				(falling || jumping)) {
			canJump = false;
			dx = 0;
		}
	}
	
	public void checkEntityInteraction() {
		
		// enemy interaction
		 ListIterator<Enemy> e = enemies.listIterator();
		 while(e.hasNext()) {
			 Enemy enemy = e.next();
			 if(enemy.intersects(this) && !enemy.dead) {
				 hit(enemy.getDamage());
			 }
			 
			 for (int i = 0; i < magaProjectiles.size(); i++) {
				 
				 if (enemy.intersects(magaProjectiles.get(i)) && !enemy.dead) { 
					 enemy.hit(magaDamage);
					 magaProjectiles.get(i).remove = true;
				 }
			 }
		 	}
		 
		 // Collectible interaction
		 ListIterator<Collectable> c = collectables.listIterator();
		 while (c.hasNext()) {
			 Collectable collectable = c.next();
			 
			 if (this.intersects(collectable)) {
				 collectable.Action(this);
				 collectable.remove = true;
			 }
		 }
		 
		 // triggerBox interaction
		 ListIterator<TriggerBox> tb = triggerBoxes.listIterator();
		 while(tb.hasNext()) {
			 TriggerBox triggerBox = tb.next();
			 
			 if (triggerBox.getType() == triggerBox.CONTAIN) {
				 if (triggerBox.contains(this)) {
					 triggerBox.setTriggered(true);
				 }
				 else
					 triggerBox.setTriggered(false);
			 }
			 else {
				 if (triggerBox.intersects(this)) {
					 triggerBox.setTriggered(true);
				 }
				 else 
					 triggerBox.setTriggered(false);
			 }
		 }
		 
		 // map obstacle interaction
		 ListIterator<MapObstacle> m = mapObstacles.listIterator();
		 while(m.hasNext()) {
			 MapObstacle currObject = m.next();
			 
			 if (this.intersects(currObject)) {
				 currObject.interaction(this);
			 }
		 }
	}
	
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		checkEntityInteraction(); 
		
		// load magAttack projectile
		if (magAttacking && currentAction != Actions.MAGATTACKING && canMagattack) {
			MagaProjectile projectile = new MagaProjectile(tileMap, facingRight);
			projectile.setPosition(x, y);
			magaProjectiles.add(projectile);
		}
		
		// update magaProjectiles
		ListIterator<MagaProjectile> projectile = magaProjectiles.listIterator();
		while(projectile.hasNext()) {
						
			MagaProjectile p = projectile.next();
			
			if (!p.shouldRemove()) {
				p.update();
				
				canMagattack = false;
			}
			else {
				numMagaProjectiles--;
				projectile.remove();
				
				if (numMagaProjectiles < 1) {
					canMagattack = false;
					spriteIndex = 0;
					currentAction = Actions.NONE;
				}
				else
					canMagattack = true;
			}
		}
		
		if(flinching) {
			flinchCount++;
			if (flinchCount > 80) 
				flinching = false;
		}
		
			// set animations
	    if (magAttacking) {
			if (currentAction != Actions.MAGATTACKING) {
				currentAction = Actions.MAGATTACKING;
//					previousAction = MAGATTACKING;
				setAnimation(Actions.MAGATTACKING);
			}
		}
	    else if (dy > 0) {
			if (currentAction != Actions.FALLING) {	
				currentAction = Actions.FALLING;
//				previousAction = FALLING;
				setAnimation(Actions.FALLING);
			}
		}
		
		else if (dy < 0) {
			if(currentAction != Actions.JUMPING) {
				currentAction = Actions.JUMPING;
//				previousAction = JUMPING;
				setAnimation(Actions.JUMPING);
			}
		}
		else if (runLeft || runRight) {
			if (currentAction != Actions.RUNNING) {
				currentAction = Actions.RUNNING;
//				previousAction = RUNNING;
				setAnimation(Actions.RUNNING);
			}
		}
		else if (walkLeft || walkRight) {
			if (currentAction != Actions.WALKING ) {
				currentAction = Actions.WALKING;
//				previousAction = WALKING;
				setAnimation(Actions.WALKING);
			}
		}
		
		else {
//			if (animation.getCount() == 10) {
//				if (currentAction != Actions.SMOKING) {
//					currentAction = Actions.SMOKING;
//					setAnimation(Actions.SMOKING);
//					
//					animation.setCount(10);
//				}
//			}
			
			if (currentAction != Actions.IDLE) {
				currentAction = Actions.IDLE;
//				previousAction = Actions.IDLE;
				setAnimation(Actions.IDLE);
			}
		}
			
		// update animation
		animation.update();
		
		// set direction
		if(walkRight || runRight) facingRight = true;
		if(walkLeft || runLeft) facingRight = false;
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw player
		if (flinching) {
			if (flinchCount % 10 < 5) return;
		}
		super.draw(g);
		
		// draw MagaProjectiles
		for (MagaProjectile projectile: magaProjectiles) {
			projectile.draw(g);
		}
	}

	@Override
	public void collisionEvent(MapObject m) {
		// TODO Auto-generated method stub
		
	}
}
