package com.samuel.hangman.framework;



import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.samuel.hangman.window.Game;

public class Animation 
{
	private int speed;
	
	private int index;
	private int frameCount;
	private int currentCount;
	
	private BufferedImage[] animationImages;
	private BufferedImage currentImg;
	
	public Animation(int speed, BufferedImage... args)
	{
		this.speed = speed;
		animationImages = new BufferedImage[args.length];
		for(int i = 0; i < args.length; i++)
		{
			animationImages[i] = args[i];
		}
		frameCount = args.length;
	}
	
	public void runAnimation()
	{
		index+= (1);
		if(index > (speed * Game.DELTA))
		{
			index = 0;
			nextFrame();
		}
	}
	
	private void nextFrame()
	{
		for(int i = 0; i < frameCount; i++)
		{
			if(currentCount == i)
				currentImg = animationImages[i];
		}
		
		currentCount ++;
		
		if(currentCount > frameCount)
			currentCount = 0;
	}
	
	public void drawAnimation(Graphics g, int x, int y)
	{
		g.drawImage(currentImg, x, y, null);
	}
	
	public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY)
	{
		g.drawImage(currentImg, x, y, scaleX, scaleY, null);
	}
}
