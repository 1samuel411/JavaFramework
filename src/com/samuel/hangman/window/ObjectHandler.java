package com.samuel.hangman.window;

import java.awt.Graphics;
import java.util.LinkedList;

import com.samuel.hangman.framework.GameObject;
import com.samuel.hangman.framework.ObjectId;
import com.samuel.hangman.objects.Block;

public class ObjectHandler 
{
	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	
	private GameObject tempObject;
	
	public void tick()
	{
		for(int i = 0; i < objects.size(); i++)
		{
			tempObject = objects.get(i);
			tempObject.tick(objects);
		}
	}
	
	public void render(Graphics g)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			tempObject = objects.get(i);
			tempObject.render(g);
		}
	}
	
	public void addObject(GameObject object)
	{
		this.objects.add(object);
	}
	
	public void removeObject(GameObject object)
	{
		this.objects.remove(object);
	}
	
	public void createLevel()
	{
		for(int i = 0; i < 60; i ++)
		{
			addObject(new Block(i * 32, Game.HEIGHT-32, ObjectId.Block));
		}
		
		for(int i = 0; i < 20; i++)
		{
			addObject(new Block(i * 32, Game.HEIGHT-(32*5), ObjectId.Block));
		}
		
		for(int i = 0; i < 60; i++)
		{
			addObject(new Block(0, i * 32, ObjectId.Block));
		}
		
		for(int i = 0; i < 60; i++)
		{
			addObject(new Block(Game.WIDTH - 32, i * 32, ObjectId.Block));
		}
	}
}
