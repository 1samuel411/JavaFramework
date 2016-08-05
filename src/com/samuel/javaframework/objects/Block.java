package com.samuel.javaframework.objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import com.samuel.javaframework.framework.Animation;
import com.samuel.javaframework.framework.GameObject;
import com.samuel.javaframework.framework.ObjectId;
import com.samuel.javaframework.framework.Texture;
import com.samuel.javaframework.window.Game;

public class Block extends GameObject
{
	Texture texture = Game.getTextureInstance();
	private Animation lavaAnim;
	
	public Block(Vector2 position, Vector2 scale, boolean trigger, ObjectId id) 
	{
		super(position, scale, trigger, id);
		
		if(id == ObjectId.Lava)
			lavaAnim = new Animation(25, texture.blocks[2], texture.blocks[3], texture.blocks[4], texture.blocks[5], texture.blocks[6]);
	}
	
	public void tick(LinkedList<GameObject> object) 
	{
		if(id == ObjectId.Lava)
			lavaAnim.runAnimation();
	}

	public void render(Graphics g) 
	{
		if(id == ObjectId.Dirt)
			drawImage(texture.blocks[2], g);
		if(id == ObjectId.Stone)
			drawImage(texture.blocks[0], g);
		if(id == ObjectId.WalkStone)
			drawImage(texture.blocks[1], g);
		if(id == ObjectId.Lava)
			lavaAnim.drawAnimation(g, (int)position.x, (int)position.y);
		if(id == ObjectId.EnemyPoint)
			return;
	}

	public Rectangle getBounds() 
	{
		return new Rectangle((int)position.x, (int)position.y, 32, 32);
	}
	
	public void keyPressed(KeyEvent e) 
	{
		
	}

	public void keyReleased(KeyEvent e) 
	{
		
	}
	
	public void drawImage(Image image, Graphics g)
	{
		g.drawImage(image, (int)position.x, (int)position.y, (int)scale.x, (int)scale.y, null);
	}
}
