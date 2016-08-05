package com.samuel.javaframework.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import javax.swing.JOptionPane;

import com.samuel.javaframework.framework.Application;
import com.samuel.javaframework.framework.Input;
import com.samuel.javaframework.framework.Texture;
import com.samuel.javaframework.framework.Base.Vector2;

public class Game extends Canvas implements Runnable 
{

	private static final long serialVersionUID = 7267857337898051117L;

	private boolean running = false;
	private Thread thread;
	
	public static int WIDTH, HEIGHT;
	public static double DELTA;
	public static double TIME;
	
	ObjectHandler handler;
	Camera camera;
	static Texture texture;
	public static Application application;
	
	private void init()
	{
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		// Texture
		texture = new Texture();
		
		// Handler
		handler = new ObjectHandler();
		
		// Camera
		camera = new Camera(new Vector2(0,0));
		
		// Application
		application = new Application(handler);
		
		application.loadBackground("background_stone");
		application.loadLevel("level1");
		
		// Key handler
		this.addKeyListener(new Input(handler));
		
		
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
		
		long startTime = System.currentTimeMillis();
		while(running)
		{
			long now = System.nanoTime();
			DELTA += (now - lastTime) / ns;
			lastTime = now;
			while(DELTA >= 0.33333)
			{
				tick();
				updates++;
				DELTA-= 1;
			}
			render();
			frames++;
			
			TIME = (System.currentTimeMillis() - startTime) / 1000d;
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
		
		camera.tick();
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
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		/// Draw Game
		
		g2d.translate(camera.getX(), camera.getY());
		
		application.render(g);
		
		handler.render(g);

		g2d.translate(-camera.getX(), -camera.getY());
		//////
		
		g.dispose();
		bs.show();
	}
	
	public static Texture getTextureInstance()
	{
		return texture;
	}
	
	private static boolean showMessage = false;
	public static void main(String args[])
	{
		if(showMessage)
			JOptionPane.showMessageDialog(null, "Created by Samuel Arminana, 2016", "Info: " + "Java Framework", JOptionPane.INFORMATION_MESSAGE);
		new Window(800, 600, "Samuel Arminana", new Game());
	}
}
