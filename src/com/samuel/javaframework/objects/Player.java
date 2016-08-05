package com.samuel.javaframework.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.samuel.javaframework.framework.Animation;
import com.samuel.javaframework.framework.GameObject;
import com.samuel.javaframework.framework.ObjectId;
import com.samuel.javaframework.framework.Rigidbody;
import com.samuel.javaframework.framework.Texture;
import com.samuel.javaframework.window.Game;
import com.samuel.javaframework.window.ObjectHandler;

public class Player extends GameObject 
{
	
	private Vector2 size = new Vector2(30, 38);
	private final double speed = 7;
	private final double jumpSpeed = -25;
	private boolean jumping = true;
	private boolean canMove = true;
	private boolean canJump = true;
	private boolean died = false;
	private final double dieTime = 2;
	private double curDieTime = 5;
	
	private boolean movingLeft = false;
	private boolean movingRight = false;
	
	private double xOffset, xScale;
	
	private Texture texture = Game.getTextureInstance()	;
	
	private Animation playerWalkAnim;
	private Animation playerIdleAnim;
	
	private Rigidbody rigidbody;
	
	private LinkedList<ObjectId> ignoreList = new LinkedList<ObjectId>();
	
	public int jumpsLeft = 0;
	public int jumps = 2;
	
	public Player(Vector2 position, Vector2 scale, boolean trigger, ObjectId id, ObjectHandler handler) 
	{
		super(position, scale, trigger, id);

		playerIdleAnim = new Animation(20, texture.player[0], texture.player[1], texture.player[2]);
		playerWalkAnim = new Animation(10, texture.player[3], texture.player[4], texture.player[5], texture.player[6], texture.player[7], texture.player[8]);
		
		rigidbody = new Rigidbody(this, size, 1, handler);
		
		ignoreList.add(this.getId());
		rigidbody.setIgnoreList(ignoreList);
		
		xOffset = getPosition().x;
		xScale = getScale().x;
	}
	
	public Rectangle getBounds()
	{
		 return rigidbody.getBounds();
	}

	public void tick(LinkedList<GameObject> object) 
	{
		rigidbody.tick();
		
		if(died && (Game.TIME) > curDieTime)
		{
			Game.application.reloadLevel();
		}
		
		if(rigidbody.getGrounded() && (jumpsLeft < 1 || jumping))
		{
			jumping = false;
			jumpsLeft = jumps;
		}
		
		if(rigidbody.getGrounded() == false && jumpsLeft > 1)
			jumpsLeft = 1;
		
		playerIdleAnim.runAnimation();
		playerWalkAnim.runAnimation();
		
		if(!canMove)
		{
			movingLeft = false;
			movingRight = false;
		}
		if(movingLeft)
			rigidbody.setVelocity(new Vector2(speed, rigidbody.getVelocity().y));
		else if(movingRight)
			rigidbody.setVelocity(new Vector2(-speed, rigidbody.getVelocity().y));
		else
			rigidbody.setVelocity(new Vector2(0, rigidbody.getVelocity().y));
	}

	public void render(Graphics g) 
	{
		if(rigidbody.getVelocity().x < 0) xScale = -scale.x;
		else if(rigidbody.getVelocity().x > 0) xScale = scale.x;
		if(rigidbody.getVelocity().x < 0) xOffset = position.x + scale.x;
		else if(rigidbody.getVelocity().x > 0) xOffset = position.x;
		
		if(rigidbody.getVelocity().x != 0)
		{
			if(!jumping && rigidbody.getGrounded())	playerWalkAnim.drawAnimation(g, (int)xOffset, (int)position.y, (int)xScale, (int)scale.y);
		}
		else
		{
			if(!jumping && rigidbody.getGrounded())	playerIdleAnim.drawAnimation(g, (int)xOffset, (int)position.y, (int)xScale, (int)scale.y);
		}
		if(jumping)
		{
			if(rigidbody.getVelocity().y < 0 || jumpsLeft > 0)
				g.drawImage(texture.player[9], (int)xOffset, (int)position.y, (int)xScale, (int)scale.y, null);
			else
				g.drawImage(texture.player[10], (int)xOffset, (int)position.y, (int)xScale, (int)scale.y, null);
		}
		else
		{
			if(!rigidbody.getGrounded())
			{
				g.drawImage(texture.player[10], (int)xOffset, (int)position.y, (int)xScale, (int)scale.y, null);
			}
		}
	}

	// Key Input
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_SPACE || key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_W)
		{
			if(jumpsLeft > 0 && canJump)
			{
				jumpsLeft --;
				jumping = true;
				this.rigidbody.setVelocity(new Vector2(rigidbody.getVelocity().x, jumpSpeed));
			}
		}
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
		{
			movingLeft = true;
		}
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
		{
			movingRight = true;
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
		{
			movingLeft = false;
			if(!movingRight)
				this.rigidbody.getVelocity().x = 0;
		}
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
		{
			movingRight = false;
			if(!movingLeft)
				this.rigidbody.getVelocity().x = 0;
		}
	}
	
	public void onCollisionEnter(Collision collision)
	{
		if(collision.object.getId() == ObjectId.Lava)
			death();
	}
	
	public void onTriggerEnter(Collision collision)
	{
		if(collision.object.getId() == ObjectId.Enemy)
		{
			if(collision.direction == Direction.DOWN)
			{
				collision.object.death();
				this.rigidbody.setVelocity(new Vector2(rigidbody.getVelocity().x, jumpSpeed));
				jumpsLeft = 0;
			}
			else
				death();
		}
	}
	
	public void death()
	{
		movingLeft = false;
		movingRight = false;
		canMove = false;
		canJump = false;
		died = true;
		List<ObjectId> newList = Arrays.asList(ObjectId.values());
		ignoreList.addAll(newList);
		rigidbody.setIgnoreList(ignoreList);
		this.rigidbody.setVelocity(new Vector2(rigidbody.getVelocity().x, jumpSpeed));
		curDieTime = dieTime + (Game.TIME);
	}
}