package com.samuel.javaframework.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import com.samuel.javaframework.framework.GameObject;
import com.samuel.javaframework.framework.ObjectId;
import com.samuel.javaframework.framework.Texture;
import com.samuel.javaframework.window.Game;

public class Block extends GameObject
{
	Texture texture = Game.getTextureInstance();
	
	public Block(float x, float y, ObjectId id) 
	{
		super(x, y, id);
		
	}
	
	public void tick(LinkedList<GameObject> object) 
	{
		
	}

	public void render(Graphics g) 
	{
		if(id == ObjectId.Dirt)
			g.drawImage(texture.blocks[0], (int)x, (int)y, null);
		if(id == ObjectId.Grass)
			g.drawImage(texture.blocks[1], (int)x, (int)y, null);
	}

	public Rectangle getBounds() 
	{
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	public void keyPressed(KeyEvent e) 
	{
		
	}

	public void keyReleased(KeyEvent e) 
	{
		
	}
}
