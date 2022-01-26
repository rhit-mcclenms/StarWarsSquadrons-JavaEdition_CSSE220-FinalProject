package gameobjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class GameObject {
	
	// first 5 position variables could be condensed into a single integer array
	private int positionX;
	protected int positionY;
	protected double velocityX;
	protected double velocityY;
	protected int rotationAngle;
	protected int maxSpeed;
	private int health;
	public int type; // 0 is default, 1 is player, 2 is enemy, 3 is projectile, 4 is fuel, 5 is bigship, 6 is powerup, 7 is asteroid
	private boolean isSlowable = false;
	private boolean isMarked;
	
	private BufferedImage image;
	protected int objectSizeX = 100;
	protected int objectSizeY = 100;
	protected String imageFilename = "images/turtle.png";
	
	/**
	 * Constructor
	 * @param positionData
	 * @param maxHealth
	 */
	public GameObject(int positionX, int positionY) {
		this.health = 1;
		this.type = 0;
		this.positionX = positionX;
		this.positionY = positionY;
		this.isMarked = false;
		
		try {
			this.setImage(ImageIO.read(new File(imageFilename)));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	
	
	/**
	 * Draws the object onto the canvas
	 * @param g2d
	 */
	public void drawObject(Graphics2D g2d) {
		g2d = (Graphics2D) g2d.create();
		
		g2d.translate(this.positionX, this.positionY);
		g2d.rotate(Math.toRadians(this.rotationAngle));
		g2d.drawImage(this.image, -this.getObjectSizeX() / 2, -this.getObjectSizeY() / 2, this.getObjectSizeX(), this.getObjectSizeY(), null);
	
	}
	
	/**
	 * 
	 * @return total velocity (not relative to angle facing)
	 */
	public double getTotalVelocity() {
		return Math.sqrt(Math.pow(this.velocityX, 2) + Math.pow(this.velocityY, 2));	
	}
	
	/**
	 * moves the object forwards
	 * @param distance
	 */
	public void moveForwards() {
		
		if (this.velocityX <= this.maxSpeed && this.velocityX >= -this.maxSpeed) {
			this.velocityX += 1 * Math.cos(Math.toRadians(this.rotationAngle));
		}
		
		if (this.velocityY <= this.maxSpeed && this.velocityY >= -this.maxSpeed) {
			this.velocityY += 1 * Math.sin(Math.toRadians(this.rotationAngle));
		}
	}
	
	
	/**
	 * moves the object backwards
	 * @param distance
	 */
	public void moveBackwards() {
		
		if (this.getTotalVelocity() <= this.maxSpeed) {
			this.velocityX -= 1 * Math.cos(Math.toRadians(this.rotationAngle));
			this.velocityY -= 1 * Math.sin(Math.toRadians(this.rotationAngle));
		}
	}
	
	/**
	 * turns based on degree angle
	 * @param angle
	 */
	public void turn(int angle) {
		this.rotationAngle = this.rotationAngle + angle;
		
	}

	/**
	 * Checks to see if the position of the object was close enough to the position of the incoming object
	 * @return true if the object was hit (close enough position)
	 */
	public boolean checkCollision(int incomingX, int incomingY, int targetSizeX, int targetSizeY) {
		if (Math.abs(this.positionX - incomingX) <= (this.getObjectSizeX()/2+targetSizeX)/2 && Math.abs(this.positionY - incomingY) <= (this.getObjectSizeY()/2+targetSizeY)/2 ) {
			return true;
		}
		return false;
	}
	
	/**
	 *  play explosion animation and remove object from game
	 */
	public void explode() {
		// add animation and remove object from the game
		// die
		this.markForRemoval();
	}

	/**
	 * continuation of momentum
	 */
	public void continuePath() {
		
		this.positionX += (int) (this.velocityX);
		this.positionY += (int) (this.velocityY);
		
		// ADD A SLOWDOWN FACTOR HERE
		if (this.isSlowable == true) {
			// X
			if (this.velocityX > 0) {
				this.velocityX -= 0.1;
			} else if (this.velocityX == 0) {
				// nothing
			} else {
				this.velocityX += 0.1;
			}
			
			// Y
			if (this.velocityY > 0) {
				this.velocityY -= 0.1;
			} else if (this.velocityY == 0) {
				// nothing
			} else {
				this.velocityY += 0.1;
			}
		}
		
		
	}

	/**
	 * used to change velocity and bump an offset to avoid glitching (only somewhat successful)
	 */
	public void bounce() {
		this.velocityX = -this.velocityX/2;
		this.velocityY = -this.velocityY/2;
		
		this.positionX += (int) (this.objectSizeX/10)*(this.velocityX)/(Math.abs(this.velocityX)) + this.velocityX;
		this.positionY += (int) (this.objectSizeY/10)*(this.velocityY)/(Math.abs(this.velocityY)) + this.velocityY;
		//this.turn(180);
	}

	
	public int getHealth() {
		return health;
	}
	
	/**
	 * handle death
	 * @param newHealth
	 */
	public void setHealth(int newHealth) {
		if (newHealth <= 0) {
			this.explode();
			
		} else {
			this.health = newHealth;
		}
		
	}


	public void setImage(BufferedImage image) {
		this.image = image;
	}


	public int getObjectSizeY() {
		return objectSizeY;
	}
	
	public int getObjectSizeX() {
		return objectSizeX;
	}


	public void setObjectSize(int objectSizeX, int objectSizeY) {
		this.objectSizeX = objectSizeX;
		this.objectSizeY = objectSizeY;
	}

	public int getPositionX() {
		return this.positionX;
	}
	
	public int getPositionY() {
		return this.positionY;
	}
	
	public void setPositionX(int x) {
		this.positionX = x;
	}
	
	public void setPositionY(int y) {
		this.positionY = y;
	}

	public boolean getIsMarked() {
		return this.isMarked;
	}

	public void setSlowable(boolean isSlowable) {
		this.isSlowable = isSlowable;
	}


	public void markForRemoval() {
		this.isMarked = true;
	}
	

	
	
	
}
