package com.samuel.javaframework.framework;

import java.awt.image.BufferedImage;

public class Texture 
{
	SpriteSheet blockSheet, playerSheet, enemySheet;
	private BufferedImage block_sheet = null;
	private BufferedImage player_sheet = null;
	private BufferedImage enemy_sheet = null;
	
	public BufferedImage[] blocks = new BufferedImage[7];
	public BufferedImage[] player = new BufferedImage[11];
	public BufferedImage[] enemy = new BufferedImage[4];
	
	public Texture()
	{
		ImageLoader imageLoader = new ImageLoader();
		block_sheet = imageLoader.loadImage("/sheets/block_sheet.png");
		player_sheet = imageLoader.loadImage("/sheets/player_sheet.png");
		enemy_sheet = imageLoader.loadImage("/sheets/enemy_1_sheet.png");
		
		blockSheet = new SpriteSheet(block_sheet);
		playerSheet = new SpriteSheet(player_sheet);
		enemySheet = new SpriteSheet(enemy_sheet);
		
		getTextures();
	}
	
	private void getTextures()
	{
		// stone
		blocks[0] = blockSheet.getImage(1, 1, 32, 32);
		// stone floor
		blocks[1] = blockSheet.getImage(2, 1, 32, 32);
		// aniamted lava
		blocks[2] = blockSheet.getImage(14, 15, 32, 32);
		blocks[3] = blockSheet.getImage(15, 15, 32, 32);
		blocks[4] = blockSheet.getImage(16, 15, 32, 32);
		blocks[5] = blockSheet.getImage(15, 16, 32, 32);
		blocks[6] = blockSheet.getImage(16, 16, 32, 32);
		
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
		// player jumping
		player[9] = playerSheet.getImage(1, 3, 40, 40);
		// player falling
		player[10] = playerSheet.getImage(2, 3, 40, 40);
		
		// enemy1 idle anim
		enemy[0] = enemySheet.getImage(1, 1, 72, 73);
		// enemy1 walking anim
		enemy[1] = enemySheet.getImage(2, 1, 72, 73);
		enemy[2] = enemySheet.getImage(3, 1, 72, 73);
		enemy[3] = enemySheet.getImage(4, 1, 72, 73);
	}
}
