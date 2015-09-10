import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.omg.CORBA.SetOverrideType;

/**
 * 
 * @author cse13105
 *  A DrawingSquare is an extension of the DrawingObject abstract class.
 *  It is used to store the details pertaining to a square for the drawing program.
 */

public class DrawingSquare extends DrawingObject {

	//------------
	//attributes
	//------------
	//height is the diffrence between x2 and x1
	//width is the diffrence between y2 and y1
	
	private int x1, y1, width, height;
	
	/**
	 * This constructs a new DrawingSquare object. 
	 * The square is defined by its two corners, (x1, y1) and (x2, y2).
	 */
	
	//--------------
	//constructor
	//--------------
	
	public DrawingSquare(int x1, int y1, int x2, int y2) {
		resizeObject(x1, y1, x2, y2);

	}
	
	/**
	 * This resizes a DrawingSquare object. 
	 * The two corners, (x1, y1) and (x2, y2), that define the square are updated.
	 */
	//------------
	//methods
	//------------
	
	@Override
	public void resizeObject(int x1, int y1, int x2, int y2) {

		this.x1 = x1;
		this.y1 = y1;
		this.width = x2 - x1;
		this.height = y2 - y1;
		if (height <= width) {
			width = height;
		} else {
			height = width;
		}
	}
	
	/**
	 * This function determines whether the given point (px, py), 
	 * where the user has clicked, hits the edge of this square.
	 */
	
	// handle each of the four lines in the square separately to enable drag
	
	@Override
	public boolean wasClicked(int px, int py) {
		return clickHitLine(px, py, x1, y1, x1 + width, y1) || 
				clickHitLine(px, py, x1, y1, x1, y1 + height) || 
				clickHitLine(px, py, x1, y1 + height, x1 + width, y1 + height) ||
				clickHitLine(px, py, x1 + width, y1, x1 + width, y1 + height) ;
	}
	
	/**
	 * This function returns a Bounding Box. 
	 * A bounding box is the smallest rectangle that contains the square.
	 */
	
	//A box around the square(a perfect fit)
	
	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(x1, y1, width, height);
	}
	
	/**
	 * This function draws the square using the given Graphics2D context.
	 */
	
	@Override
	public void drawObject(Graphics2D g2) {
		g2.drawRect(x1, y1, width, height);
	}

}
