package entity;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import handlers.Content;

public class Hud {
	
	ArrayList<BufferedImage> hud;
	ArrayList<BufferedImage> nums;
	
	Font font;
	
	Player p;
	
	int movement;
	private Boolean remove;
	
	public final int HAT = 0;
	public final int HEART = 1;
	public final int BALLOT = 2;
	public final int MONEY = 3;
	public final int DOLLARSIGN = 4;
	
	public Hud(Player p) {
		hud = Content.hud.get(0);
		nums = Content.numbers.get(0);
		
		font = new Font("Arial", Font.PLAIN, 10);
		
		this.p = p;
		
		remove = false;
	}
	
	public Boolean shouldRemove() { return remove; }
	public void setRemove(Boolean b) { remove = b; }
	
	public void update() {
	}
	
	public void draw(Graphics2D g) {
		
		// hearts
		for (int i = 0; i < p.getHealth(); i ++) {
			g.drawImage(hud.get(HEART), 8 + i*16, 3, null);
		}
		
		// magaHats
		for (int i = 0; i < p.getNumMagaProjectiles(); i++) {
			g.drawImage(hud.get(HAT), 8 + i * 16, 19, null);
		}
		
		// ballots and numBallots
		g.drawImage(hud.get(BALLOT), 120, 3, null);
		
		g.setFont(font);
		g.drawString("x " + p.getBallots(), 138, 17);
		
		// dollar sign
		g.drawImage(hud.get(DOLLARSIGN), 165, 4, null);
		g.drawString(" = ", 178, 18);
		
		// displayNumbers
		String s = String.valueOf(p.getMoney());
		
		for(int i = 0; i < s.length(); i++ ) {
			int index = (int)s.charAt(i)-48;
//			g.drawImage(nums[index][0], i* 8 + p.getx()+8 +(int)p.xmap, p.gety() + (int)p.ymap, null);
			g.drawImage(nums.get(index), i * 8 + 190, 10, null);
		}
	}
	
}
