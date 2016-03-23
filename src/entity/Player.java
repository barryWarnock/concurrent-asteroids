package entity;

import gui.Screen;
import logger.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_W;

public class Player extends Entity {

	private static Player player = new Player();

	private int degOfRotation = 0;
	//0 degrees corresponds to facing directly vertical

	private static double linearSpeedChange = -1;
	//speed increase in direction player is moving

	private Player() {
		try {
			sprite = ImageIO.read(getClass().getResource("/graphics/player.png"));
		} catch (IOException e) {
			Log.warn(e.getMessage());
		}
		//TODO cleanup this bad engineering
		xPos = Screen.getInstance().getWidth()/2;
		yPos = Screen.getInstance().getHeight()/2;



	}

	private boolean accelerating = false;
	private boolean rotatingCW = false;
	private boolean rotatingCCW = false;

	//TODO this
	@Override
	public void update() {
		//TODO set sprite as non accelerating one
		if(accelerating) {
			int theta = degOfRotation % 360;
			xSpeed += (Math.sin((double) theta) * linearSpeedChange);
			ySpeed += (Math.cos((double) theta) * linearSpeedChange);
			//TODO swap the sprite for the accelerating one

			/*
			move forward, decompose linear speed change into x and y components
			based on the coordinate system where 0 degrees is viewed as vertical
			to the user and x and y axes are swapped relative to Cartesian coordinates
			*/
		}
		if(rotatingCCW) {
			degOfRotation += 1;
		}
		if(rotatingCW) {
			degOfRotation -= 1;
		}

		xPos += xSpeed;
		yPos += ySpeed;
		//TODO decay speed
		//TODO rotate the sprite

	}

	@Override
	public void die() {
		//TODO this
	}

	public static Player getInstance() {
		return player;
	}

	public Player initialize(int xLocation, int yLocation) {
		this.xPos = xLocation;
		this.yPos = yLocation;
		return player;
	}

	public void keyPressed(KeyEvent e) {

		int keyPressed = e.getKeyCode();

		switch(keyPressed) {
			case VK_A:
				rotatingCCW = true;
				//rotate cclockwise, positive degree increase
				Log.debug("Player pressing A");
				break;
			case VK_D:
				rotatingCW = true;
				//rotate clockwise, negative degree increase
				Log.debug("Player pressing D");
				break;
			case VK_W:
				accelerating = true;
				Log.debug("Player pressing W");
				break;
			default:
				Log.debug("Player pressing: " + keyPressed);
				break;
		}
	}
	public void keyReleased(KeyEvent e) {

		int keyReleased = e.getKeyCode();

		switch(keyReleased) {
			case VK_A:
				rotatingCCW = false;
				//rotate cclockwise, positive degree increase
				Log.debug("Player released A");
				break;
			case VK_D:
				rotatingCW = false;
				//rotate clockwise, negative degree increase
				Log.debug("Player released D");
				break;
			case VK_W:
				accelerating = false;
				Log.debug("Player releaded W");
				break;
			default:
				Log.debug("Player releasing: " + keyReleased);
				break;
		}
	}
}
