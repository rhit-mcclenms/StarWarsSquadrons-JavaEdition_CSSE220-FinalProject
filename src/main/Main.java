package main;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

/**
 * The main class for your arcade game.
 * 
 * You can design your game any way you like, but make the game start
 * by running main here.
 * 
 * Also don't forget to write javadocs for your classes and functions!
 * 
 * @author YOUR_NAME
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static final int DELAY=50;
	
	public static void main(String[] args) {
		new Main();
		
		
	}
	

	private BufferedImage imageTitle;
	protected String imageTitleFilename = "images/titleImage.jpg";
	
	
	public Main() {
		JFrame frame = new JFrame("Star Wars: Squadrons | Java Edition");
		frame.setSize(1600, 900);
		 
		
		JPanel GUIpanel = new JPanel();
		JLabel GUIlabel = new JLabel("BEGIN GAME");
		GUIpanel.add(GUIlabel);
		
		ClickComponent component = new ClickComponent(frame,GUIlabel);

		
		KeyListener keys = new ClickListener(component);
		GameAdvanceListener advanceListener = new GameAdvanceListener(component);
		frame.addKeyListener(keys);
		
		
		
		musicStuff levelOST = new musicStuff();
		levelOST.playMusic("images/level3OST.wav");
		levelOST.playMusic("images/falconSFX.wav");

		

		
		frame.add(GUIpanel, BorderLayout.NORTH);
		frame.add(component, BorderLayout.CENTER);
		
		
		JButton upArrow = new JButton("Up Arrow");
		
		
		Timer timer = new Timer(DELAY, advanceListener);
		timer.start();

		
		
		
		
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}