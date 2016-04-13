package main;

import game.Game;
import gui.Screen;
import logger.Log;

import javax.swing.*;
import java.awt.*;
import java.io.*;

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
	public static boolean testMode = true;
	public static boolean playerLost = false;
	public static int quadTreeThreshold = 2;
	public static long testDuration = 30_000;

	public static void main(String[] args) {
		Log.debugEnabled = true;
		Log.infoEnabled = true;



		try {
			if(testMode){
				Screen.getInstance().initialize(width, height);
				test();
			} else {
				Log.debug("Warming up.");
				launch();
				Log.debug("Ready to go!");
				Game.getInstance().Level1(fps);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.warn(e.getMessage());
		} catch (FileNotFoundException e) {
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

	private static void test() throws InterruptedException, FileNotFoundException {
		PrintStream old = System.out;
		int testLength = 20;
		int testIncrements = 5;
		int[] thresholds = {2,5,10,15,20,25,50,75,100};
		int thresholdIncrement = 5;

//		System.setOut(new PrintStream(new BufferedOutputStream(
//				new FileOutputStream(new File("Threaded-Brute-small.txt"))), true));
//		System.out.println("Elements, Avg time per frame");
//		runThreaded = true;
//		runQuadTree = false;
//
//		for(int i = 1; testLength > i; i++ ) {
//			System.out.println(i*testIncrements + ", " + Game.getInstance().stressTest(i*testIncrements));
//		}
//
//		System.setOut(new PrintStream(new BufferedOutputStream(
//				new FileOutputStream(new File("Dumb-Brute-small.txt"))), true));
//		System.out.println("Elements, Avg time per frame");
//		runThreaded = false;
//		runQuadTree = false;
//
//		for(int i = 1; testLength > i; i++ ) {
//			System.out.println(i*testIncrements + ", " + Game.getInstance().stressTest(i*testIncrements));
//		}

		runQuadTree = true;
		for(int k=0; thresholds.length > k; k++) {
			quadTreeThreshold = thresholds[k];

			System.setOut(new PrintStream(new BufferedOutputStream(
					new FileOutputStream(new File("Threaded-QuadThreshold-small"+thresholds[k]+".txt"))), true));
			System.out.println("Elements, Avg time per frame");
			runThreaded = true;

			for(int i = 1; testLength > i; i++ ) {
				System.out.println(i*testIncrements + ", " + Game.getInstance().stressTest(i*testIncrements));
			}

			System.setOut(new PrintStream(new BufferedOutputStream(
					new FileOutputStream(new File("Dumb-QuadThreshold-small"+thresholds[k]+".txt"))), true));
			System.out.println("Elements, Avg time per frame");
			runThreaded = false;

			for(int i = 1; testLength > i; i++ ) {
				System.out.println(i*testIncrements + ", " + Game.getInstance().stressTest(i*testIncrements));
			}
		}
		System.setOut(old);
		Log.info("Test Done!");
	}
}
