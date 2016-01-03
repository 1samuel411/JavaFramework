package com.samuel.hangman.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.samuel.hangman.framework.KeyInput;
import com.samuel.hangman.framework.ObjectId;
import com.samuel.hangman.objects.Player;

public class Game extends Canvas implements Runnable 
{

	private static final long serialVersionUID = 7267857337898051117L;

	private boolean running = false;
	private Thread thread;
	
	public static int WIDTH, HEIGHT;
	public static double DELTA;
	
	ObjectHandler handler;
	
	private void init()
	{
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		// Handler
		handler = new ObjectHandler();
		// make player
		Player player = new Player(100, 100, ObjectId.Player, handler);
		player.setGrounded(false);
		handler.addObject(player);
		handler.createLevel();
		
		// Key handler
		this.addKeyListener(new KeyInput(handler));
	}
	
	public synchronized void start()
	{
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() 
	{
		init();
		this.requestFocus();
		
		//game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		DELTA = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running)
		{
			long now = System.nanoTime();
			DELTA += (now - lastTime) / ns;
			lastTime = now;
			while(DELTA >= 0.3333)
			{
				tick();
				updates++;
				DELTA--;
			}
			render();
			frames++;
			
			// every second tell us the fps count and tick count
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println("FPS: " + frames + "; Ticks: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	private void tick()
	{
		handler.tick();
	}
	
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();	
		
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		/// Draw Game
		handler.render(g);
		//////
		
		g.dispose();
		
		bs.show();
	}
	
	public static void main(String args[])
	{
		new Window(800, 600, "Hangman - Samuel Arminana", new Game());
	}
}
