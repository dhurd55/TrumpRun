package tileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Background {
	
	private BufferedImage image;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private int width;
	private int height;
	
	private double moveScale;
	
	private double xScale;
	private double yScale;
	
	private double yAnchor;
	private double xAnchor;
	
	
	public Background(String s) {
		this(s, 0.1);
	}
	
	public Background(String s, double d) {
		
		this(s, d, d);
		
	}
	
	public Background(String s, double d1, double d2) {
		
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream(s));
			
			width = image.getWidth();
			height = image.getHeight();
			
			xScale = d1;
			yScale = d2;
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setXAnchor(double xAnchor) {
		this.xAnchor = xAnchor;
	}
	
	public void setYAnchor(double yAnchor) {
		this.yAnchor = yAnchor;
	}
	
	public void setScale(double xScale, double yScale) {
		this.xScale = xScale;
		this.yScale = yScale;
	}
	
	public void setPosition(double x, double y) {
		
		this.x = (x * xScale) % GamePanel.WIDTH;
		this.y = ((y * yScale ) % GamePanel.HEIGHT) + yAnchor;		
		
	}
	
	public void setVector(double dx, double dy) {
		
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		
		if (x > width) x = 0;
		if (y > height) y = 0;
		
		x += dx;
		y += dy;
	}
	
	public void draw(Graphics2D g) {
		
		
		g.drawImage(image, (int)x, (int)y, null);
		
		if (x < 0) {
			g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
		}
		if (x > 0) {
			g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
		}
//		if (y < 0) {
//			g.drawImage(image, (int)x, (int)y + GamePanel.HEIGHT, null);
//		}
//		if (y > 0) {
//			g.drawImage(image, (int)x, (int)y - GamePanel.HEIGHT, null);
//		}
		
	}

}
