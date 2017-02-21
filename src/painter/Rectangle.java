package painter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/** Class that represents a stamp in the shape of an rectangle. The class is used when creating rectangles
 * in DrawingPane  */
public class Rectangle extends Stamp {

	private java.awt.Rectangle rect;

	public Rectangle() {
		rect = new java.awt.Rectangle();
	}

	public void setSize(int w, int h) {
		super.setSize(w, h);

		rect.setBounds(rect.x, rect.y, w, h);
	}

	public void setLocation(int x, int y) {
		super.setLocation(x, y);

		rect.setBounds(x, y, rect.width, rect.height);
	}

	@Override
	public void render(Graphics2D g) {
		float x = getX();
		float y = getY();
		double w = getSize().getWidth();
		double h = getSize().getHeight();

		rect.setBounds((int) x, (int) y, (int) w, (int) h);
		Color c = getColor();
		g.setColor(c);
		g.fillRect((int) x, (int) y, (int) w, (int) h);
	}

	public boolean contains(Point p) {
		return rect.contains(p);
	}

	@Override
	public Stamp newStamp() {
		return new Rectangle();
	}

}
