package com.samuel.javaframework.framework;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

import com.samuel.javaframework.window.Game;
import com.samuel.javaframework.window.ObjectHandler;

public class Rigidbody extends Base
{
	public Vector2 velocity;
	
	public Vector2 size;
	
	public boolean grounded;
	public final double gravity = 1.51d;
	public double gravityScale;
	public final double max_speed = 22;
	
	public GameObject gameObject;
	
	public ObjectHandler handler;
	
	public LinkedList<ObjectId> ignoreList;
	
	public Rigidbody (GameObject gameObject, Vector2 size, double gravityScale, ObjectHandler handler) 
	{
		this.gameObject = gameObject;
		this.size = size;
		this.handler = handler;
		this.gravityScale = gravityScale;
		this.velocity = Vector2.zero;
		this.ignoreList = new LinkedList<ObjectId>();
	}
	
	public void setIgnoreList (LinkedList<ObjectId> ignoreList)
	{
		  this.ignoreList = ignoreList;
	}
	
	public void setGrounded (boolean grounded)
	{
		this.grounded = grounded;
	}
	
	public boolean getGrounded ()
	{
		return (this.grounded);
	}
	
	public void setVelocity (Vector2 velocity)
	{
		this.velocity = velocity;
	}
	
	public Vector2 getVelocity ()
	{
		return (this.velocity);
	}
	
	public void tick()
	{
		gameObject.setPosition(gameObject.getPosition().Add(velocity.Multiply(Game.DELTA)));
		
		collision();
		
		grounded = checkGrounded();
		
		if(grounded == false)
		{
			velocity.y += (gravity * gravityScale);
			
			if(velocity.y >= max_speed )
				velocity.y = max_speed;
		}
	}
	
	public AtomicReference<RaycastHit> groundedHit = new AtomicReference<RaycastHit>();
	public boolean checkGrounded()
	{
		groundedHit = new AtomicReference<RaycastHit>();
		if(raycast(new Vector2(gameObject.getPosition().x + 5, gameObject.getPosition().y + (size.y/2)), new Vector2(0, -1), size.y/2, groundedHit))
		{
			return true;
		}
		if(raycast(new Vector2(gameObject.getPosition().x + (size.x/2), gameObject.getPosition().y + (size.y/2)), new Vector2(0, -1), size.y/2, groundedHit))
		{
			return true;
		}
		if(raycast(new Vector2(gameObject.getPosition().x + (size.x) - 5, gameObject.getPosition().y + (size.y/2)), new Vector2(0, -1), size.y/2, groundedHit))
		{
			return true;
		}
		return false;
	}
	
	public Rectangle getBounds()
	{
		return getBoundsTotal();
	}
	
	public void getAllBounds()
	{
		bottomBounding = getBoundsBottom();
		topBounding = getBoundsTop();
		leftBounding = getBoundsLeft();
		rightBounding = getBoundsRight();
	}
	
	public Rectangle bottomBounding = new Rectangle(), topBounding = new Rectangle(), leftBounding = new Rectangle(), rightBounding = new Rectangle();
	
	// total
	public Rectangle getBoundsTotal() 
	{
		return new Rectangle(
				(int)gameObject.getPosition().x, 
				(int)gameObject.getPosition().y, 
				(int)size.x, 
				(int)size.y);
	}
	// bottom
	public Rectangle getBoundsBottom() 
	{
		return new Rectangle(
				(int)gameObject.getPosition().x + (((int)size.x/2) - ((int)size.x/2)/2), 
				(int)gameObject.getPosition().y + ((int)size.y/2), 
				(int)(size.x/2), 
				(int)size.y/2);
	}
	// top
	public Rectangle getBoundsTop() 
	{
		return new Rectangle(
				(int)gameObject.getPosition().x + (((int)size.x/2) - ((int)size.x/2)/2), 
				(int)gameObject.getPosition().y, 
				(int)(size.x/2) - 5, 
				(int)size.y/2);
	}
	// right
	public Rectangle getBoundsRight() 
	{
		return new Rectangle(
				(int)gameObject.getPosition().x+(int)size.x - 5, 
				(int)gameObject.getPosition().y + 5, 
				(int)5, 
				(int)size.y - 15);
	}
	//left
	public Rectangle getBoundsLeft() 
	{
		return new Rectangle(
				(int)gameObject.getPosition().x, 
				(int)gameObject.getPosition().y + 5, 
				(int)5, 
				(int)size.y - 15);
	}
	
	public boolean raycast(Vector2 position, Vector2 direction, double distance, AtomicReference<RaycastHit> hit)
	{
		boolean intersects = false;
		Vector2 newPos = new Vector2(position.x, position.y);
		direction = direction.Multiply(-1);
		direction = direction.Multiply(distance);
		direction = direction.Add(newPos);
		Line2D line = new Line2D.Double(newPos.x, newPos.y, direction.x, direction.y);
		
		for(int i = 0; i < handler.objects.size(); i++)
		{
			GameObject curObject = handler.objects.get(i);
			if(!ignoreList.contains(curObject.id) && !curObject.trigger)
			{
				// Line collision check
				if(line.intersects(curObject.getBounds()))
				{
					intersects = true;
					Direction newDirection = Direction.DOWN;
					if(position.x < curObject.position.x)
						newDirection = Direction.LEFT;
					if(position.x > curObject.position.x)
						newDirection = Direction.RIGHT;
					if(position.y < curObject.position.y)
						newDirection = Direction.DOWN;
					if(position.y > curObject.position.y)
						newDirection = Direction.UP;
					
					double m = (direction.y - position.y) / (direction.x - position.x);
					double b = position.y;
					double x = curObject.position.x;
					double y = (m * x) + b;
					Collision collision = new Collision(curObject, new Vector2(x, y), newDirection);
					RaycastHit newHit = new RaycastHit();
					newHit.collision = collision;
					hit.set(newHit);
					return intersects;
				}
			}
		}
		return intersects;
	}
	
	public void collision()
	{
		getAllBounds();
		GameObject curObject;
		
		for(int i = 0; i < handler.objects.size(); i++)
		{
			curObject = handler.objects.get(i);
			if(!ignoreList.contains(curObject.id) && (gameObject.position.x - curObject.position.x) < (size.x * 2) && (gameObject.position.y - curObject.position.y) < (size.y * 2))
			{
				// Bottom collision check
				if(bottomBounding.intersects((curObject.getBounds())))
				{
					Collision collision = new Collision(curObject, new Vector2(size.x/2, -size.y), Direction.DOWN);
					if(curObject.trigger)
					{
						gameObject.onTriggerEnter(collision);
					}
					else
					{
						gameObject.setPosition(new Vector2(gameObject.getPosition().x, curObject.position.y - size.y));
						velocity.y = 0;
						gameObject.onCollisionEnter(collision);
					}
				}
				
				// Top collision check
				if(topBounding.intersects((curObject.getBounds())))
				{
					Collision collision = new Collision(curObject, new Vector2(size.x/2, size.y), Direction.UP);
					if(curObject.trigger)
					{
						gameObject.onTriggerEnter(collision);
					}
					else
					{
						gameObject.setPosition(new Vector2(gameObject.getPosition().x, curObject.position.y + (size.y)));
						velocity.y = 0;
						gameObject.onCollisionEnter(collision);
					}
				}
				
				// Right collision check
				if(rightBounding.intersects((curObject.getBounds())))
				{
					Collision collision = new Collision(curObject, new Vector2(size.x, size.y/2), Direction.RIGHT);
					if(curObject.trigger)
					{
						gameObject.onTriggerEnter(collision);
					}
					else
					{
						velocity.x = 0;
						gameObject.setPosition(new Vector2(curObject.position.x - size.x, gameObject.getPosition().y));
						gameObject.onCollisionEnter(collision);
					}
				}
				
				// Left collision check
				if(leftBounding.intersects((curObject.getBounds())))
				{
					Collision collision = new Collision(curObject, new Vector2(0, size.y/2), Direction.LEFT);
					if(curObject.trigger)
					{
						gameObject.onTriggerEnter(collision);
					}
					else
					{
						velocity.x = 0;
						gameObject.setPosition(new Vector2(curObject.position.x + size.x, gameObject.getPosition().y));
						gameObject.onCollisionEnter(collision);
					}
				}
			}
		}
	}
}
