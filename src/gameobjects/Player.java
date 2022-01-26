package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends GameObject {

	private String imgFilename = "images/fillimium_malcon.png";
	public boolean fuelCooldown;
	
	public Player(int positionX, int positionY, int maxHealth) {
		super(positionX, positionY);
		this.type = 1;
		this.maxSpeed = 12;
		this.setObjectSize(100,100);
		this.setSlowable(true);
		this.setHealth(21);
		
		
		try {
			this.setImage(ImageIO.read(new File(imgFilename)));
		} catch (IOException e) {
			System.out.println("playermodel not found!");
		}
		
	}

	@Override
	/**
	 *  play explosion animation and remove object from game
	 */
	public void explode() {
		// add animation and remove object from the game
		// die
	}
	
	@Override
	/**
	 * moves the object forwards
	 * @param distance
	 */
	public void moveForwards() {
		super.moveForwards();
		
		
		if (this.getTotalVelocity() > 10) {
			this.imgFilename = "images/fillimium_malcon.png";
		} else {
			this.imgFilename = "images/fillimium_malconLow.png";
		}
		
		try {
			this.setImage(ImageIO.read(new File(imgFilename)));
		} catch (IOException e) {
			System.out.println("playermodel not found!");
		}
	}
	
	
	@Override
	/**
	 * continuation of momentum
	 */
	public void continuePath() {
		super.continuePath();
		
		// reset image to default
		this.imgFilename = "images/fillimium_malconLow.png";
		try {
			this.setImage(ImageIO.read(new File(imgFilename)));
		} catch (IOException e) {
			System.out.println("playermodel not found!");
		}
		
	}
	
	public int getRotation() {
		return this.rotationAngle;
	}

	public void moveDown() {
		this.positionY += 1;
	}

	public void increaseMaxSpeed() {
		this.maxSpeed += 5;
	}
}
