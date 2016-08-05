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

public class Enemy extends GameObject 
{
	
	private Vector2 size = new Vector2(28, 28);
	private final double speed = 5;
	private final double jumpSpeed = 3;
	
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean canMove = true;
	private boolean died = false;
	private final double dieTime = 2;
	private double curDieTime = 5;
	
	private double xOffset, xScale;
	
	private Texture texture = Game.getTextureInstance()	;
	
	private Animation walkAnim;
	private Animation idleAnim;
	
	private Rigidbody rigidbody;
	
	private LinkedList<ObjectId> ignoreList = new LinkedList<ObjectId>();
	
	public Enemy(Vector2 position, Vector2 scale, boolean trigger, ObjectId id, ObjectHandler handler) 
	{
		super(position, scale, trigger, id);

		idleAnim = new Animation(20, texture.enemy[0]);
		walkAnim = new Animation(10, texture.enemy[1], texture.enemy[2], texture.enemy[3]);
		
		rigidbody = new Rigidbody(this, size, 1, handler);
		
		ignoreList.add(this.getId());
		rigidbody.ignoreList = ignoreList;
		
		xOffset = getPosition().x;
		xScale = getScale().x;
		
		movingRight = true;
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
			object.remove(this);
		}
		
		if(triggerEntered && Game.TIME > triggerEnteredTimer)
			triggerEntered = false;
		
		if(!canMove)
		{
			movingLeft = false;
			movingRight = false;
		}
		
		idleAnim.runAnimation();
		walkAnim.runAnimation();
		
		
		if(movingLeft)
			rigidbody.setVelocity(new Vector2(-speed, rigidbody.getVelocity().y));
		else if(movingRight)
			rigidbody.setVelocity(new Vector2(speed, rigidbody.getVelocity().y));
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
			walkAnim.drawAnimation(g, (int)xOffset, (int)position.y, (int)xScale, (int)scale.y);
		}
		else
		{
			idleAnim.drawAnimation(g, (int)xOffset, (int)position.y, (int)xScale, (int)scale.y);
		}
	}

	// Key Input
	public void keyPressed(KeyEvent e)
	{
		
	}
	
	public void keyReleased(KeyEvent e)
	{
		
	}
	
	public void onCollisionEnter(Collision collision)
	{
		if(collision.object.getId() == ObjectId.Lava)
		{
			
		}
	}
	
	private boolean triggerEntered;
	private double triggerEnteredTimer;
	public void onTriggerEnter(Collision collision)
	{
		if(collision.object.getId() == ObjectId.EnemyPoint && !triggerEntered)
		{
			triggerEntered = true;
			triggerEnteredTimer = Game.TIME + 0.2d;
			if(movingLeft)
			{
				movingRight = true;
				movingLeft = false;
			}
			else
			{
				movingRight = false;
				movingLeft = true;
			}
		}
	}
	
	public void death()
	{
		movingLeft = false;
		movingRight = false;
		canMove = false;
		died = true;
		List<ObjectId> newList = Arrays.asList(ObjectId.values());
		ignoreList.addAll(newList);
		rigidbody.setIgnoreList(ignoreList);
		this.rigidbody.setVelocity(new Vector2(rigidbody.getVelocity().x, jumpSpeed));
		curDieTime = dieTime + (Game.TIME);
	}
}