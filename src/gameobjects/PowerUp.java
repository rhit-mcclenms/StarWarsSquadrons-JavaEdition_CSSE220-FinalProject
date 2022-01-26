package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PowerUp extends GameObject {
	
	private String P1Filename = "images/health.png";
	private String P2Filename = "images/speedBoost.png";
	private int powerType;
	
	public PowerUp(int positionX, int positionY, int powerType) {
		super(positionX, positionY);
		this.powerType = powerType;
		this.type = 6;
		if (powerType == 0) {
			try {
				this.setImage(ImageIO.read(new File(P1Filename)));
				this.setObjectSize(50,50);
			} catch (IOException e) {
				System.out.println("powerup 1 not found!");
			}
		} else {
			try {
				this.setImage(ImageIO.read(new File(P2Filename)));
				this.setObjectSize(30,60);
			} catch (IOException e) {
				System.out.println("powerup 2 not found!");
			}
		}
	
	}

	public int getPowerType() {
		return powerType;
	}

}
