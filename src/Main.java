import gui.Screen;
import logger.Log;
import javax.swing.*;

/**
* Contains the main method to bootstrap the application
*
*
*/

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Log.debugEnabled = true;
		Log.infoEnabled = true;

		int width = 500;
		int height = 500;

		JFrame mainFrame = new JFrame();
		mainFrame.setSize(width, height);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.add(Screen.getInstance().intialize(width, height));



		mainFrame.setVisible(true);
	}
}