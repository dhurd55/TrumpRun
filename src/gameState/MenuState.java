package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import handlers.Keys;
import tileMap.Background;

public class MenuState extends GameState {
	
	private Background bg;
	private Background bg1;
	
	private int currentChoice = 0;
	private String[] options = {"Start", "Help", "Quit"};
	
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			
			bg = new Background("/backgrounds/levelone-sky.png", 0);
			bg1 = new Background("/backgrounds/clouds.png", 0, 1);
			bg1.setVector(.5,  0);
			bg1.setPosition(0, 50);
			
			font = new Font("Arial", Font.PLAIN, 12);
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		handleInput();
		bg.update();
		bg1.update();
	}

	@Override
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		bg1.draw(g);
		
		// draw options
		for(int i = 0; i < options.length; i++) {
			
			if (i == currentChoice) {
				g.setColor(Color.RED);
			}
			else
				g.setColor(Color.BLACK);
			
			g.drawString(options[i], 145, 140 + i * 15);

		}	
		
	}
	
	private void select() {
		
		if (currentChoice == 0) {
			//TODO START
			gsm.setState(GameStates.LEVELONESTATE.val);
		}
		else if (currentChoice == 1) {
			//TODO HELP
		}
		else if (currentChoice == 2){
			System.exit(0);
		}
	}
	
	public void handleInput() {
		if (Keys.isPressed(Keys.ENTER)) {
			select();
		}
		else if (Keys.isPressed(Keys.DOWN)) {
			currentChoice++;
			if(currentChoice > 2) { currentChoice = 0; }
		}
		else if (Keys.isPressed(Keys.UP)) {
			currentChoice --;
			if(currentChoice < 0) { currentChoice = 2; }
		}
	}
}
