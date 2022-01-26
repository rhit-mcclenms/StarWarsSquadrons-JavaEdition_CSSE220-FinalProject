package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BigShip extends GameObject {

	private boolean hasTurned;
	private String imgFilename = "images/fuelShip.png";
	private int firstPiece = 0;
	
	public BigShip(int positionX, int positionY) {
		super(positionX, positionY);
		this.type = 5;
		this.setHasTurned(false);
		this.setObjectSize(200,100);
		this.maxSpeed = 4;
		try {
			this.setImage(ImageIO.read(new File(imgFilename)));
		} catch (IOException e) {
			System.out.println("bigship image not found!");
		}
	}

	public boolean getHasTurned() {
		return hasTurned;
	}

	public void setHasTurned(boolean hasTurned) {
		this.hasTurned = hasTurned;
		if (this.hasTurned == true) {
			this.imgFilename = "images/fuelShipBoosting.png";
			try {
				this.setImage(ImageIO.read(new File(imgFilename)));
			} catch (IOException e) {
				System.out.println("bigship image not found!");
			}
		}
	}

	public void setImageCompletion(int progress) {
		if (progress == 0) {
			this.imgFilename = "images/fuelShipP0.png";
			this.setObjectSize(100,60);
		} else if (progress == 1) {
			this.imgFilename = "images/fuelShipP0P1.png";
			this.firstPiece = 1;
			this.setObjectSize(140,60);
		} else if (progress == 2) {
			this.imgFilename = "images/fuelShipP0P2.png";
			this.firstPiece = 2;
			this.setObjectSize(100,100);
		} else if (progress == 3) {
			this.imgFilename = "images/fuelShipP0P3.png";
			this.firstPiece = 3;
			this.setObjectSize(140,60);
		} else if (progress == 4) {
			if (this.firstPiece == 2) {
				this.imgFilename = "images/fuelShipP0P1P2.png";
				this.setObjectSize(140,100);
			} else {
				this.imgFilename = "images/fuelShipP0P1P3.png";
				this.setObjectSize(200,70);
			}
			
		} else if (progress == 5) {
			if (this.firstPiece == 1) {
				this.imgFilename = "images/fuelShipP0P1P2.png";
				this.setObjectSize(140,100);
			} else {
				this.imgFilename = "images/fuelShipP0P2P3.png";
				this.setObjectSize(140,100);
			}
		} else if (progress == 6) {
			if (this.firstPiece == 1) {
				this.imgFilename = "images/fuelShipP0P1P3.png";
				this.setObjectSize(200,70);
			} else {
				this.imgFilename = "images/fuelShipP0P2P3.png";
				this.setObjectSize(140,100);
			}
		} else {
			this.imgFilename = "images/fuelShip.png";
			this.setObjectSize(200, 100);
		}
		try {
			this.setImage(ImageIO.read(new File(imgFilename)));
		} catch (IOException e) {
			System.out.println("bigship image not found!");
		}
		
	}

}
