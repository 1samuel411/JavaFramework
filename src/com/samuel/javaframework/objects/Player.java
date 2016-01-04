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
import com.samuel.javaframework.window.ObjectHandler;

public class Player extends GameObject 
{

	private float width = 35, height = 38;
	private final float speed = 7;
	private final float jumpSpeed = -40;
	private boolean jumping = true;
	
	private boolean movingLeft = false;
	private boolean movingRight = false;
	
	private float xOffset, xScale;
	
	private ObjectHandler handler;
	private Texture texture = Game.getTextureInstance()	;
	
	private Animation playerWalkAnim;
	private Animation playerIdleAnim;
	
	public Player(float x, float y, ObjectId id, ObjectHandler handler) 
	{
		super(x, y, id);
		this.handler = handler;
		
		playerIdleAnim = new Animation(20, texture.player[0], texture.player[1], texture.player[2]);
		playerWalkAnim = new Animation(10, texture.player[3], texture.player[4], texture.player[5], texture.player[6], texture.player[7], texture.player[8]);
		
		xOffset = x;
		xScale = width;
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
		
		collision(object);
		
		playerIdleAnim.runAnimation();
		playerWalkAnim.runAnimation();
	}

	public void render(Graphics g) 
	{
		if(velocityX < 0) xScale = -width;
		else if(velocityX > 0) xScale = width;
		if(velocityX < 0) xOffset = x + width;
		else if(velocityX > 0) xOffset = x;
		
		if(velocityX != 0)
		{
			if(!jumping)	playerWalkAnim.drawAnimation(g, (int)xOffset, (int)y, (int)xScale, (int)height);
		}
		else
		{
			if(!jumping)	playerIdleAnim.drawAnimation(g, (int)xOffset, (int)y, (int)xScale, (int)height);
		}
		if(jumping)
		{
			g.drawImage(texture.player[9], (int)xOffset, (int)y, (int)xScale, (int)height, null);
		}
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
	
	public void collision(LinkedList<GameObject> object)
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
