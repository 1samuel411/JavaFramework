package com.samuel.javaframework.window;

import com.samuel.javaframework.framework.GameObject;
import com.samuel.javaframework.framework.ObjectId;

public class Camera 
{
	private float x, y;
	private GameObject target;
	
	public Camera(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void tick()
	{
		target = ObjectHandler.instance.findObject(ObjectId.Player);
		x = -target.getX() + (Game.WIDTH/2);
	}

	public float getX() 
	{
		return x;
	}

	public void setX(float x) 
	{
		this.x = x;
	}

	public float getY() 
	{
		return y;
	}

	public void setY(float y) 
	{
		this.y = y;
	}
}
