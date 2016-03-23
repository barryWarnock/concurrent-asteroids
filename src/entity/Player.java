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

public class Player extends Entity implements KeyListener{

	private static Player player = new Player();

	private int degOfRotation = 0;
	//0 degrees corresponds to facing directly vertical

	private static double linearSpeedChange = 1;
	//speed increase in direction player is moving

	private Player() {
		//bufferedImage Sprite = imageIO.read(getClass().getResource(/graphics.playerMove));
		try {
			sprite = ImageIO.read(getClass().getResource("/graphics/player.png"));
		} catch (IOException e) {
			Log.warn(e.getMessage());
		}
		//TODO cleanup this bad engineering
		xPos = Screen.getInstance().getWidth()/2;
		yPos = Screen.getInstance().getHeight()/2;

	}

	@Override
	public void update() {
		//TODO this
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


	public void updateLocation() {
		xPos += xSpeed;
		yPos += ySpeed;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//TODO
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
						xSpeed += (Math.sin((double)theta)*linearSpeedChange);
						ySpeed += (Math.cos((double)theta)*linearSpeedChange);
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
		//TODO
	}
}
