package com.samuel.javaframework.objects;

import java.awt.Graphics;
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
	
	public Block(float x, float y, float scaleX, float scaleY, ObjectId id) 
	{
		super(x, y, scaleX, scaleY, id);
		
		lavaAnim = new Animation(50, texture.blocks[2], texture.blocks[3], texture.blocks[4], texture.blocks[5], texture.blocks[6]);
	}
	
	public void tick(LinkedList<GameObject> object) 
	{
		if(id == ObjectId.Lava && lavaAnim != null)
			lavaAnim.runAnimation();
	}

	public void render(Graphics g) 
	{
		if(id == ObjectId.Dirt)
			g.drawImage(texture.blocks[2], (int)x, (int)y, (int)scaleX, (int)scaleY, null);
		if(id == ObjectId.Stone)
			g.drawImage(texture.blocks[0], (int)x, (int)y, (int)scaleX, (int)scaleY, null);
		if(id == ObjectId.WalkStone)
			g.drawImage(texture.blocks[1], (int)x, (int)y, (int)scaleX, (int)scaleY, null);
		if(id == ObjectId.Lava)
			lavaAnim.drawAnimation(g, (int)x, (int)y);
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
