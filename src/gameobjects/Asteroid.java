package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Asteroid extends GameObject {

	private String imgFilename = "images/astroid.png";
	
	public Asteroid(int positionX, int positionY, int size) {
		super(positionX, positionY);
		this.setObjectSize(100*size, 100*size);
		this.type = 7;
		this.rotationAngle = (int) (1000*Math.random());
		this.setHealth(10*size);
		try {
			this.setImage(ImageIO.read(new File(imgFilename)));
		} catch (IOException e) {
			System.out.println("debris image not found!");
		}
		
	}

}
