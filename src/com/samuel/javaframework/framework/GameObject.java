package com.samuel.javaframework.framework;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public abstract class GameObject extends Base
{
	protected Vector2 position;
	protected Vector2 scale;
	protected ObjectId id;
	protected boolean trigger;
	
	public GameObject(Vector2 position, Vector2 scale, boolean trigger, ObjectId id)
	{
		this.position = position;
		this.scale = scale;
		this.trigger = trigger;
		this.id = id;
	}
	
	public abstract void tick(LinkedList<GameObject> object);	
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);
	public void death()
	{
		System.out.println("should not show");
	}
	
	public Vector2 getPosition()
	{
		return position;
	}
	public void setPosition(Vector2 position)
	{
		this.position = position;
	}
	
	public Vector2 getScale()
	{
		return scale;
	}
	public void setScale(Vector2 scale)
	{
		this.scale = scale;
	}
	
	public ObjectId getId()
	{
		return id;
	}
	
	public boolean getTrigger()
	{
		return trigger;
	}
}
