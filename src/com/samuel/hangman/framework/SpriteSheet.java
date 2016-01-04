package com.samuel.hangman.framework;

import java.awt.image.BufferedImage;

public class SpriteSheet 
{
	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image)
	{
		this.image = image;
	}
	
	public BufferedImage getImage(int column, int row, int width, int height)
	{
		BufferedImage img = image.getSubimage((column * width) - width, (row * height) - height, width, height);
		return img;
	}
}
