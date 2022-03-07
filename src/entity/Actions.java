package entity;

public enum Actions {
	NONE(-1, -1, 32), 
	IDLE(0, 400, 32), 
	WALKING(1, 100, 32), 
	SMOKING(2, 100, 32), 
	RUNNING(3, 75, 32), 
	JUMPING(4, -1, 32), 
	FALLING(5, 1200, 32), 
	MAGATTACKING(6, -1, 32), 
	ATTACKING(7, -1, 32);
	
	private final int index;
	private final int delay; 
	private final int width;
	
	private Actions(int index, int delay, int width) {
		this.index = index;
		this.delay = delay;
		this.width = width;
	}
	
	public int index() { return this.index; }
	public int delay() { return this.delay; }
	public int width() { return this.width; }

}
