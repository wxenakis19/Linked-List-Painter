package painter;

import javax.swing.*;

import painter.DrawingPane.ResetListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
	private DrawingPane drawing;

	/**
	 * Setup the main Window
	 * 
	 * @param title
	 *            The title of the window
	 */
	public MainWindow(String title) {
		super(title);

		drawing = new DrawingPane();
		JPanel toolbar = setupToolbar();

		setLayout(new BorderLayout());
		add(toolbar, BorderLayout.WEST);
		add(drawing, BorderLayout.CENTER);
	}

	public JPanel setupToolbar() {
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));

		// The stamps to use for the buttons
		/*
		 * TODO: Add the Stamps that need buttons here
		 */
		Stamp[] stamps = { new Rectangle(), new Elipse(), new Triangle() };

		toolbar.add(Box.createGlue());
		for (Stamp s : stamps) {
			Tool t = new Tool(s);
			t.setPreferredSize(new Dimension(60, 60));
			t.addActionListener(new BtnListener());
			toolbar.add(t);
		}
		toolbar.add(Box.createGlue());
		
		JButton resetBtn = new JButton("Reset");
		ResetListener resetListener = drawing.new ResetListener();
		resetBtn.addActionListener(resetListener);
		toolbar.add(resetBtn);

		return toolbar;
	}

	public class BtnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Tool t = (Tool) e.getSource();
			drawing.setSelected(t.getStamp().newStamp());
		}
	}

}
