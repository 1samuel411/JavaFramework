package com.samuel.javaframework.window;

import java.awt.Graphics;
import java.util.LinkedList;

import com.samuel.javaframework.framework.GameObject;
import com.samuel.javaframework.framework.ObjectId;
import com.samuel.javaframework.objects.Block;

public class ObjectHandler 
{
	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	
	private GameObject tempObject;
	
	public static ObjectHandler instance;
	
	public ObjectHandler()
	{
		instance = this;
	}
	
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
	
	public GameObject findObject(ObjectId idToFind)
	{
		GameObject objectFound = null;
		for(int i = 0; i < objects.size(); i++)
		{
			if(objects.get(i).getId() == idToFind)
			{
				objectFound = objects.get(i);
			}
		}
		return objectFound;
	}
	
	public LinkedList<GameObject> findObjects(ObjectId idToFind)
	{
		LinkedList<GameObject> objectsFound = new LinkedList<GameObject>();
		for(int i = 0; i < objects.size(); i++)
		{
			if(objects.get(i).getId() == idToFind)
			{
				objectsFound.add(objects.get(i));
			}
		}
		return objectsFound;
	}
}
