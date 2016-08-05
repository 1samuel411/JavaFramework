package com.samuel.javaframework.framework;

public class Base 
{
	public static class Vector2
	{
		public double x;
		public double y;
		public Vector2()
		{
			this.x = 0;
			this.y = 0;
		}
		
		public static Vector2 zero = new Vector2(0, 0);
		
		public Vector2(double x)
		{
			this.x = x;
			this.y = 0;
		}
		
		public Vector2(double x, double y)
		{
			this.x = x;
			this.y = y;
		}
		
		public Vector2 Multiply(double amount)
		{
			return new Vector2(x * amount, y * amount);
		}
		
		public Vector2 Multiply(Vector2 amount)
		{
			return new Vector2(x * amount.x, y * amount.y);
		}
		
		public Vector2 Add(double amount)
		{
			return new Vector2(x + amount, y + amount);
		}
		
		public Vector2 Add(Vector2 amount)
		{
			return new Vector2(x + amount.x, y + amount.y);
		}
		
		public Vector2 Subtract(double amount)
		{
			return new Vector2(x - amount, y - amount);
		}
		
		public Vector2 Subtract(Vector2 amount)
		{
			return new Vector2(x - amount.x, y - amount.y);
		}
		
		public Vector2 Divide(double amount)
		{
			return new Vector2(x / amount, y / amount);
		}
		
		public Vector2 Divide(Vector2 amount)
		{
			return new Vector2(x / amount.x, y / amount.y);
		}
	}
	
	public class Collision 
	{
		public GameObject object;
		public Vector2 position;
		public Direction direction;
		public Collision (GameObject object, Vector2 position, Direction direction)
		{
			this.object = object;
			this.position = position;
			this.direction = direction;
		}
	}
	
	public class RaycastHit
	{
		public Collision collision;
	}
	
	public void onCollisionEnter(Collision collision)
	{
		// should never be printed out since the onCollisionEnter method on the class inheriting this class will be called.
		
	}
	
	public void onTriggerEnter(Collision collision)
	{
		// should never be printed out since the onCollisionEnter method on the class inheriting this class will be called.
		
	}
	
	public static enum Direction
	{
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
}
