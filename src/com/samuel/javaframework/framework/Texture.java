package com.samuel.javaframework.framework;

import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

public class Texture 
{
	SpriteSheet blockSheet, playerSheet;
	private BufferedImage block_sheet = null;
	private BufferedImage player_sheet = null;
	
	public BufferedImage[] blocks = new BufferedImage[2];
	public BufferedImage[] player = new BufferedImage[10];
	
	public Texture()
	{
		ImageLoader imageLoader = new ImageLoader();
		block_sheet = imageLoader.loadImage("/sheets/block_sheet.png");
		player_sheet = imageLoader.loadImage("/sheets/player_sheet.png");
		
		blockSheet = new SpriteSheet(block_sheet);
		playerSheet = new SpriteSheet(player_sheet);
		
		getTextures();
	}
	
	private void getTextures()
	{
		// dirt
		blocks[0] = blockSheet.getImage(3, 1, 32, 32);
		// grass
		blocks[1] = blockSheet.getImage(4, 1, 32, 32);
		
		// player idle anim
		player[0] = playerSheet.getImage(1, 1, 40, 40);
		player[1] = playerSheet.getImage(2, 1, 40, 40);
		player[2] = playerSheet.getImage(3, 1, 40, 40);
		// player walking anim
		player[3] = playerSheet.getImage(1, 2, 40, 40);
		player[4] = playerSheet.getImage(2, 2, 40, 40);
		player[5] = playerSheet.getImage(3, 2, 40, 40);
		player[6] = playerSheet.getImage(4, 2, 40, 40);
		player[7] = playerSheet.getImage(5, 2, 40, 40);
		player[8] = playerSheet.getImage(6, 2, 40, 40);
		player[9] = playerSheet.getImage(1, 3, 40, 40);
	}
}
