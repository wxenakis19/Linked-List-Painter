package painter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawingPane extends JPanel {

	private Stamp selected;
	private LList<Stamp> shapes;
	private Random rand;

	public DrawingPane() {
		/** Initialize the shapes arrayList and rand */
		shapes = new LList<Stamp>();
		rand = new Random();

		/**
		 * Initializing the mouse listener and mouse motion listener and key
		 * listener.
		 */
		Mouser mouser = new Mouser();
		this.addMouseListener(mouser);
		this.addMouseMotionListener(mouser);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke((KeyEvent.VK_BACK_SPACE), 0, true), "delete");
		this.getActionMap().put("delete", new DeleteListener());
	}

	public boolean setSelected(Stamp toUse) {
		boolean rtn = false;
		if (toUse != null) {
			this.selected = toUse;
			rtn = true;
		}
		return rtn;
	}

	public Stamp getSelected() {
		return selected;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		/*
		 * TODO: Complete the painting
		 */
		/** For every Stamp in the arrayList shapes, render each one */
		for (Stamp s : shapes) {
			s.render(g2);
		}

		/** Render the current selected Stamp */
		if (selected != null) {
			selected.render(g2);
		}

	}

	private class DeleteListener extends AbstractAction {

		@Override
		// Removes the last node in the list, repaints, and sets selected to a
		// size of (0,0).
		public void actionPerformed(ActionEvent e) {
			shapes.remove(shapes.size() - 1);
			repaint();
			selected.setSize(0, 0);
		}

	}

	public class ResetListener implements ActionListener {

		@Override
		// Clears the window, repaints, and sets selected to a size of (0,0).
		public void actionPerformed(ActionEvent e) {
			shapes.clear();
			repaint();
			selected.setSize(0, 0);
		}

	}

	private class Mouser implements MouseListener, MouseMotionListener {

		private int anchorX, anchorY, distX, distY, x_coor, y_coor;
		private boolean mouseDragged, quad1, quad2, quad3, quad4;
		private Stamp clickedStamp;

		@Override
		public void mouseClicked(MouseEvent e) {
			int x_coor = e.getX();
			int y_coor = e.getY();
			int size = shapes.size();
			Point clickPoint = new Point(x_coor, y_coor);

			aLoop: for (int i = size - 1; i >= 0; i--) {

				if ((shapes.get(i)).contains(clickPoint)) {
					shapes.add(shapes.remove(i));
					break aLoop;
				}
			}
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			mouseDragged = false;
			int size = shapes.size();
			x_coor = e.getX();
			y_coor = e.getY();
			Point clickPoint = new Point(x_coor, y_coor);

			/** Set anchors to coordinates of click */
			anchorX = e.getX();
			anchorY = e.getY();

			// If either Alt or Control is pressed, find the forward most shape
			// that contains the point where the mouse was clicked
			if (e.isAltDown() || e.isControlDown()) {
				for (int i = size - 1; i >= 0; i--) {
					if ((shapes.get(i)).contains(clickPoint)) {
						clickedStamp = shapes.get(i);
						return;
					}
				}
				return;
			}
			
			
			Stamp current = getSelected();

			if (current == null) {
				return;
			}

			/**
			 * Create a new stamp, randomize the color and set it as the current
			 * stamp
			 */
			Stamp new_Stamp = current.newStamp();
			new_Stamp.setLocation(anchorX, anchorY);
			new_Stamp.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
			setSelected(new_Stamp);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			/**
			 * Append the selected stamp to the shapes arrayList Reset selected
			 * by calling the newStamp() method Repaint the window
			 */
			if (clickedStamp != null) {
				clickedStamp = null;
				return;
			}
			if (mouseDragged && selected != null) {
				shapes.add(selected);
				selected.newStamp();
				repaint();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// Sets variables to the distance of change between the anchor and
			// the current mouse point
			int changeX = e.getX() - anchorX;
			int changeY = e.getY() - anchorY;
			mouseDragged = true;
			// If Alt is pressed, reset the location using the change of X and Y
			if (e.isAltDown() && clickedStamp != null) {
				clickedStamp.setLocation(clickedStamp.getX() + changeX, clickedStamp.getY() + changeY);
				anchorX = e.getX();
				anchorY = e.getY();
				repaint();
				return;

				// If Control is pressed, reset the size of the clicked shape
				// based on which quadrant of the shape the click is in.
			} else if (e.isControlDown() && clickedStamp != null) {
				if (x_coor > clickedStamp.getX() + clickedStamp.getSize().getWidth() / 2
						&& y_coor > clickedStamp.getY() + clickedStamp.getSize().getHeight() / 2) {
					clickedStamp.setSize((float) (clickedStamp.getSize().getWidth() + changeX),
							(float) (clickedStamp.getSize().getHeight() + changeY));
				
				}
				repaint();
			} else {

				/** Upper left x and y coordinates */
				int ux = anchorX;
				int uy = anchorY;

				/**
				 * Set width to current mouse x coordinate - upper left x. Set
				 * height to current mouse y coordinate - upper left y.
				 */
				int width = e.getX() - ux;
				int height = e.getY() - uy;
				Stamp selected = getSelected();

				if (selected == null) {
					return;
				}

				/**
				 * If mouse x is to the left of the anchor, set the upper left x
				 * to mouse x and reset the width
				 */
				if (e.getX() < anchorX) {
					ux = e.getX();
					width = ux - anchorX;
				}

				/**
				 * If mouse y is below the anchor, set the upper left y to mouse
				 * y and reset the height
				 */
				if (e.getY() < anchorY) {
					uy = e.getY();
					height = uy - anchorY;
				}

				/*
				 * TODO: Complete the 'rubberbanding' effect
				 */
				/**
				 * Set location of upper left hand corner and width and height
				 * of selected
				 */
				selected.setLocation(ux, uy);
				selected.setSize(width, height);

				repaint();

			}

		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}

	}

}
