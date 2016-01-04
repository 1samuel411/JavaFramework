package com.samuel.javaframework.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import com.samuel.javaframework.framework.ImageLoader;
import com.samuel.javaframework.framework.KeyInput;
import com.samuel.javaframework.framework.ObjectId;
import com.samuel.javaframework.framework.Texture;
import com.samuel.javaframework.objects.Block;
import com.samuel.javaframework.objects.Player;

public class Game extends Canvas implements Runnable 
{

	private static final long serialVersionUID = 7267857337898051117L;

	private boolean running = false;
	private Thread thread;
	
	public static int WIDTH, HEIGHT;
	public static double DELTA;
	
	private BufferedImage level = null, background = null;
	
	ObjectHandler handler;
	Camera camera;
	ImageLoader imageLoader;
	static Texture texture;
	
	private void init()
	{
		JOptionPane.showMessageDialog(null, "Created by Samuel Arminana, 2016", "InfoBox: " + "Java Framework", JOptionPane.INFORMATION_MESSAGE);
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		// Texture
		texture = new Texture();
		
		// Handler
		handler = new ObjectHandler();
		
		// Camera
		camera = new Camera(0, 0);

		ImageLoader loader = new ImageLoader();
		// Load background
		background = loader.loadImage("/levels/background.png");
		// Load level
		level = loader.loadImage("/levels/level1.png");
		loadImageLevel(level);
		
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
			while(DELTA >= 0.33333)
			{
				tick();
				updates++;
				DELTA-= 1;
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
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		/// Draw Game
		g2d.translate(camera.getX(), camera.getY());
		
		for(int x = 0; x < (background.getWidth()/2) * 5; x += (background.getWidth()/2))
			for(int y = 0; y < (background.getHeight()*0.6f) * 5; y += (background.getHeight()*0.6f))
				g.drawImage(background, x, y, (int)background.getWidth()/2, (int)(background.getHeight() * 0.6f), this);
		
		handler.render(g);
		
		g2d.translate(-camera.getX(), -camera.getY());
		//////
		
		g.dispose();
		bs.show();
	}
	
	private void createPlayer(int x, int y)
	{
		Player player = new Player(x, y, ObjectId.Player, handler);
		player.setGrounded(false);
		handler.addObject(player);
	}
	
	private void loadImageLevel(BufferedImage image)
	{
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		
		for(int x = 0; x < imageWidth; x++)
		{
			for(int y = 0; y < imageHeight; y++)
			{
				int pixel = image.getRGB(x, y);
				int r = (pixel >> 16) & 0xff;
				int g = (pixel >> 8) & 0xff;
				int b = (pixel) & 0xff;
				
				// dirt
				if(r == 255 && g == 255 && b == 255)
					handler.addObject(new Block(x * 32, y * 32, ObjectId.Dirt));
				
				// player
				if(r == 0 && g == 0 && b == 255)
					createPlayer(x * 32, y * 32);
			}
		}
	}
	
	public static Texture getTextureInstance()
	{
		return texture;
	}
	
	public static void main(String args[])
	{
		new Window(800, 600, "Samuel Arminana", new Game());
	}
}
