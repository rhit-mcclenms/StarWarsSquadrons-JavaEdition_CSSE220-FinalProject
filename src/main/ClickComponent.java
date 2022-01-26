package main;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import gameobjects.Asteroid;
import gameobjects.BigShip;
import gameobjects.Enemy;
import gameobjects.Fuel;
import gameobjects.GameObject;
import gameobjects.Player;
import gameobjects.PowerUp;
import gameobjects.Enemy;
import gameobjects.Projectile;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class ClickComponent extends JComponent {
	

	private JFrame frame;
	private JLabel label;
	private int LevelNumber;
	
	public ArrayList<GameObject> currentObjects;
	private ArrayList<Enemy> currentEnemies;
	private ArrayList<Projectile> currentProjectiles;
	private ArrayList<Fuel> currentFuel;
	private ArrayList<PowerUp> currentPowerUps;
	private ArrayList<Asteroid> currentAsteroids;
	private Player player;
	private BigShip bigship;
	private BufferedImage background;
	private Level currentLevel;
	private boolean gameOver;
	private int fuel;
	
	private int points;
	
	
	
	/**
	 * Constructor
	 * @param frame
	 * @param GUIlabel
	 */
	

	public ClickComponent(JFrame frame, JLabel GUIlabel) {
		this.frame = frame;
		this.label = GUIlabel; 
		this.LevelNumber = 0;
		
		this.currentObjects = new ArrayList<GameObject>();
		this.currentEnemies = new ArrayList<Enemy>();
		this.currentProjectiles = new ArrayList<Projectile>();
		this.currentFuel = new ArrayList<Fuel>();
		this.currentPowerUps = new ArrayList<PowerUp>();
		this.currentAsteroids = new ArrayList<Asteroid>();
		this.background = null;
		try {
			this.SwitchLevel(true);
		} catch (Exception e) {
			System.out.println("could not find inital level");
		}
		this.gameOver = false;
		
		this.label.setText("check, one , two ,three...");
	}
		
	public JFrame getFrame() {
		return this.frame;
	}

	
	// ACTIONS
	
	public void down() {
		this.player.moveBackwards();
	}
	
	public void right() {
		this.player.turn(5);
	}
	
	public void up() {
		this.player.moveForwards();	
	}
	
	public void left() {
		this.player.turn(-5);
	}

	
	/**
	 *  create a new laser from the player and add it to the game
	 */
	public void playerFire() {
		Projectile laser = new Projectile(this.player.getPositionX(), this.player.getPositionY(), this.player.getRotation(), true);
		this.currentObjects.add(laser);
		this.getCurrentProjectiles().add(laser);
	}


	/**
	 * read level and extract created game objects
	 * @throws Exception 
	 */
	public void SwitchLevel(boolean nextLevel) throws Exception {
		// check if next level is a valid request
		if (nextLevel == true && this.LevelNumber < 3) {
			this.LevelNumber += 1;

		} else if (nextLevel == false && this.LevelNumber > 1) {
			this.LevelNumber -= 1;
			
		} else {
			throw new Exception();
		}
		
		// reset game objects
		this.currentLevel = new Level();
		this.currentEnemies.clear();
		this.currentProjectiles.clear();
		this.currentFuel.clear();
		this.currentPowerUps.clear();
		this.currentObjects.clear();
		this.currentAsteroids.clear();
		this.gameOver = false;
		this.fuel = 0;
		this.points = 0;
		
		
		// load a level from the level reader
		System.out.println("LEVEL " + this.LevelNumber);
		try {
			this.currentObjects = currentLevel.readNewFile("levels/LEVEL"+this.LevelNumber+".csv", this.frame);
			try {
				background = this.currentLevel.getLevelImage(this.LevelNumber);
			} catch (IOException e) {
				System.out.println("no level " + this.LevelNumber + " background");
			}
		} catch (IOException e) {
			System.out.println("LEVEL NOT FOUND!");
		}
		
		
		// designate a player and enemies
		for (GameObject object:this.currentObjects) {
			if (object.type == 1){
				this.player = (Player) object;
			} else if (object.type == 2) {
				this.currentEnemies.add((Enemy) object);
			} else if (object.type == 4) {
				this.currentFuel.add((Fuel) object);
			} else if (object.type == 5) {
				this.bigship = (BigShip) object;
			} else if (object.type == 6) {
				this.currentPowerUps.add((PowerUp) object);
			} else if (object.type == 7) {
				this.currentAsteroids.add((Asteroid) object);
			}
		}
		
		// exception to designate a special uncompleted ship in the first level
		if (this.LevelNumber == 1) {
			this.bigship.setImageCompletion(0);
		}
		
		this.drawScreen();
	}
	
	
	
	// AUTOMATIC ACTIONS
	
	// check for endgame 
	public void checkIfEndgame() {
		// check if player is dead
		if (this.player.getHealth() <= 1) {
			this.gameOver = true;
		}
		
		// trigger ship leaving
		if (this.fuel == 3) {
			if (this.bigship.getHasTurned() == false ) {
				this.bigship.turn(-90);
				this.bigship.setHasTurned(true);
			}
			this.bigship.moveForwards();
		}
		// check if ship has left 
		if (this.bigship.getPositionY() <= 0) {
			this.gameOver = true;
		}
		// if game has ended, show endcards
		if (this.gameOver == true) {
			if (this.fuel == 3) {
				//System.out.println("win");
				try {
					background = ImageIO.read(new File("images/winImage.png"));
				} catch (IOException e) {
					System.out.println("no win background");
				}
			} else {
				//System.out.println("loss");
				try {
					background = ImageIO.read(new File("images/loseImage.png"));
				} catch (IOException e) {
					System.out.println("no loss background");
				}
			}
			this.repaint();
			this.currentObjects.clear();
		}
		
		
	}
	
	
	
	// gravity
	public void gravity() {
		this.player.moveDown();
	}
	
	// continue objects on paths
	public void momentum() {
		for (GameObject object : this.currentObjects) {
			object.continuePath();
		}
	}
	
	// run AI for each enemy
	public void drawEnemies() {
		for (Enemy enemy : this.currentEnemies) {
			enemy.enemyMove(this);
		}
	}
	
	// move fuel cans if they are attached to the player
	public void dragCans() {
		for (Fuel fuelcan : this.currentFuel ) {
			fuelcan.moveWith(this.player);
		}
	}
	
	// check for collisions 
	public void checkCollisions() {
		// Player
		// check for collision with other craft
		for (Enemy opponent : this.currentEnemies) {
			if (this.player.checkCollision(opponent.getPositionX(), opponent.getPositionY(), opponent.getObjectSizeY()+10, opponent.getObjectSizeY()+10) == true) {
				this.player.bounce();
				this.player.setHealth(this.player.getHealth()-1);
			}
		}
		// check for collision with the bounding box
		if (this.player.getPositionY() > this.frame.getHeight()-this.player.getObjectSizeY()/2 || this.player.getPositionY() < this.player.getObjectSizeY()/2 || this.player.getPositionX() > this.frame.getWidth()-this.player.getObjectSizeX()/2 || this.player.getPositionX() < this.player.getObjectSizeX()/2) {
			this.player.bounce();
		}
		// handle out of map glitching (ideally this is never called)
		if (this.player.getPositionY() > this.frame.getHeight() || this.player.getPositionY() < 0 || this.player.getPositionX() > this.frame.getWidth() || this.player.getPositionX() < 0 ) {
			this.player.setPositionX(300);
			this.player.setPositionY(300);
		}
		// check for collision with fuel 
		for (Fuel can : this.currentFuel) {
			if (this.player.checkCollision(can.getPositionX(), can.getPositionY(), can.getObjectSizeX()+10, can.getObjectSizeY()+10) == true) {
				can.attachTo(this.player);
				//can.markForRemoval();
			}
			if (this.bigship.checkCollision(can.getPositionX(), can.getPositionY(), can.getObjectSizeX()+30, can.getObjectSizeY()+30) == true) {
				// logic to determine the completion level of the bigship
				if (this.LevelNumber == 1 ) {
					if (this.currentFuel.size() == 3) {
						this.bigship.setImageCompletion(can.getPartNumber());
						this.points += 100;
					} else if (this.currentFuel.size() == 2) {
						this.bigship.setImageCompletion(can.getPartNumber()+3);
						this.points += 500;
					} else {
						// complete the ship
						this.bigship.setImageCompletion(7);
						this.points += 1000;
					}
				}
				can.markForRemoval();
			}
		}
		// check for collision with bigship
			if (this.player.checkCollision(this.bigship.getPositionX(), this.bigship.getPositionY(), this.bigship.getObjectSizeX()+30, this.bigship.getObjectSizeY()+30) == true) {
				this.fuel = 3 - this.currentFuel.size();
			}
		// check for collision with powerups
		for (PowerUp boost : this.currentPowerUps) {
			if (this.player.checkCollision(boost.getPositionX(), boost.getPositionY(), boost.getObjectSizeX()+10, boost.getObjectSizeY()+10) == true) {
				if (boost.getPowerType() == 0) {
					// powerup 1
					this.player.setHealth(this.player.getHealth()+5);
				} else {
					// powerup 2
					this.player.increaseMaxSpeed();
				}
				boost.markForRemoval();
			}
		}
		// check for collision with asteroids
		for (Asteroid obstacle : this.currentAsteroids) {
			if (this.player.checkCollision(obstacle.getPositionX(), obstacle.getPositionY(), obstacle.getObjectSizeX()+10, obstacle.getObjectSizeY()+10) == true) {
				this.player.bounce();
				this.player.setHealth((int) (this.player.getHealth()-this.player.getTotalVelocity()));
			}
		}
		
		
		// Projectiles
		for (GameObject object1 : this.currentProjectiles) {
		// remove laser if out of bounds
			if (object1.getPositionY() > this.frame.getHeight() || object1.getPositionY() < 0 || object1.getPositionX() > this.frame.getWidth() || object1.getPositionX() < 0) {
				object1.markForRemoval();
			} else {
				// check for collisions with Enemies
				for (GameObject object2 : this.currentEnemies) {
					if (object1.checkCollision(object2.getPositionX(), object2.getPositionY(), object2.getObjectSizeX(), object2.getObjectSizeY()) == true) {
						// enemy loses health
						object2.setHealth(object2.getHealth()-1);
						// remove laser
						this.points += 10;
						object1.markForRemoval();
					}
				}
				// check for collisions with Asteroids
				for (GameObject object2 : this.currentAsteroids) {
					if (object1.checkCollision(object2.getPositionX(), object2.getPositionY(), object2.getObjectSizeX(), object2.getObjectSizeY()) == true) {
						object2.setHealth(object2.getHealth()-1);
						this.points += 10;
						object1.markForRemoval();
						
					}
				}
				// check for collisions with Player
				if (object1.checkCollision(this.player.getPositionX(), this.player.getPositionY(), this.player.getObjectSizeX(), this.player.getObjectSizeX()) == true) {
					// player loses health
					this.player.setHealth(this.player.getHealth()-1);
					// remove laser
					object1.markForRemoval();
				}
			}
		}
	}
	
//	void playMusic() {
//		InputStream music;
//		String musicLocation = "images/level3OST.wav";
//
//		try 
//		{
//			File musicPath = new File(musicLocation);
//			
//			if(musicPath.exists()) {
//				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
//				Clip clip = AudioSystem.getClip();
//				clip.open(audioInput);
//				clip.start();
//				
//				
//				System.out.println(this.LevelNumber);
//				
//				JOptionPane.showMessageDialog(null, "Press ok to stop playing");
//				
////				if(this.LevelNumber == 2) {
////					System.out.println("It is level 2!");
////					clip.stop();
////				}
////				System.out.println(this.LevelNumber);
//				
//				
//			}
//			
//			else {
//				System.out.println("Can't find file");
//			}
//		} 
//		
//		
//		catch (Exception ex) 
//		{
//			ex.printStackTrace();
//		}
//		
//		
//
//		
//	}
	
	


	
	// remove all objects marked for removal
	public void devourTheMarked() {
		for (int i=0; i<this.currentFuel.size();i++) {
			if (this.currentFuel.get(i).getIsMarked() == true) {
				this.currentFuel.remove(i);
			}
		}
		for (int i=0; i<this.currentEnemies.size();i++) {
			if (this.currentEnemies.get(i).getIsMarked() == true) {
				this.currentEnemies.remove(i);
			}
		}
		for (int i=0; i<this.getCurrentProjectiles().size();i++) {
			if (this.getCurrentProjectiles().get(i).getIsMarked() == true) {
				this.getCurrentProjectiles().remove(i);
			}
		}
		for (int i=0; i<this.currentObjects.size();i++) {
			if (this.currentObjects.get(i).getIsMarked() == true) {
				this.currentObjects.remove(i);
			}
		}
		for (int i=0; i<this.currentPowerUps.size();i++) {
			if (this.currentPowerUps.get(i).getIsMarked() == true) {
				this.currentPowerUps.remove(i);
			}
		}
		for (int i=0; i<this.currentAsteroids.size();i++) {
			if (this.currentAsteroids.get(i).getIsMarked() == true) {
				this.currentAsteroids.remove(i);
			}
		}
	}
	
	// redraw 
	public void drawScreen() {
		this.label.setText("HEALTH:  " + (this.player.getHealth() - 1) + "        MOTHERSHIP FUEL: " + this.fuel + "/3 " +  "        ENEMIES REMAINING: " + this.currentEnemies.size() +  "        SCORE: " + this.points);
		
		if (this.gameOver == false) {
			this.repaint();
		}
		
		
	}
	
	@Override
	/**
	 *  draws all the objects onto the component
	 */
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		g2.drawImage(this.background, 0,0,frame.getWidth(), frame.getHeight(), null);
		
		// paint all objects in the game
		for (GameObject object : this.currentObjects) {
			object.drawObject(g2);
		}
		
		
	}

	// used to 
	public ArrayList<Projectile> getCurrentProjectiles() {
		return currentProjectiles;
	}







	
	

}
