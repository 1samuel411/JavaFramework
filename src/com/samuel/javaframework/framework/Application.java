package com.samuel.javaframework.framework;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.samuel.javaframework.objects.Block;
import com.samuel.javaframework.objects.Player;
import com.samuel.javaframework.window.Game;
import com.samuel.javaframework.window.ObjectHandler;

public class Application 
{
	
	private BufferedImage level = null, background = null;
	private ObjectHandler handler;
	
	public Application(ObjectHandler handler)
	{
		this.handler = handler;
	}
	
	public void render(Graphics g)
	{
		if(background != null)
		{
			for(int x = 0; x < (Game.WIDTH/2) * 5; x += (Game.WIDTH/2))
				for(int y = 0; y < (Game.HEIGHT/2) * 3; y += (Game.HEIGHT/2))
					g.drawImage(background, x, y, (int)Game.WIDTH/2, (int)Game.HEIGHT/2, null);
		}
	}
	
	public void reloadLevel()
	{
		handler.objects.clear();
		
		int imageWidth = this.level.getWidth();
		int imageHeight = this.level.getHeight();
		
		for(int x = 0; x < imageWidth; x++)
		{
			for(int y = 0; y < imageHeight; y++)
			{
				int pixel = this.level.getRGB(x, y);
				int r = (pixel >> 16) & 0xff;
				int g = (pixel >> 8) & 0xff;
				int b = (pixel) & 0xff;
				
				// stone
				if(r == 255 && g == 255 && b == 255)
					handler.addObject(new Block(x * 32, y * 32, 32, 32, ObjectId.Stone));
				if(r == 121 && g == 121 && b == 121)
					handler.addObject(new Block(x * 32, y * 32, 32, 32, ObjectId.WalkStone));
				if(r == 255 && g == 0 && b == 0)
					handler.addObject(new Block(x * 32, y * 32, 32, 32, ObjectId.Lava));
				
				// player
				if(r == 0 && g == 0 && b == 255)
					createPlayer(x * 32, y * 32, 38, 38);
			}
		}
	}
	
	public void loadLevel(String level)
	{
		
		handler.objects.clear();
		
		ImageLoader loader = new ImageLoader();
		this.level = loader.loadImage("/levels/" + level + ".png");
		
		int imageWidth = this.level.getWidth();
		int imageHeight = this.level.getHeight();
		
		for(int x = 0; x < imageWidth; x++)
		{
			for(int y = 0; y < imageHeight; y++)
			{
				int pixel = this.level.getRGB(x, y);
				int r = (pixel >> 16) & 0xff;
				int g = (pixel >> 8) & 0xff;
				int b = (pixel) & 0xff;
				
				// stone
				if(r == 255 && g == 255 && b == 255)
					handler.addObject(new Block(x * 32, y * 32, 32, 32, ObjectId.Stone));
				if(r == 121 && g == 121 && b == 121)
					handler.addObject(new Block(x * 32, y * 32, 32, 32, ObjectId.WalkStone));
				if(r == 255 && g == 0 && b == 0)
					handler.addObject(new Block(x * 32, y * 32, 32, 32, ObjectId.Lava));
				
				// player
				if(r == 0 && g == 0 && b == 255)
					createPlayer(x * 32, y * 32, 38, 38);
			}
		}
	}
	
	public void loadBackground(String background)
	{
		ImageLoader loader = new ImageLoader();
		this.background = loader.loadImage("/levels/"+ background + ".png");
	}
	
	public void createPlayer(int x, int y, int scaleX, int scaleY)
	{
		Player player = new Player(x, y, scaleX, scaleY, ObjectId.Player, handler);
		player.setGrounded(false);
		handler.addObject(player);
	}
}
