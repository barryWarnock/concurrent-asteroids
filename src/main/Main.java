package main;

import game.Game;
import gui.Screen;
import logger.Log;

import javax.swing.*;
import java.awt.*;

/**
* Contains the main method to bootstrap the application
*
*
*/

public class Main {

	private static int width = 500;
	private static int height = 500;
	private static int fps = 60;
	public static boolean runThreaded = true;
	public static boolean runQuadTree = true;
	public static boolean testMode = false;
	public static boolean playerLost = false;
	public static int quadTreeThreshold = 2;
	public static long testDuration = 5000;

	public static void main(String[] args) {
		Log.debugEnabled = true;
		Log.infoEnabled = true;

		Log.debug("Warming up.");
		launch();
		Log.debug("Ready to go!");

		try {
			if(testMode){
				test();
			} else {
				Game.getInstance().Level1(fps);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.warn(e.getMessage());
		}
	}

	private static void launch() {
		Screen.getInstance().initialize(width, height);
		JFrame mainFrame = new JFrame();
		mainFrame.setSize(width, height);
		mainFrame.setMaximumSize(new Dimension(width, height));
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.add(Screen.getInstance());
		mainFrame.setVisible(true);
	}

	private static void test() throws InterruptedException {
		int testLength = 10;
		int testIncrements = 10;
		int thresholdMax = 10;
		int thresholdIncrement = 1;

		runThreaded = true;
		runQuadTree = false;
		for(int i = 1; testLength > i; i++ ) {
			System.out.println(Game.getInstance().stressTest(i*testIncrements));
		}

		runThreaded = false;
		runQuadTree = false;
		for(int i = 1; testLength > i; i++ ) {
			System.out.println(Game.getInstance().stressTest(i*testIncrements));
		}

		runQuadTree = true;
		for(int j=2; thresholdMax > j; j+= thresholdIncrement) {
			quadTreeThreshold = j;
			runThreaded = true;

			for(int i = 1; testLength > i; i++ ) {
				System.out.println(Game.getInstance().stressTest(i*testIncrements));
			}
			runThreaded = false;
			for(int i = 1; testLength > i; i++ ) {
				System.out.println(Game.getInstance().stressTest(i*testIncrements));
			}
		}
	}
}
