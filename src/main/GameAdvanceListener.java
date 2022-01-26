package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameAdvanceListener implements ActionListener {

	private ClickComponent component;

	public GameAdvanceListener(ClickComponent component) {
		// TODO Auto-generated constructor stub
		this.component = component;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		advanceOneTick();
		// TODO Auto-generated method stub
		
	}
	
	public void advanceOneTick() {
//		System.out.println("Current Time " + System.currentTimeMillis());
		
		this.component.checkIfEndgame();
		this.component.gravity();
		this.component.momentum();
		this.component.dragCans();
		this.component.drawEnemies();
		this.component.drawScreen();
		this.component.checkCollisions();
		this.component.devourTheMarked();
	}

}
