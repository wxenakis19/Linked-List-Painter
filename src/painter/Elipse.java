package painter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

/**
 * Class that represents a stamp in the shape of an ellipse. The class is used
 * when creating ellipses in DrawingPane
 */
public class Elipse extends Stamp {
	private Ellipse2D.Float elipse;

	public Elipse() {
		elipse = new Ellipse2D.Float();
	}

	public void setSize(float w, float h) {
		super.setSize(w, h);

		if (elipse != null)
			elipse.setFrame(elipse.x, elipse.y, getSize().width, getSize().height);
	}

	public void setLocation(float x, float y) {
		super.setLocation(x, y);

		if (elipse != null) {
			elipse.setFrame(x, y, elipse.width, elipse.height);
		}
	}

	public boolean contains(Point p) {
		return elipse.contains(p);
	}

	@Override
	public void render(Graphics2D g) {
		Color c = getColor();
		g.setColor(c);
		g.fill(elipse);
	}

	@Override
	public Stamp newStamp() {
		return new Elipse();
	}

}
