import gui.Screen;

import javax.swing.*;

/**
* Contains the main method to bootstrap the application
*
*
*/

public class Main {

	public static void main(String[] args) {

		JFrame mainFrame = new JFrame();
		mainFrame.setSize(500,500);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.add(Screen.getInstance());

		mainFrame.setVisible(true);
	}
}