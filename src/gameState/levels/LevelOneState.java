package gameState.levels;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ListIterator;

import entity.Collectable;
import entity.MovingTile;
import entity.Enemy;
import entity.Hud;
import entity.MagaProjectile;
import entity.MapObject;
import entity.MapObstacle;
import entity.Player;
import entity.SharpTile;
import entity.TriggerBox;
import entity.Water;
import entity.Enemies.AntifaEnemy;
import entity.collectables.Ballot;
import entity.collectables.MagaHat;
import entity.collectables.Money;
import gameState.GameState;
import gameState.GameStateManager;
import handlers.Keys;
import main.GamePanel;
import tileMap.Background;
import tileMap.TileMap;

public class LevelOneState extends GameState {
	
	private TileMap tileMap;
	
	// backgrounds
	private Background bgSky;
	private Background bgWater;
	private Background bgClouds;
	private Background bgCloseBuildings;
	
	// map objects
	private Player player;
	private Hud hud;
	private MovingTile elev;
	private ArrayList<Enemy> enemies;
	private ArrayList<Collectable> collectables;
	private ArrayList<MapObstacle> mapObstacles;
	private ArrayList<TriggerBox> triggerBoxes;
	
	public LevelOneState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	@Override
	public void init() {
		// tilemap
		tileMap = new TileMap(32);
		tileMap.loadTiles("/tileSets/tileSet.png");
		tileMap.setTween(1);
		tileMap.loadMap("/maps/level-1.txt");
		tileMap.setPosition(0, 0);
		
		// map objects
		player = new Player(tileMap);
		player.setPosition(48 , 592);
		
		hud = new Hud(player);
		
		// enemies
		enemies = new ArrayList<Enemy>();
		AntifaEnemy ae = new AntifaEnemy(tileMap);
		ae.setPosition(250, 592);
		enemies.add(ae);
		
		// collectables
		collectables = new ArrayList<Collectable>();
		
		Ballot b;
		b = new Ballot(tileMap, player);
		b.setPosition(80, 153);
		collectables.add(b);
		
		MagaHat mh;
		mh = new MagaHat(tileMap, player);
		mh.setPosition(275, 344);
		collectables.add(mh);
		
		mh = new MagaHat(tileMap, player);
		mh.setPosition(player.getx() + 50, player.gety());
		collectables.add(mh);
		
		Money m;
		m = new Money(tileMap, player);
		m.setPosition(100, 600);
		collectables.add(m);
		
		// obstacles	
		mapObstacles = new ArrayList<MapObstacle>();
		triggerBoxes = new ArrayList<TriggerBox>();
		TriggerBox tb;
		
		elev = new MovingTile(tileMap, player);
		elev.setPosition(176, 592);
		
		tb = new TriggerBox(elev, 52, 40);
		tb.setyOffset(-20);
		tb.setPosition(176, 592);
		tb.setType(tb.CONTAIN);
		triggerBoxes.add(tb);
		elev.init(tb);
		
		SharpTile st;
		st = new SharpTile(tileMap);
		st.setPosition(271, 500);
		mapObstacles.add(st);
		
		tb = new TriggerBox(st, 10, 64);
		tb.setPosition(271, 591);
		triggerBoxes.add(tb);
			
		//init player
		player.init(enemies, collectables, mapObstacles, triggerBoxes);
		
		// backgrounds
		bgSky = new Background("/backgrounds/levelone-background.png", 0);
		bgSky.setPosition(0, 0);
		
		bgClouds = new Background("/backgrounds/levelone-clouds.png");
		bgClouds.setVector(.2, 0);
		
		bgWater = new Background("/backgrounds/levelone-water.png");
		bgWater.setVector(.03, 0);
		
		bgCloseBuildings = new Background("/backgrounds/levelone-closebuildings.png", 0, .3);
		bgCloseBuildings.setYAnchor((tileMap.getHeight() - GamePanel.HEIGHT)*.3);
	}

	@Override
	public void update() {
		
		//handle key input
		handleInput();
		
		// update map objects
		player.update();
		hud.update();
		
		// update enemies
		ListIterator<Enemy> e = enemies.listIterator();
		while(e.hasNext()) {
			Enemy enemy = e.next();
			
			enemy.update();
			
			if (enemy.shouldRemove()) {
				e.remove();
			}
		}
		
		// update collectables
		ListIterator<Collectable> collectableIterator = collectables.listIterator();
		while(collectableIterator.hasNext()) {
			
			Collectable collectable = collectableIterator.next();
			if (collectable.shouldRemove()) {
				collectableIterator.remove();
			}
			else
				collectable.update();
		}
		
		// update mapObsticles (water, spiked tiles, etc.)
		ListIterator<MapObstacle> m = mapObstacles.listIterator();
		while(m.hasNext()) {
			m.next().update();
		}
		
		// update moving tiles
		elev.update();
		
		// update backgrounds
		bgCloseBuildings.setPosition(tileMap.getx(), tileMap.gety());
		bgClouds.update();
		bgWater.update();
		
		//set tilemap position
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety());

	}

	@Override
	public void draw(Graphics2D g) {
		
		// draw background screen 
		bgSky.draw(g);
		bgClouds.draw(g);
		bgWater.draw(g);
		bgCloseBuildings.draw(g);
		
		// draw tileMap
		tileMap.draw(g);
		
		// draw map objects
		ListIterator<Collectable> collectableIterator = collectables.listIterator();
		while(collectableIterator.hasNext()) {
			collectableIterator.next().draw(g);
		}
			
		// draw enemies
		ListIterator<Enemy> e = enemies.listIterator();
		while(e.hasNext()) {
			e.next().draw(g);
		}
		
		// draw moving tile
		elev.draw(g);
		
		//draw map obstacle
		ListIterator<MapObstacle> m = mapObstacles.listIterator();
		while(m.hasNext()) {
			
			m.next().draw(g);
		}
		
//		// triggerbox collision
//		ListIterator<TriggerBox> tb = triggerBoxes.listIterator();
//		while(tb.hasNext()) {
//			tb.next().drawCollisionBox(g, tileMap);
//		}
		
		// player and hud
		player.draw(g);
		hud.draw(g);
//		player.drawCollisionBox(g);
	}

	public void handleInput() {
		
		player.setRunLeft(Keys.keyState[Keys.LEFT] && Keys.keyState[Keys.UP]);
		player.setRunRight(Keys.keyState[Keys.RIGHT] && Keys.keyState[Keys.UP]);
		player.setUp(Keys.keyState[Keys.UP]);
		player.setWalkLeft(Keys.keyState[Keys.LEFT]);
		player.setDown(Keys.keyState[Keys.DOWN]);
		player.setWalkRight(Keys.keyState[Keys.RIGHT]);
		player.setJumping(Keys.keyState[Keys.BUTTON1]);
		player.setCanJump(Keys.keyState[Keys.BUTTON1]);
		player.setMagAttacking(Keys.isPressed(Keys.BUTTON2));
//		player.setMagAttacking(Keys.keyState[Keys.BUTTON2]);
		
	}

}
