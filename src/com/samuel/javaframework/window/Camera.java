package com.samuel.javaframework.window;

import com.samuel.javaframework.framework.Base;
import com.samuel.javaframework.framework.GameObject;
import com.samuel.javaframework.framework.ObjectId;

public class Camera extends Base
{
	private Vector2 position;
	private GameObject target;
	
	public Camera(Vector2 position)
	{
		this.position = position;
	}
	
	public void tick()
	{
		target = ObjectHandler.instance.findObject(ObjectId.Player);
		position.x = -target.getPosition().x + (Game.WIDTH/2);
	}

	public double getX() 
	{
		return position.x;
	}

	public void setX(double x) 
	{
		position.x = x;
	}

	public double getY() 
	{
		return position.y;
	}

	public void setY(double y) 
	{
		position.y = y;
	}
}