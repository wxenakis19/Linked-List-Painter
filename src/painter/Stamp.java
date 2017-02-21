package painter;
import java.awt.*;

/**
 *
 * This class represents to base for all graphical drawing tools for the painter.
 * 
 * In order to create new Stamps, you should extend this class and override the 
 * two abstract methods to draw correctly.
 * 
 * @author bsea
 *
 */

public abstract class Stamp {
	
	private float x, y, width, height;
	private Color color;
	private boolean fill;
	
	public Stamp() {
		reset();
	}
	
	abstract boolean contains(Point p);
	
	public void reset(){
		this.setSize(0, 0);
		this.setLocation(0, 0);
		this.color = Color.BLACK;
		this.fill = false;
	}
	
	public boolean isFilled() {
		return fill;
	}
	
	public void setFilled( boolean filled ){
		this.fill = filled;
	}
	
	public void setSize( float width, float height ){
		this.width = width;
		this.height = height;
	}
	
	public void setLocation( float x, float y ){
		this.x = x;
		this.y = y;
	}
	
	public boolean invertedY(){
		if( height < 0 ){
			return true;
		}
		return false;
	}
	
	public boolean invertedX() {
		if( width < 0 ){
			return true;
		}
		return false;
	}
	
	public int getX() {
		int x = Math.round( this.x );
		return x;
	}
	
	public int getY() {
		int y = Math.round(this.y);
		return y;
	}
	
	public Dimension getSize() {
		Dimension rtn = new Dimension();
		rtn.width = Math.abs(Math.round(this.width));
		rtn.height = Math.abs(Math.round(this.height));
		return rtn;
	}
		
	public boolean setColor( Color c ){
		boolean rtn = false;
		if( c != null ) {
			this.color = c;
			rtn = true;
		}
		return rtn;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public abstract void render( Graphics2D g );
	public abstract Stamp newStamp();
}
