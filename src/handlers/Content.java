package handlers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;;

public class Content {
	
	public static ArrayList<ArrayList<BufferedImage>> numbers = load("/sprites/objects/Numbers.png", 8, 8);
	public static ArrayList<ArrayList<BufferedImage>> magaProjectileSprites = load("/sprites/objects/MagaProjectile.png", 16, 16);
	public static ArrayList<ArrayList<BufferedImage>> hud = load("/sprites/objects/HUD-sheet2.png", 16, 16);
	public static ArrayList<ArrayList<BufferedImage>> moneySprites = load("/sprites/objects/money_sprite-sheet.png", 16, 16);
	public static ArrayList<ArrayList<BufferedImage>> ballotSprites = load("/sprites/objects/BALLOT_SPRITE-SHEET.png", 16, 16);
	public static ArrayList<ArrayList<BufferedImage>> magaHatSprites = load("/sprites/objects/magaHat.png", 16, 16);
	public static ArrayList<ArrayList<BufferedImage>> antifaSprites = load("/sprites/enemies/antifa_fullSheet.png", 32, 32);
	public static ArrayList<ArrayList<BufferedImage>> waterSprites = load("/sprites/objects/water.png", 32, 32);
	public static ArrayList<ArrayList<BufferedImage>> elevatorSprites = load("/sprites/objects/elevatorTile.png", 32, 32);
	
	public static ArrayList<ArrayList<BufferedImage>> load(String s, int tileWidth, int tileHeight) {
		
		ArrayList<ArrayList<BufferedImage>> spriteSets = new ArrayList<ArrayList<BufferedImage>>();
		
		try {
			BufferedImage loadedSheet = ImageIO.read(Content.class.getResourceAsStream(s));
		
			int spritesAcross = loadedSheet.getWidth() / tileWidth;
			int spritesDown = loadedSheet.getHeight() / tileHeight;
		
			
			for (int i = 0; i < spritesDown; i++) {
				ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
				
				for (int j = 0; j < spritesAcross; j++) {
					BufferedImage sprite = loadedSheet.getSubimage(j * tileWidth, i * tileHeight, tileWidth, tileHeight);
					if (isTransparent(sprite)) continue;
					
					sprites.add(sprite);
				}
				
				spriteSets.add(sprites);
			}
			return spriteSets;
		} catch (Exception e) {
			System.out.println("error loading Graphics");
			e.printStackTrace();
			System.exit(0);
			
		}
		return null;
	}
	
	public static boolean isTransparent(BufferedImage image) {
		for(int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				int pixel = image.getRGB(i, j);
				
				// if transparent continue else return false
				if ((pixel >> 24) == 0x00 ) {
					continue;
				}
				else
					return false;
			}
		}
		// if all pixels are transparent return true
		return true;
	}
}
