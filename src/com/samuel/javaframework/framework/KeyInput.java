package com.samuel.javaframework.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.samuel.javaframework.window.ObjectHandler;

public class KeyInput extends KeyAdapter
{
	ObjectHandler handler;
	
	public KeyInput(ObjectHandler handler)
	{
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.objects.size(); i ++)
		{
			GameObject curObj = handler.objects.get(i);
			curObj.keyPressed(e);
		}
		
		// closing window
		if(key == KeyEvent.VK_ESCAPE)
		{
			System.exit(1);
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		for(int i = 0; i < handler.objects.size(); i ++)
		{
			GameObject curObj = handler.objects.get(i);
			curObj.keyReleased(e);
		}
	}
}
