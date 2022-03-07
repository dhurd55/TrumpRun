package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
	
	private ArrayList<BufferedImage> frames;
	private int currentFrame;
	
	private long startTime;
	private long delay;
	
	private boolean playedOnce;
	private int playCount;
	
	public Animation() {
		
		playedOnce = false;
		playCount = 0;
	}
	
	public void setFrames(ArrayList<BufferedImage> arrayList) {
		
		this.frames = arrayList;
		currentFrame = 0;   
		startTime = System.nanoTime();
		playedOnce = false;
		playCount = 0;
		
	}
	
	public void setDelay(long d) { delay = d; }
	public void setFrame(int i) { currentFrame = i; }
	public void setCount(int i ) { playCount = i; }
	
	public void update() {
		
		if(delay == -1) return;
		
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if (elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if (currentFrame == frames.size()) {
			currentFrame = 0;
			playedOnce = true;
			playCount++;
		}
		
	}
	
	public int getFrame() { return currentFrame; }
	public BufferedImage getImage() { return frames.get(currentFrame); }
	public boolean hasPlayedOnce() { return playedOnce; }
	public int getCount() { return playCount; }

}
