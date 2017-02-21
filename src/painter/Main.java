package painter;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Main {

	// The program starts here
	public static void main(String[] args) {

		MainWindow app = new MainWindow("Painter");
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setSize(500, 500);

		// Center the frame on the primary screen
		app.setLocationRelativeTo(null);
		app.setVisible(true);
	}
}
