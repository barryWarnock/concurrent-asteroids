import gui.Screen;
import logger.Log;
import javax.swing.*;
import java.awt.*;
import game.Game;

/**
* Contains the main method to bootstrap the application
*
*
*/

public class Main {

	private static int width = 500;
	private static int height = 500;

	public static void main(String[] args) {
		Log.debugEnabled = true;
		Log.infoEnabled = true;

		Log.debug("Warming up.");
		launch();
		Log.debug("Ready to go!");

		Game.getInstance().gameLoop();
	}

	private static void launch() {
		Screen.getInstance().initialize(width, height);
		JFrame mainFrame = new JFrame();
		mainFrame.setSize(width, height);
		mainFrame.setMaximumSize(new Dimension(width, height));
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.add(Screen.getInstance());
		mainFrame.setVisible(true);
	}
}
