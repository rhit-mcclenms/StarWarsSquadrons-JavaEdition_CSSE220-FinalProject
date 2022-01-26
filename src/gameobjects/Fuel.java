package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fuel extends GameObject {

	private String imgFilename = "images/fuel.png";
	private int partNumber;
	private boolean isAttached;
	
	public Fuel(int positionX, int positionY, int partType) {
		super(positionX, positionY);
		this.type = 4;
		this.setObjectSize(50,50);
		this.isAttached = false;
		this.rotationAngle = (int) (1000*Math.random());
		if (partType == 1 ) {
			this.imgFilename = "images/fuelShipPart1.png";
			this.partNumber = 1;
		} else if (partType == 2 ) {
			this.imgFilename = "images/fuelShipPart2.png";
			this.partNumber = 2;
		} else if (partType == 3 ){
			this.imgFilename = "images/fuelShipPart3.png";
			this.partNumber = 3;
		} else {
			// default 
			this.partNumber = 0;
		}
		
		try {
			this.setImage(ImageIO.read(new File(imgFilename)));
		} catch (IOException e) {
			System.out.println("fuel image not found!");
		}
		
	}

	public void attachTo(Player player) {
		this.isAttached = true;
	}
	
	public int getPartNumber() {
		return this.partNumber;
	}

	public void moveWith(Player player) {
		if (this.isAttached == true ){
			this.setPositionX((int) (player.getPositionX()-6*player.velocityX));
			this.setPositionY((int) (player.getPositionY()-6*player.velocityY));
		}
		
	}
	

}
