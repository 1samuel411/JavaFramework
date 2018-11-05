# S-Engine (Java Framework)

- A completey open sourced and customizable game engine created in Java.

# How it works

- The game is launched via the Game.java class where it then opens up a window based on the options and also creates a new Game instance.

- Once the instance is launched it will create...
	- The **texture** instance which loads all sprites and textures in.
	- The **handler** instance which will cycle through every gameObject and call the **render** and **tick** methods.
	- The **camera** instance which will allow the 'camera' to move around in the game.
	- The **application** instance which controls the loading of gameObjects, levels, and backgrounds.
	- The **keyListener** which will listen for keys and report them to any gameObjects.

- **Game variables** can be accessed any time by doing, Game.variable
	- **TIME** - The time in seconds since the launch of the game.
	- **DELTA** - The delta time
	- **WIDTH** and **HEIGHT** - The width and height of the window
	- **application** - The application instance.

- **GameObject**
	* All objects must be GameObjects.
	* GameObject inherits from the Base.java class which contains variables that the engine will use such as, RaycastHit, Collision, Vector2, etc.

- There is a demo game already created, take a look and play with it and figure out how it works :>
