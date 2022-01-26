package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Projectile extends GameObject {

	private int damage;
	private String redFilename = "images/redlaser.png";
	private String greenFilename = "images/greenlaser.png";
	
	public Projectile(int positionX, int positionY, int rotationAngle, boolean isRed) {
		super((int) (positionX+(70*Math.cos(Math.toRadians(rotationAngle)))), (int) (positionY+(70*Math.sin(Math.toRadians(rotationAngle)))));
		//this.damage = damage;
		this.maxSpeed = 21;
		this.rotationAngle = rotationAngle;
		this.velocityX = this.maxSpeed * Math.cos(Math.toRadians(this.rotationAngle));
		this.velocityY = this.maxSpeed * Math.sin(Math.toRadians(this.rotationAngle));
		this.type = 3;
		
		if (isRed == true) {
			try {
				this.setImage(ImageIO.read(new File(redFilename)));
				this.setObjectSize(20,15);
			} catch (IOException e) {
				System.out.println("red laser not found!");
			}
		} else {
			try {
				this.setImage(ImageIO.read(new File(greenFilename)));
				this.setObjectSize(20,5);
			} catch (IOException e) {
				System.out.println("green laser not found!");
			}
		}
		
	}	

}
