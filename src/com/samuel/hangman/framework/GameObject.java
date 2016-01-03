package com.samuel.hangman.framework;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public abstract class GameObject 
{
	protected float x, y;
	protected ObjectId id;
	protected float velocityX = 0, velocityY = 0;
	
	protected boolean grounded;
	protected final float gravity = 3.91f;
	protected float max_speed = 22;
	
	public GameObject(float x, float y, ObjectId id)
	{
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void tick(LinkedList<GameObject> object);	
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);
	
	public float getX()
	{
		return x;
	}
	public float getY()
	{
		return y;
	}
	public void setX(float x)
	{
		this.x = x;
	}
	public void setY(float y)
	{
		this.y = y;
	}
	
	public float getVelocityX()
	{
		return velocityX;
	}
	public float getVelocityY()
	{
		return velocityY;
	}
	public void setVelocityX(float velocityX)
	{
		this.velocityX = velocityX;
	}
	public void setVelocityY(float velocityY)
	{
		this.velocityY = velocityY;
	}
	public boolean getGrounded() 
	{
		return grounded;
	}
	public void setGrounded(boolean kinematic) 
	{
		this.grounded = kinematic;
	}
	public float getGravity() 
	{
		return gravity;
	}
	
	public float getMax_speed() {
		return max_speed;
	}

	public void setMax_speed(float max_speed) {
		this.max_speed = max_speed;
	}
	
	public ObjectId getId()
	{
		return id;
	}
}
