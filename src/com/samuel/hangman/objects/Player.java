package com.samuel.hangman.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import com.samuel.hangman.framework.GameObject;
import com.samuel.hangman.framework.ObjectId;
import com.samuel.hangman.window.Game;
import com.samuel.hangman.window.ObjectHandler;

public class Player extends GameObject 
{

	private float width = 32, height = 64;
	private final float speed = 5;
	private final float jumpSpeed = -45;
	private boolean jumping = false;
	
	private boolean movingLeft = false;
	private boolean movingRight = false;
	
	private ObjectHandler handler;
	
	public Player(float x, float y, ObjectId id, ObjectHandler handler) 
	{
		super(x, y, id);
		this.handler = handler;
	}

	public void tick(LinkedList<GameObject> object) 
	{
		x += velocityX * Game.DELTA;
		y += velocityY * Game.DELTA;
		
		if(grounded == false || jumping == true)
		{
			velocityY += (gravity);
			
			if(velocityY >= max_speed )
				velocityY = max_speed;
		}
		
		Collision(object);
	}

	public void render(Graphics g) 
	{
		g.setColor(Color.blue);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}

	// bottom
	public Rectangle getBounds() 
	{
		return new Rectangle((int)x + (((int)width/2) - ((int)width/2)/2), (int)y + ((int)height/2), (int)width/2, (int)height/2);
	}
	// top
	public Rectangle getBoundsTop() 
	{
		return new Rectangle((int)x + (((int)width/2) - ((int)width/2)/2), (int)y, (int)width/2, (int)height/2);
	}
	// right
	public Rectangle getBoundsRight() 
	{
		return new Rectangle((int)x+(int)width-5, (int)y + 5, (int)5, (int)height - 10);
	}
	//left
	public Rectangle getBoundsLeft() 
	{
		return new Rectangle((int)x, (int)y + 5, (int)5, (int)height - 10);
	}
	
	// Key Input
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_SPACE && !jumping)
		{
			jumping = true;
			this.setVelocityY(jumpSpeed);
		}
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
		{
			movingLeft = true;
			this.setVelocityX(speed);
		}
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
		{
			movingRight = true;
			this.setVelocityX(-speed);
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
		{
			movingLeft = false;
			if(!movingRight)
				this.setVelocityX(0);
		}
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
		{
			movingRight = false;
			if(!movingLeft)
				this.setVelocityX(0);
		}
	}
	
	public void Collision(LinkedList<GameObject> object)
	{
		for(int i = 0; i < handler.objects.size(); i++)
		{
			GameObject curObject = handler.objects.get(i);
			
			if(curObject.getId() != ObjectId.Player)
			{
				// Bottom collision check
				if(this.getBounds().intersects((curObject.getBounds())))
				{
					y = curObject.getY() - height;
					velocityY = 0;
					jumping = false;
				}
				
				// Top collision check
				if(this.getBoundsTop().intersects((curObject.getBounds())))
				{
					y = curObject.getY() + (height / 2);
					velocityY = 0;
				}
				
				// Right collision check
				if(this.getBoundsRight().intersects((curObject.getBounds())))
				{
					x = curObject.getX() - width;
				}
				
				// Left collision check
				if(this.getBoundsLeft().intersects((curObject.getBounds())))
				{
					x = curObject.getX() + width;
				}
			}
		}
	}
}
