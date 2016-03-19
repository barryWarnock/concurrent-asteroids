package entity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_W;

public class Player extends Entity implements KeyListener{

	private static Player player = new Player();

	private int degOfRotation = 0;
	//0 degrees corresponds to facing directly vertical

	private static int linearSpeedChange = 1;
	//speed increase in direction player is moving

	private Player(){
	}

	@Override
	public void runCollisionChecking() {
		// TODO
	}

	public Player getInstance() {
		return player;
	}

	public Player initialize(int xLocation, int yLocation) {
		this.xPos = xLocation;
		this.yPos = yLocation;
		return player;
	}


	@Override
	public void updateLocationAndMomentum() {
		xPos += xSpeed;
		yPos += ySpeed;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyPressed = e.getKeyCode();

		int theta;

		switch(keyPressed) {
			case VK_A: 	degOfRotation += 1;
				//rotate cclockwise, positive degree increase
				break;
			case VK_D: 	degOfRotation -= 1;
				//rotate clockwise, negative degree increase
				break;
			case VK_W: 	theta = degOfRotation%360;
						xSpeed += (int)(Math.sin((double)theta)*linearSpeedChange);
						ySpeed += (int)(Math.cos((double)theta)*linearSpeedChange);
				/*
				move forward, decompose linear speed change into x and y components
				based on the coordinate system where 0 degrees is viewed as vertical
				to the user and x and y axes are swapped relative to Cartesian coordinates
				*/
				break;
			default:
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	//bufferedImage Sprite = imageIO.read(getClass().getResource(/graphics.playerMove));

}
