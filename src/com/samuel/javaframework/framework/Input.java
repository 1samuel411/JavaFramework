package com.samuel.javaframework.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.samuel.javaframework.window.ObjectHandler;

public class Input extends KeyAdapter implements MouseListener
{
	ObjectHandler handler;
	
	public Input(ObjectHandler handler)
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

	public void mouseClicked(MouseEvent e) 
	{
		System.out.println("HI");
	}

	public void mousePressed(MouseEvent e) 
	{
		
	}

	public void mouseReleased(MouseEvent e) 
	{
		
	}

	public void mouseEntered(MouseEvent e) 
	{
		
	}
	
	public void mouseExited(MouseEvent e) 
	{
		
	}
}
