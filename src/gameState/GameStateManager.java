package gameState;

import java.util.ArrayList;

import gameState.levels.LevelOneState;
import handlers.Keys;

public class GameStateManager {
	
	private ArrayList<GameState> gameStates;
	private int currentState;
	
	public GameStateManager() {
		
		gameStates = new ArrayList<GameState>();
		
		
		currentState = GameStates.MENUSTATE.val;
		
		gameStates.add(new MenuState(this));
		gameStates.add(new LevelOneState(this));
		
	}

	public void setState(int state) {
		
		currentState = state;
		gameStates.get(currentState).init();
		
	}
	
	public void update() {
		
		gameStates.get(currentState).update();
		
	}
	
	public void draw(java.awt.Graphics2D g) {
		
		gameStates.get(currentState).draw(g);
		
	}

}
