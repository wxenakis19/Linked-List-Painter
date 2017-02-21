package painter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Class that represents a stamp in the shape of an triangle. The class is used
 * when creating triangles in DrawingPane.
 */
public class Triangle extends Stamp {

	private java.awt.Polygon triangle;

	public Triangle() {
		triangle = new java.awt.Polygon();
	}

	public void setSize(float w, float h) {
		super.setSize(w, h);
		float x = getX();
		float y = getY();
		
		w = getSize().width;
		h = getSize().height;

		int[] x_coordinates = new int[3];
		int[] y_coordinates = new int[3];

		/** If y is not inverted, draw a normal upright triangle */
		if (!invertedY()) {
			x_coordinates[0] = (int) (x + w / 2);
			x_coordinates[1] = (int) x;
			x_coordinates[2] = (int) (x + w);
			y_coordinates[0] = (int) (y + h);
			y_coordinates[1] = (int) y;
			y_coordinates[2] = (int) y;

		/**
		 * If y is inverted, draw the new triangle by swapping the y
		 * coordinates */
		} else {
			x_coordinates[0] = (int) (x + w / 2);
			x_coordinates[1] = (int) x;
			x_coordinates[2] = (int) (x + w);
			y_coordinates[0] = (int) y;
			y_coordinates[1] = (int) (y + h);
			y_coordinates[2] = (int) (y + h);
		}

		if (triangle != null) {
			triangle.reset();
			triangle.addPoint(x_coordinates[0], y_coordinates[0]);
			triangle.addPoint(x_coordinates[1], y_coordinates[1]);
			triangle.addPoint(x_coordinates[2], y_coordinates[2]);
			
		}
	}

	public void setLocation(float x, float y) {
		super.setLocation(x, y);
		setSize(getSize().width, getSize().height);
	}

	public boolean contains(Point p) {
		return triangle.contains(p);
	}

	@Override
	public void render(Graphics2D g) {

		Color c = getColor();
		g.setColor(c);
		g.fill(triangle);
	}

	@Override
	public Stamp newStamp() {
		return new Triangle();
	}

}
