package com.samuel.javaframework.framework;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.samuel.javaframework.objects.Block;
import com.samuel.javaframework.objects.Enemy;
import com.samuel.javaframework.objects.Player;
import com.samuel.javaframework.window.Game;
import com.samuel.javaframework.window.ObjectHandler;

public class Application extends Base
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
				
				Vector2 position = new Vector2(x, y);
				
				if(r == 255 && g == 255 && b == 255)
					addObject(position, false, ObjectId.Stone);
				if(r == 121 && g == 121 && b == 121)
					addObject(position, false, ObjectId.WalkStone);
				if(r == 255 && g == 0 && b == 0)
					addObject(position, false, ObjectId.Lava);
				if(r == 255 && g == 200 && b == 0)
					addEnemy(new Vector2(x * 32, y * 32), true, ObjectId.Enemy);
				if(r == 255 && g == 255 && b == 0)
					addObject(position, true, ObjectId.EnemyPoint);
				
				// player
				if(r == 0 && g == 0 && b == 255)
					createPlayer(new Vector2(x * 32, y * 32), false, new Vector2(38, 38));
			}
		}
	}
	
	public void addObject(Vector2 position, boolean trigger, ObjectId id)
	{
		handler.addObject(new Block(position.Multiply(32), new Vector2(32, 32), trigger, id));
	}
	
	public void addEnemy(Vector2 position, boolean trigger, ObjectId id)
	{
		handler.addObject(new Enemy(position, new Vector2(28, 28), trigger, id, handler));
	}
	
	public void loadLevel(String level)
	{
		ImageLoader loader = new ImageLoader();
		this.level = loader.loadImage("/levels/" + level + ".png");
		
		reloadLevel();
	}
	
	public void loadBackground(String background)
	{
		ImageLoader loader = new ImageLoader();
		this.background = loader.loadImage("/levels/"+ background + ".png");
	}
	
	public void createPlayer(Vector2 position, boolean trigger, Vector2 scale)
	{
		Player player = new Player(position, scale,trigger, ObjectId.Player, handler);
		handler.addObject(player);
	}
}
