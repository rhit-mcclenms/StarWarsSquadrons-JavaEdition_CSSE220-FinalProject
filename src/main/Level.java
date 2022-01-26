package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import gameobjects.BigShip;
import gameobjects.Asteroid;
import gameobjects.Enemy;
import gameobjects.Fuel;
import gameobjects.GameObject;
import gameobjects.Player;
import gameobjects.PowerUp;

public class Level {
	
	protected ArrayList<GameObject> objectsInLevel = new ArrayList<GameObject>();
	// should also store a background image
	private int PixelModifierX = 1600/20;
	private int PixelModifierY = 900/20;
	
	private String level1Filename = "images/level1.jpg";
	private String level2Filename = "images/level2.jpg";
	private String level3Filename = "images/level3.jpg";
	private String titleScreen = "images/titleImage.jpg";
	
	
	/**
	 * Expecting 20x20 grid of level data
	 * @param fileName
	 * @throws IOException 
	 */
	public ArrayList<GameObject> readNewFile(String levelFileName, JFrame frame) throws IOException {
		
		this.PixelModifierX = frame.getWidth()/20;
		this.PixelModifierY = frame.getHeight()/20;
		
		
		String filename = levelFileName;
		FileReader file = new FileReader(filename);
		Scanner s = new Scanner(file);
		s.useDelimiter(",");
		
		boolean playerCreated = false;
		
		s.nextLine();
		for (int row=0; row<20; row++) {
			
			for (int col=0; col<19; col++ ) {
				String currentLetter = s.next();
				
				// PLAYER 
				if (currentLetter.contentEquals("Player")) {
					if (playerCreated == false) {
						Player currentObject = new Player(col*PixelModifierX,row*PixelModifierY, 20);
						this.objectsInLevel.add(currentObject);
						playerCreated = true;
					}
				}
				// ENEMY 2
				if (currentLetter.contentEquals("E1")) {
						Enemy currentObject = new Enemy(col*PixelModifierX,row*PixelModifierY, 0);
						this.objectsInLevel.add(currentObject);
				}
				// ENEMY 2
				if (currentLetter.contentEquals("E2")) {
						Enemy currentObject = new Enemy(col*PixelModifierX,row*PixelModifierY, 1);
						this.objectsInLevel.add(currentObject);
				}
				
				// DEBRIS SIZE 1
				if (currentLetter.contentEquals("D1")) {
					Asteroid currentObject = new Asteroid(col*PixelModifierX,row*PixelModifierY, 1);
					this.objectsInLevel.add(currentObject);
				}
				
				// DEBRIS SIZE 2
				if (currentLetter.contentEquals("D2")) {
					Asteroid currentObject = new Asteroid(col*PixelModifierX,row*PixelModifierY, 2);
					this.objectsInLevel.add(currentObject);
				}
				
				// POWERUP TYPE 1
				if (currentLetter.contentEquals("P1")) {
					PowerUp currentObject = new PowerUp(col*PixelModifierX,row*PixelModifierY, 0);
					this.objectsInLevel.add(currentObject);
				}
				
				// POWERUP TYPE 2
				if (currentLetter.contentEquals("P2")) {
					PowerUp currentObject = new PowerUp(col*PixelModifierX,row*PixelModifierY, 1);
					this.objectsInLevel.add(currentObject);
				}
				
				// FUEL
				if (currentLetter.contentEquals("F")) {
					Fuel currentObject = new Fuel(col*PixelModifierX,row*PixelModifierY, 0);
					this.objectsInLevel.add(currentObject);
				}
				
				// Ship Parts (classified as fuel for easier implementation)
				if (currentLetter.contentEquals("Part1")) {
					Fuel currentObject = new Fuel(col*PixelModifierX,row*PixelModifierY, 1);
					this.objectsInLevel.add(currentObject);
				}
				if (currentLetter.contentEquals("Part2")) {
					Fuel currentObject = new Fuel(col*PixelModifierX,row*PixelModifierY, 2);
					this.objectsInLevel.add(currentObject);
				}
				if (currentLetter.contentEquals("Part3")) {
					Fuel currentObject = new Fuel(col*PixelModifierX,row*PixelModifierY, 3);
					this.objectsInLevel.add(currentObject);
				}
				
				// BIGSHIP
				if (currentLetter.contentEquals("L")) {
					BigShip currentObject = new BigShip(col*PixelModifierX,row*PixelModifierY);
					this.objectsInLevel.add(currentObject);
				}
				
				
				//System.out.print(currentLetter); //displays the level in the console
			}
			
		}
		System.out.println("");
		

		file.close();
		s.close();
		
		return objectsInLevel;
	}


	public BufferedImage getLevelImage(int levelnumber) throws IOException {
		if (levelnumber == 1) {
			return ImageIO.read(new File(level1Filename));
		} else if (levelnumber == 2 ){
			return ImageIO.read(new File(level2Filename));
		} else if (levelnumber == 3 ){
			return ImageIO.read(new File(level3Filename));
		} else {
			return ImageIO.read(new File(level1Filename));
		}
		
	}
	
}
		
